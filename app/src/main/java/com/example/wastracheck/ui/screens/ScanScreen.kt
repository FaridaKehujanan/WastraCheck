package com.example.wastracheck.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastracheck.ui.theme.BrownPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(navController: NavController? = null) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview Placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            // Pattern Overlay (Simulated)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FilterCenterFocus,
                    contentDescription = null,
                    modifier = Modifier.size(280.dp),
                    tint = Color.White.copy(alpha = 0.3f)
                )
            }
        }

        // Top Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "WASTRA-CHECK",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrownPrimary)
                }
            },
            actions = {
                IconButton(onClick = { navController?.navigate("profile") }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = BrownPrimary)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )

        // Floating UI Elements
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Identifying Badge
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFE57373), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("IDENTIFYING...", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Camera Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 120.dp)
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flash Toggle
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.size(50.dp)
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.FlashOff, contentDescription = null, tint = Color.White)
                    }
                }

                // Shutter Button
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier.size(80.dp),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(70.dp).border(2.dp, Color.Gray, CircleShape)
                        ) {
                            IconButton(onClick = { navController?.navigate("result") }) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = BrownPrimary, modifier = Modifier.padding(15.dp))
                            }
                        }
                    }
                }

                // Info
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.size(50.dp)
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }
        
        // Gallery Shortcut (Left)
        Surface(
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp).size(60.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.Black.copy(alpha = 0.3f),
            border = BorderStroke(1.dp, Color.White)
        ) {
             IconButton(onClick = { navController?.navigate("library") }) {
                 Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Color.White, modifier = Modifier.padding(12.dp))
             }
        }
    }
}

@Preview
@Composable
fun ScanScreenPreview() {
    ScanScreen()
}
