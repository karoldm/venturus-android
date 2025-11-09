package com.karoldm.pokedex.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.karoldm.pokedex.data.repositories.PokedexRepository
import com.karoldm.pokedex.factories.SimpleViewModelFactory
import com.karoldm.pokedex.navigation.Screen
import com.karoldm.pokedex.ui.components.FilterMenu
import com.karoldm.pokedex.ui.components.PokemonCard
import com.karoldm.pokedex.ui.theme.red
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.BatteryStd
import com.karoldm.pokedex.ui.theme.black
import com.karoldm.pokedex.ui.theme.off_white

@Composable
fun HomeScreen(navController: NavController) {
    val repository = PokedexRepository()
    val viewModel: HomeViewModel = viewModel(
        factory = SimpleViewModelFactory { HomeViewModel(repository) }
    )

    val pokemons by viewModel.pokemons.collectAsState()
    val typeList by viewModel.typeList.collectAsState()
    val genList by viewModel.genList.collectAsState()
    val loading by viewModel.loading.collectAsState()

    var search by remember { mutableStateOf("") }
    var genMenuExpanded by remember { mutableStateOf(false) }
    var typeMenuExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = off_white

    ) {
        if (loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = red)
            }
        } else {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = search,
                        onValueChange = { search = it },
                        label = { Text("Search by name") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp),
                        onClick = { viewModel.filter(Filter(name = search.ifBlank { null })) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = red
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "App Logo",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterMenu(
                        label = "Generation",
                        expanded = genMenuExpanded,
                        onExpandChange = { genMenuExpanded = it },
                        items = genList.map { it.name },
                        onSelect = {
                            viewModel.filter(Filter(gen = it))
                            genMenuExpanded = false
                        }
                    )
                    FilterMenu(
                        label = "Type",
                        expanded = typeMenuExpanded,
                        onExpandChange = { typeMenuExpanded = it },
                        items = typeList.map { it.name },
                        onSelect = {
                            viewModel.filter(Filter(type = it))
                            typeMenuExpanded = false
                        }
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "${pokemons.size} Pokémon(s)",
                    style = MaterialTheme.typography.bodyLarge.copy(color = black)
                )

                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp),
                ) {
                    items(
                        count = pokemons.size,
                        key = { pokemons[it].name }
                    ) { index ->
                        PokemonCard(
                            pokemon = pokemons[index],
                            onClick = {
                                navController.navigate(
                                    Screen.Details.route(pokemons[index].name)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    MaterialTheme {
        HomeScreen(navController)
    }
}