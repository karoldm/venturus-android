package com.karoldm.pokedex.data.models.api

import com.squareup.moshi.Json

data class GenerationsResponse(
    @Json(name="count") val count: Int,
    @Json(name="results") val results: List<Generation>
)

data class Generation(
    @Json(name = "name") val name: String
)
