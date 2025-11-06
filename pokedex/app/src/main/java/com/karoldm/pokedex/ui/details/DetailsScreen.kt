package com.karoldm.pokedex.ui.details

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.karoldm.pokedex.data.repositories.PokedexRepository
import com.karoldm.pokedex.factories.SimpleViewModelFactory
import com.karoldm.pokedex.ui.home.DetailsViewModel


@Composable
fun DetailsScreen(navController: NavController, id: String) {

    val repository = PokedexRepository()

    val viewModel: DetailsViewModel = viewModel(
        factory = SimpleViewModelFactory { DetailsViewModel(repository) }
    )

    val pokemon by viewModel.pokemon.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getPokemon(id)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Text("pokemon id " + id)
            Text(pokemon?.name ?: "pokemon is undefined")
        }
    }
}
