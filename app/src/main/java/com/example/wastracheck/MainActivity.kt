package com.example.wastracheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.data.WastraDatabase
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
    val context = LocalContext.current
    val database = WastraDatabase.getDatabase(context)
    val factory = AuthViewModelFactory(database.userDao())
    
    val navController = rememberNavController()
    val batikViewModel: BatikAiViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(factory = factory)
    
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("explore") { ExploreScreen(navController) }
        composable("scan") { ScanScreen(navController, batikViewModel) }
        composable("select_textile") { SelectTextileScreen(navController) }
        composable("result") { ResultScreen(navController, batikViewModel) }
        composable("encyclopedia_detail") { EncyclopediaDetailScreen(navController) }
        composable("library") { LibraryScreen(navController) }
        composable("profile") { ProfileScreen(navController, authViewModel) }
        composable("admin_login") { AdminLoginScreen(navController) }
        composable("admin_motif") { AdminMotifScreen(navController) }
        composable("admin_user") { AdminUserScreen(navController) }
    }
}
