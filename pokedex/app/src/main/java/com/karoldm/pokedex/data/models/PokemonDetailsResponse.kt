package com.karoldm.pokedex.data.models

import com.squareup.moshi.Json

data class PokemonStat(
    @Json(name="base_stat") val baseStat: String,
    @Json(name="stat.name") val statName: String,
)

data class PokemonType(
    @Json(name="slot")  val slot: Int,
    @Json(name="type.name") val type: String,
)

data class PokemonDetailsResponse(
    @Json(name="weight") val weight: Int,
    @Json(name="height") val height: Int,
    @Json(name="types") val types: List<PokemonType>,
    @Json(name="stats") val stats: List<PokemonStat>,
    @Json(name="sprites.front_default") val sprite: String,
)
