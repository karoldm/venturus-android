package com.karoldm.pokedex.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.karoldm.pokedex.ui.details.DetailsScreen
import com.karoldm.pokedex.ui.home.HomeScreen
import com.karoldm.pokedex.ui.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = Screen.Splash.route,
        modifier = modifier,
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(
            Screen.Details.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) {
            val name = it.arguments?.getString("name")!!
            DetailsScreen(navController, name)
        }
    }
}
