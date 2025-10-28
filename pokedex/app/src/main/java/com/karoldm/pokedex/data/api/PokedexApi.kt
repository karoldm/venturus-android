package com.karoldm.pokedex.data.api

import com.karoldm.pokedex.data.models.PokemonDetailsResponse
import com.karoldm.pokedex.data.models.PokemonListResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface PokedexApiService {
    @GET("/pokemon/:id")
    suspend fun getPokemonDetails(id: String): Response<PokemonDetailsResponse>

    @GET("/pokemon")
    suspend fun getPokemonList(): Response<PokemonListResponse>
}

object RemoteApiProvider {
    private const val BASE_URL = "https://pokeapi.co/api/v2"

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