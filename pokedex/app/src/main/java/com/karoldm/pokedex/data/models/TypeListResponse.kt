package com.karoldm.pokedex.data.models

import com.squareup.moshi.Json


data class PokemonType(
    @Json(name="name") val name: String
)

data class TypeListResponse(
    @Json(name="results") val result: List<PokemonType>
)