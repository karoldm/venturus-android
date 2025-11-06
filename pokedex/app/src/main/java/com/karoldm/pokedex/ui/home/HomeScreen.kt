package com.karoldm.pokedex.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.karoldm.pokedex.data.repositories.PokedexRepository
import com.karoldm.pokedex.factories.SimpleViewModelFactory
import com.karoldm.pokedex.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    var search by remember { mutableStateOf("") }
    var genMenuExpanded by remember { mutableStateOf(false) }
    var typeMenuExpanded by remember { mutableStateOf(false) }

    val repository = PokedexRepository()

    val viewModel: HomeViewModel = viewModel(
        factory = SimpleViewModelFactory { HomeViewModel(repository) }
    )

    val pokemons by viewModel.pokemons.observeAsState(initial = emptyList())
    val typeList by viewModel.typeList.observeAsState(initial = emptyList())
    val genCount by viewModel.genCount.observeAsState(initial = 0)


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Row {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Pesquisar por nome") }
                )
                Button(
                    onClick = {}
                ) { Text("Pesquisar") }
            }

            Row {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    IconButton(onClick = { genMenuExpanded = !genMenuExpanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "filter by gen")
                    }
                    DropdownMenu(
                        expanded = genMenuExpanded,
                        onDismissRequest = { genMenuExpanded = false }
                    ) {
                        genCount.let { count ->
                            (1..count).forEach { num ->
                                DropdownMenuItem(
                                    text = { Text(num.toString()) },
                                    onClick = { /* Do something... */ }
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    IconButton(onClick = { typeMenuExpanded = !typeMenuExpanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "filter by type")
                    }
                    DropdownMenu(
                        expanded = typeMenuExpanded,
                        onDismissRequest = { typeMenuExpanded = false }
                    ) {
                        typeList.let { typeList ->
                            typeList.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.name) },
                                    onClick = { /* Do something... */ }
                                )
                            }
                        }
                    }
                }
            }

            pokemons.let { pokemons ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pokemons.size) { index ->
                        Button(
                            onClick = {
                                val url = pokemons[index].url
                                val id = url.trimEnd('/').substringAfterLast('/')
                                navController.navigate(Screen.Details.route(id))
                            }
                        ) {
                            Text(pokemons[index].name)
                        }
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