package com.karoldm.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karoldm.pokedex.data.models.view.Pokemon
import com.karoldm.pokedex.data.repositories.PokedexRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DetailsViewModel(
    private val pokedexRepository: PokedexRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    val pokemon: StateFlow<Pokemon?> = _pokemon.asStateFlow()

    fun loadPokemon(name: String) {
        viewModelScope.launch {
            _loading.value = true
            pokedexRepository.fetchPokemon(name)
                .onSuccess { response ->
                    _pokemon.value = response
                }
                .onFailure { e ->
                    println("Error fetching pokemon: $e")
                }
            _loading.value = false
        }
    }
}
