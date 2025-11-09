package com.karoldm.pokedex.data.repositories

import android.util.Log
import com.karoldm.pokedex.data.api.PokedexApiService
import com.karoldm.pokedex.data.api.RemoteApiProvider
import com.karoldm.pokedex.data.models.api.Generation
import com.karoldm.pokedex.data.models.api.PokemonDetails
import com.karoldm.pokedex.data.models.api.PokemonSpecies
import com.karoldm.pokedex.data.models.api.PokemonsResponse
import com.karoldm.pokedex.data.models.api.PokemonType
import com.karoldm.pokedex.data.models.view.Pokemon

class PokedexRepository(
    private val pokedexApiService: PokedexApiService = RemoteApiProvider.service
) {

    suspend fun fetchGenerations(): Result<List<Generation>> = try {
        val response = pokedexApiService.getGenerations()
        if(response.isSuccessful){
            val body = response.body()
            if(body != null) {
                Result.success(body.results)
            } else {
                Result.failure(IllegalStateException("Empty response"))
            }

        } else {
            Result.failure(IllegalStateException("HTTP ${response.code()} \n ${response.errorBody()}"))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }

    suspend fun fetchTypes(): Result<List<PokemonType>> = try {
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

    suspend fun fetchPokemons(offset: Int, limit: Int): Result<PokemonsResponse> = try {
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

    suspend fun fetchPokemonSpecie(id: String): Result<PokemonSpecies>  = try {
        val response = pokedexApiService.getPokemonSpecies(id)
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

    suspend fun fetchPokemon(name: String): Result<Pokemon> = try {
        val responsePokemonDetails = this.fetchPokemonDetails(name)
        val pokemonDetails = responsePokemonDetails.getOrNull()
        if(pokemonDetails != null) {
            val responsePokemonSpecies = this.fetchPokemonSpecie(pokemonDetails.species.name);
            val pokemonSpecies = responsePokemonSpecies.getOrNull()
            if(pokemonSpecies != null) {
                Result.success(Pokemon(
                    name=pokemonDetails.name,
                    stats = pokemonDetails.stats,
                    types = pokemonDetails.types.map { it.type },
                    height = pokemonDetails.height,
                    weight = pokemonDetails.weight,
                    sprites = pokemonDetails.sprites,
                    generation = pokemonSpecies.generation,
                ))
            }
            else {
                Result.failure(IllegalStateException("Fail to fetch pokemon species"))
            }
        } else {
            Result.failure(IllegalStateException("Fail to fetch pokemon details"))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }

    suspend fun fetchPokemonDetails(name: String): Result<PokemonDetails> = try {
        val response = pokedexApiService.getPokemonDetails(name)
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