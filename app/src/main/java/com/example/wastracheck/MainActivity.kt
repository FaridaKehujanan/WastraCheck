package com.example.wastracheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.ui.screens.*
import com.example.wastracheck.ui.theme.WastraCheckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WastraCheckTheme {
                WastraNavHost()
            }
        }
    }
}

@Composable
fun WastraNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("scan") { ScanScreen(navController) }
        composable("result") { ResultScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("admin_login") { AdminLoginScreen(navController) }
        composable("admin_motif") { AdminMotifScreen(navController) }
        // Untuk screen lain jika parameternya sudah ditambahkan
        composable("library") { LibraryScreen() }
        composable("select_textile") { SelectTextileScreen() }
        composable("admin_user") { AdminUserScreen() }
    }
}