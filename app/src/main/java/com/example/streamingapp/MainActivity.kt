package com.example.streamingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Stream
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.streamingapp.ui.theme.StreamingAppTheme
import com.example.streamingapp.views.perplexity.PerplexityScreen
import com.example.streamingapp.views.perplexity.PerplexityViewModel
import com.example.streamingapp.views.streaming.StreamingScreen
import com.example.streamingapp.views.streaming.StreamingViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.WebSocket
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Inject the WebSocket to force Hilt to create it.
    @Inject
    lateinit var webSocket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log to verify that the WebSocket instance is created.
        Log.d("Priyanshu", "WebSocket injected: $webSocket")
        setContent {
            StreamingAppTheme {
                MainScreen()
            }
        }
    }
}

// Navigation Screens
sealed class Screen(val route: String, val title: String) {
    object Streaming : Screen("streaming", "Streaming")
    object Perplexity : Screen("perplexity", "Perplexity")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val currentRoute = currentRoute(navController)
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Stream, contentDescription = "Streaming") },
                    label = { Text("Streaming") },
                    selected = currentRoute == Screen.Streaming.route,
                    onClick = { navController.navigate(Screen.Streaming.route) }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Calculate, contentDescription = "Perplexity") },
                    label = { Text("Perplexity") },
                    selected = currentRoute == Screen.Perplexity.route,
                    onClick = { navController.navigate(Screen.Perplexity.route) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Streaming.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Streaming.route) {
                val streamingViewModel: StreamingViewModel = hiltViewModel()
                StreamingScreen(viewModel = streamingViewModel)
            }
            composable(Screen.Perplexity.route) {
                // For simplicity, we use the default viewModel() here.
                val perplexityViewModel: PerplexityViewModel =
                    androidx.lifecycle.viewmodel.compose.viewModel()
                PerplexityScreen(viewModel = perplexityViewModel)
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
