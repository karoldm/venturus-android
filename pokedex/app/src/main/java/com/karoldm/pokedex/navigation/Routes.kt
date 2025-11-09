package com.karoldm.pokedex.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Details : Screen("details/{name}") {
        fun route(name: String) = "details/$name"
    }
}