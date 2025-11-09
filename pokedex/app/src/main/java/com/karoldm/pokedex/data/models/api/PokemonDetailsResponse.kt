package com.karoldm.pokedex.data.models.api

import com.squareup.moshi.Json

data class PokemonDetails(
    @Json(name = "weight") val weight: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "types") val types: List<PokemonTypeSlot>,
    @Json(name = "stats") val stats: List<PokemonStat>,
    @Json(name = "sprites") val sprites: Sprites,
    @Json(name = "name") val name: String,
    @Json(name="species") val species: PokemonDetailsSpecie
)

data class PokemonTypeSlot(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val type: PokemonType
)

data class PokemonStat(
    @Json(name = "base_stat") val baseStat: Int,
    @Json(name = "effort") val effort: Int,
    @Json(name = "stat") val stat: StatInfo
)

data class StatInfo(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

data class Sprites(
    @Json(name = "front_default") val frontDefault: String?
)
