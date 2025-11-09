package com.karoldm.pokedex.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.GlideImage
import com.karoldm.pokedex.data.repositories.PokedexRepository
import com.karoldm.pokedex.factories.SimpleViewModelFactory
import com.karoldm.pokedex.ui.home.DetailsViewModel
import com.karoldm.pokedex.ui.theme.black
import com.karoldm.pokedex.ui.theme.blue
import com.karoldm.pokedex.ui.theme.off_white
import com.karoldm.pokedex.ui.theme.red
import com.karoldm.pokedex.ui.theme.white
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.karoldm.pokedex.ui.components.InfoCard
import com.karoldm.pokedex.ui.components.StatBar
import com.karoldm.pokedex.ui.components.TypeChip
import com.karoldm.pokedex.ui.theme.gold

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(navController: NavController, name: String) {
    val repository = PokedexRepository()
    val viewModel: DetailsViewModel = viewModel(
        factory = SimpleViewModelFactory { DetailsViewModel(repository) }
    )

    val pokemon by viewModel.pokemon.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPokemon(name)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (pokemon == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = red)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(off_white)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pokemon!!.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                pokemon?.let { pokemon ->
                    GlideImage(
                        model = pokemon.sprites.frontDefault,
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .size(256.dp)
                            .clip(CircleShape)
                            .background(off_white)
                            .padding(8.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoCard(title = "Height", value = "${pokemon!!.height / 10.0} m")
                    InfoCard(title = "Weight", value = "${pokemon!!.weight / 10.0} kg")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    pokemon!!.types.forEach { type ->
                        TypeChip(type.name)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Base Stats",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = black
                )
                Spacer(modifier = Modifier.height(8.dp))
                pokemon!!.stats.forEach { stat ->
                    StatBar(statName = stat.stat.name, value = stat.baseStat)
                }
            }
        }
    }
}
