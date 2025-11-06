package com.karoldm.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karoldm.pokedex.data.models.PokemonListItem
import com.karoldm.pokedex.data.models.PokemonType
import com.karoldm.pokedex.data.repositories.PokedexRepository
import kotlinx.coroutines.launch
import kotlin.collections.addAll


class HomeViewModel(
    private val pokedexRepository: PokedexRepository
): ViewModel() {
    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _typeList= MutableLiveData<List<PokemonType>>()
    val typeList: LiveData<List<PokemonType>> get() = _typeList

    private var _pokemons = MutableLiveData(arrayListOf<PokemonListItem>())
    val pokemons: LiveData<ArrayList<PokemonListItem>> get() = _pokemons

    private val limit = 50;
    private var offset = 0;

    private var _genCount = MutableLiveData<Int>(0)
    val genCount: LiveData<Int> get() = _genCount

    init {
        loadFilters()
        loadPokemons()
    }

    fun loadFilters() {
        viewModelScope.launch {
            pokedexRepository.fetchTypeList()
                .onSuccess {
                    _typeList.value = it
                }
                .onFailure {
                    print("Error to fetch types")
                }
            pokedexRepository.fetchGenerationCount()
                .onSuccess { generationCount ->
                    _genCount.value = generationCount

                }
                .onFailure {
                    print("Error to fetch generation count")
                }
        }
    }


    fun loadPokemons() {
        viewModelScope.launch {
            pokedexRepository.fetchPokemons(offset, limit)
                .onSuccess {
                    val currentList = _pokemons.value ?: arrayListOf()
                    val newList = ArrayList(currentList).apply {
                        addAll(it.results)
                    }
                    _pokemons.value = newList
                    offset += limit
                }
                .onFailure {
                    print("Error to fetch pokemons")
                }
        }
    }
}