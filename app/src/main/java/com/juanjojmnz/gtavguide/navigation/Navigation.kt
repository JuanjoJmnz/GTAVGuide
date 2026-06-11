package com.juanjojmnz.gtavguide.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.gtavguide.ui.screens.HomeScreen
import com.juanjojmnz.gtavguide.ui.screens.CheatsScreen
import com.juanjojmnz.gtavguide.ui.screens.HundredPercentScreen
import com.juanjojmnz.gtavguide.ui.screens.MissionsScreen
import com.juanjojmnz.gtavguide.ui.screens.StrangersScreen
import com.juanjojmnz.gtavguide.ui.screens.TemporalScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
}

@Composable
fun GTANavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable("hundred_percent") {
            HundredPercentScreen(navController = navController)
        }
        composable("missions") {
            MissionsScreen(navController = navController)
        }
        composable("strangers") {
            StrangersScreen(navController = navController)
        }
        composable("heists") {
            TemporalScreen(navController = navController)
        }
        composable("lester") {
            TemporalScreen(navController = navController)
        }
        composable("collectibles") {
            TemporalScreen(navController = navController)
        }
        composable("activities") {
            TemporalScreen(navController = navController)
        }
        composable("secondary") {
            TemporalScreen(navController = navController)
        }
        composable("races") {
            TemporalScreen(navController = navController)
        }
        composable("events") {
            TemporalScreen(navController = navController)
        }
        composable("properties") {
            TemporalScreen(navController = navController)
        }
        composable("map") {
            TemporalScreen(navController = navController)
        }
        composable("curiosities") {
            TemporalScreen(navController = navController)
        }
        composable("cheats") {
            CheatsScreen(navController = navController)
        }
        composable("online") {
            TemporalScreen(navController = navController)
        }
        composable("about") {
            TemporalScreen(navController = navController)
        }
    }
}