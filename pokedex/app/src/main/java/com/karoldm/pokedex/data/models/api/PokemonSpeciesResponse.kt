package com.karoldm.pokedex.data.models.api

import com.squareup.moshi.Json

data class PokemonDetailsSpecie(
    @Json(name="url") val url: String,
    @Json(name="name") val name: String,
)

data class PokemonSpecies(
    @Json(name="generation") val generation: Generation,
)