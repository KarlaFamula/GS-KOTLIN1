package com.github.karlaeisaque.gs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.karlaeisaque.gs.screens.AboutScreen
import com.github.karlaeisaque.gs.screens.DetailScreen
import com.github.karlaeisaque.gs.screens.EventsScreen
import com.github.karlaeisaque.gs.screens.HomeScreen
import com.github.karlaeisaque.gs.screens.SatellitesScreen
import com.github.karlaeisaque.gs.ui.theme.SpaceMonitorTheme
import com.github.karlaeisaque.gs.viewmodel.SpaceViewModel


class MainActivity : ComponentActivity() {


    private val viewModel: SpaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceMonitorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {

                        // ── Tela 1: Home ──────────────────────────────
                        composable(route = "home") {
                            HomeScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        // ── Tela 2: Lista de Satélites ────────────────
                        composable(route = "satellites") {
                            SatellitesScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        // ── Tela 3: Detalhes do Satélite (com argumento)
                        composable(
                            route = "detail/{satelliteId}",
                            arguments = listOf(
                                navArgument("satelliteId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val satelliteId = backStackEntry.arguments?.getInt("satelliteId") ?: 1
                            DetailScreen(
                                navController = navController,
                                viewModel = viewModel,
                                satelliteId = satelliteId
                            )
                        }

                        // ── Tela 4: Eventos Espaciais ─────────────────
                        composable(route = "events") {
                            EventsScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        // ── Tela 5: Sobre o Projeto ───────────────────
                        composable(route = "about") {
                            AboutScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
