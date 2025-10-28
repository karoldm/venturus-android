package com.karoldm.pokedex.data.models

import com.squareup.moshi.Json

data class PokemonListItem(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

data class PokemonListResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "next") val next: String,
    @Json(name = "previous") val previous: String,
    @Json(name = "results") val results: List<PokemonListItem>
)
