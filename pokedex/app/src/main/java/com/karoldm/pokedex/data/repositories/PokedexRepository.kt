package com.karoldm.pokedex.data.repositories

import android.util.Log
import com.karoldm.pokedex.data.api.PokedexApiService
import com.karoldm.pokedex.data.api.RemoteApiProvider
import com.karoldm.pokedex.data.models.PokemonDetailsResponse
import com.karoldm.pokedex.data.models.PokemonListResponse
import com.karoldm.pokedex.data.models.PokemonType

class PokedexRepository(
    private val pokedexApiService: PokedexApiService = RemoteApiProvider.service
) {

    suspend fun fetchGenerationCount(): Result<Int> = try {
        val response = pokedexApiService.getGenerationCount()
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                Result.success(body.count)
            } else {
                Result.failure(IllegalStateException("Empty response"))
            }

        } else {
            Result.failure(IllegalStateException("HTTP ${response.code()} \n ${response.errorBody()}"))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }

    suspend fun fetchTypeList(): Result<List<PokemonType>> = try {
        val response = pokedexApiService.getTypeList()
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                Result.success(body.result)
            } else {
                Result.failure(IllegalStateException("Empty response"))
            }

        } else {
            Result.failure(IllegalStateException("HTTP ${response.code()} \n ${response.errorBody()}"))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }


    suspend fun fetchPokemons(offset: Int, limit: Int): Result<PokemonListResponse> = try {
        val response = pokedexApiService.getPokemonList(offset, limit)
        Log.e("response", response.toString())
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
        Log.e("PokedexRepository", "Erro ao buscar pokémons", t)
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