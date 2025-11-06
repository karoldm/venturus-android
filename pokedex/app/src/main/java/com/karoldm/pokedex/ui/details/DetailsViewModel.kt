package com.karoldm.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karoldm.pokedex.data.models.PokemonDetailsResponse
import com.karoldm.pokedex.data.repositories.PokedexRepository
import kotlinx.coroutines.launch


class DetailsViewModel(
    private val pokedexRepository: PokedexRepository,
): ViewModel() {
    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _pokemon = MutableLiveData<PokemonDetailsResponse>()
    val pokemon: LiveData<PokemonDetailsResponse> get() = _pokemon


    fun getPokemon(id: String) {
        Log.e("pokemon", id)
        viewModelScope.launch {
            pokedexRepository.fetchPokemonDetails(id)
                .onSuccess {
                    Log.e("sucess", it.toString())
                    _pokemon.value = it
                }
                .onFailure {
                    Log.e("pokemon", it.toString())
                    print("Error to fetch pokemon with ID: $id")
                }
        }
    }
}