package com.karoldm.pokedex.data.models.view

import com.karoldm.pokedex.data.models.api.Generation
import com.karoldm.pokedex.data.models.api.PokemonStat
import com.karoldm.pokedex.data.models.api.PokemonType
import com.karoldm.pokedex.data.models.api.Sprites

data class Pokemon(
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val sprites: Sprites,
    val name: String,
    val generation: Generation
)
