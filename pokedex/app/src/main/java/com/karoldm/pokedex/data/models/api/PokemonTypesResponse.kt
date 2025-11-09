package com.karoldm.pokedex.data.models.api

import com.squareup.moshi.Json


data class PokemonType(
    @Json(name="name") val name: String
)

data class PokemonTypesResponse(
    @Json(name="results") val result: List<PokemonType>
)