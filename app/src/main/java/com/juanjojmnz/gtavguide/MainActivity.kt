package com.juanjojmnz.gtavguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.gtavguide.navigation.GTANavGraph
import com.juanjojmnz.gtavguide.ui.theme.GTAVGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GTAVGuideTheme {
                val navController = rememberNavController()
                GTANavGraph(navController = navController)
            }
        }
    }
}