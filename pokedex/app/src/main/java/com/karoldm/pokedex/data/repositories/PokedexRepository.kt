package com.karoldm.pokedex.data.repositories

import com.karoldm.pokedex.data.api.PokedexApiService
import com.karoldm.pokedex.data.api.RemoteApiProvider
import com.karoldm.pokedex.data.models.PokemonDetailsResponse
import com.karoldm.pokedex.data.models.PokemonListResponse

class PokedexRepository(
    private val pokedexApiService: PokedexApiService = RemoteApiProvider.service
) {
    suspend fun fetchPokemons(): Result<PokemonListResponse> = try {
        val response = pokedexApiService.getPokemonList()
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                Result.success(body)
            } else {
                Result.failure(IllegalStateException("Empty response"))
            }

        } else {
            Result.failure(IllegalStateException("HTTP ${response.code()} \n ${response.errorBody()}"))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }

    suspend fun fetchPokemonDetails(id: String): Result<PokemonDetailsResponse> = try {
        val response = pokedexApiService.getPokemonDetails(id)
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                Result.success(body)
            } else {
                Result.failure(IllegalStateException("Empty response"))
            }

        } else {
            Result.failure(IllegalStateException("HTTP ${response.code()} \n ${response.errorBody()}"))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }
}