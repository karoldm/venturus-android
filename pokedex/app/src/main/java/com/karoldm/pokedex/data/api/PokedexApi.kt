package com.karoldm.pokedex.data.api

import com.karoldm.pokedex.data.models.api.GenerationsResponse
import com.karoldm.pokedex.data.models.api.PokemonDetails
import com.karoldm.pokedex.data.models.api.PokemonSpecies
import com.karoldm.pokedex.data.models.api.PokemonsResponse
import com.karoldm.pokedex.data.models.api.PokemonTypesResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokedexApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): Response<PokemonDetails>

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonsResponse>

    @GET("type")
    suspend fun getTypeList(): Response<PokemonTypesResponse>

    @GET("generation")
    suspend fun getGenerations(): Response<GenerationsResponse>

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(@Path("name") name: String): Response<PokemonSpecies>

}

object RemoteApiProvider {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    val service: PokedexApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PokedexApiService::class.java)
    }
}