package com.karoldm.pokedex.data.models

import com.squareup.moshi.Json

data class GenerationResponse(
    @Json(name="count") val count: Int
)