package com.karoldm.pokedex.data.models.api

import com.squareup.moshi.Json

data class PokemonItem(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

data class PokemonsResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<PokemonItem>
)
