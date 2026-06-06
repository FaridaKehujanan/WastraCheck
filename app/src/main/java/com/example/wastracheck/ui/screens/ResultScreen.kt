package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastracheck.AiUiState
import com.example.wastracheck.BatikAiViewModel
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavController, viewModel: BatikAiViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HASIL IDENTIFIKASI", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { 
                        viewModel.resetState()
                        navController.popBackStack() 
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share logic */ }) {
                        Icon(Icons.Default.Share, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(padding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is AiUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = BrownPrimary)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Gemini sedang menganalisis motif...", color = Color.Gray)
                        }
                    }
                }
                is AiUiState.Success -> {
                    Card(
                        modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "Analisis AI Selesai",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = BrownPrimary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = state.result,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                    
                    Button(
                        onClick = { 
                            viewModel.resetState()
                            navController.navigate("explore") {
                                popUpTo("explore") { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(20.dp).fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("KEMBALI KE BERANDA")
                    }
                }
                is AiUiState.Error -> {
                    Column(
                        modifier = Modifier.padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Waduh!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(state.message, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { navController.popBackStack() }) {
                            Text("COBA LAGI")
                        }
                    }
                }
                else -> {
                    // Idle state
                }
            }
        }
    }
}
