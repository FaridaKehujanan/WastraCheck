package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text("Privacy Policy", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BrownPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Your privacy is important to us. It is WastraCheck's policy to respect your privacy regarding any information we may collect from you through our app.\n\n" +
                "1. Information we collect\n" +
                "We only ask for personal information when we truly need it to provide a service to you.\n\n" +
                "2. Use of information\n" +
                "We use collected information to provide, maintain and improve our services.\n\n" +
                "3. Data Storage\n" +
                "We only retain collected information for as long as necessary to provide you with your requested service.",
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}
