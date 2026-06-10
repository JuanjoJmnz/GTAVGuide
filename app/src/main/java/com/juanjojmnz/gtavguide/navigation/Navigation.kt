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
        composable("cheats") {
            CheatsScreen(navController = navController)
        }
    }
}