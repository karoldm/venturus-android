package com.karoldm.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karoldm.pokedex.data.models.api.Generation
import com.karoldm.pokedex.data.models.api.PokemonDetails
import com.karoldm.pokedex.data.models.api.PokemonType
import com.karoldm.pokedex.data.models.view.Pokemon
import com.karoldm.pokedex.data.repositories.PokedexRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Filter(
    val name: String? = null,
    val type: String? = null,
    val gen: String? = null,
)

class HomeViewModel(
    private val pokedexRepository: PokedexRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _typeList = MutableStateFlow<List<PokemonType>>(emptyList())
    val typeList: StateFlow<List<PokemonType>> = _typeList.asStateFlow()

    private val _genList = MutableStateFlow<List<Generation>>(emptyList())
    val genList: StateFlow<List<Generation>> = _genList.asStateFlow()

    private val _originalPokemons = mutableListOf<Pokemon>()
    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons.asStateFlow()

    init {
        loadFilters()
        loadPokemons()
    }

    fun filter(filter: Filter) {
        var filtered: List<Pokemon> = _originalPokemons
        filter.name?.let { name ->
            filtered = filtered.filter { it.name.contains(name, ignoreCase = true) }
        }
        filter.type?.let { type ->
            filtered = filtered.filter { pokemon ->
                pokemon.types.any { it.name == type }
            }
        }
        filter.gen?.let { gen ->
            filtered = filtered.filter { it.generation.name == gen }
        }
        _pokemons.value = filtered.toMutableList()
    }

    private fun loadFilters() {
        viewModelScope.launch {
            pokedexRepository.fetchTypes()
                .onSuccess { _typeList.value = it }
                .onFailure { Log.e("homeViewModel", "Error fetching types") }

            pokedexRepository.fetchGenerations()
                .onSuccess { _genList.value = it }
                .onFailure { Log.e("homeViewModel", "Error fetching generations") }
        }
    }

    fun loadPokemons() {
        viewModelScope.launch {
            _loading.value = true
            pokedexRepository.fetchPokemons(0, 2000)
                .onSuccess { response ->
                    val details = coroutineScope {
                        response.results.map { pokemon ->
                            async {
                                val result = pokedexRepository.fetchPokemon(pokemon.name)
                                result.onFailure { e ->
                                    Log.e("HomeViewModel", "Failed for ${pokemon.name}: $e")
                                }
                                result.getOrNull()
                            }
                        }.awaitAll().filterNotNull()
                    }
                    _originalPokemons.clear()
                    _originalPokemons.addAll(details)
                    _pokemons.value = details
                }
                .onFailure { e ->
                    println("Error fetching pokemons: $e")
                }
            _loading.value = false
        }
    }
}
