package com.example.wastracheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

    // Menggunakan ViewModelFactory tunggal untuk semua ViewModel yang butuh DAO
    val factory = ViewModelFactory(
        userDao = database.userDao(),
        motifDao = database.motifDao()
    )

    val navController = rememberNavController()
    val batikViewModel: BatikAiViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val libraryViewModel: LibraryViewModel = viewModel(factory = factory)
    val exploreViewModel: ExploreViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("explore") { ExploreScreen(navController, exploreViewModel) }
        composable("scan") { ScanScreen(navController, batikViewModel, exploreViewModel) }
        composable("select_textile") { SelectTextileScreen(navController) }
        composable("result") { ResultScreen(navController, batikViewModel) }
        composable("batik_challenge") { BatikChallengeScreen(navController, exploreViewModel) }
        composable(
            "encyclopedia_detail/{motifId}",
            arguments = listOf(navArgument("motifId") { type = NavType.StringType })
        ) { backStackEntry ->
            val motifId = backStackEntry.arguments?.getString("motifId")
            EncyclopediaDetailScreen(navController, motifId, exploreViewModel)
        }
        composable("library") { LibraryScreen(navController, libraryViewModel) }
        composable("profile") { ProfileScreen(navController, authViewModel) }
        composable("edit_profile") { EditProfileScreen(navController) }
        composable("notifications") { NotificationSettingsScreen(navController) }
        composable("language") { LanguageSettingsScreen(navController) }
        composable("privacy_policy") { PrivacyPolicyScreen(navController) }
    }
}