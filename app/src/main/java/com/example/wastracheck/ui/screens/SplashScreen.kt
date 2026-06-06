package com.example.wastracheck.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.TextGray
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Logic: Delay 3 seconds then navigate to Login
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // 1. Decorative Frame and Brackets
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            val frameStrokeWidth = 1.dp.toPx()
            val bracketStrokeWidth = 4.dp.toPx()
            val bracketSize = 32.dp.toPx()
            val blueColor = Color(0xFF4F5CBF)
            val lightBlueFrame = blueColor.copy(alpha = 0.15f)

            drawRect(
                color = lightBlueFrame,
                topLeft = Offset.Zero,
                size = size,
                style = Stroke(width = frameStrokeWidth)
            )

            drawLine(color = blueColor, start = Offset(0f, 0f), end = Offset(0f, bracketSize), strokeWidth = bracketStrokeWidth)
            drawLine(color = blueColor, start = Offset(0f, 0f), end = Offset(bracketSize, 0f), strokeWidth = bracketStrokeWidth)
            drawLine(color = blueColor, start = Offset(size.width, size.height), end = Offset(size.width, size.height - bracketSize), strokeWidth = bracketStrokeWidth)
            drawLine(color = blueColor, start = Offset(size.width, size.height), end = Offset(size.width - bracketSize, size.height), strokeWidth = bracketStrokeWidth)
        }

        // 2. Faded Background Motifs
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(Icons.Default.Cloud, null, Modifier.offset(x = 60.dp, y = 60.dp).size(80.dp), Color.LightGray.copy(alpha = 0.12f))
            Icon(Icons.Default.FilterVintage, null, Modifier.align(Alignment.TopEnd).offset(x = (-30).dp, y = 40.dp).size(100.dp), Color.LightGray.copy(alpha = 0.12f))
            Icon(Icons.Default.FilterVintage, null, Modifier.align(Alignment.CenterStart).offset(x = 20.dp, y = (-180).dp).size(120.dp), Color.LightGray.copy(alpha = 0.08f))
            Icon(Icons.Default.Cloud, null, Modifier.align(Alignment.CenterEnd).offset(x = (-20).dp, y = 140.dp).size(110.dp), Color.LightGray.copy(alpha = 0.08f))
            Icon(Icons.Default.FilterVintage, null, Modifier.align(Alignment.BottomStart).offset(x = 60.dp, y = (-80).dp).size(90.dp), Color.LightGray.copy(alpha = 0.12f))
        }

        // 3. Main Center Content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Surface(modifier = Modifier.size(130.dp), shape = CircleShape, color = Color.White, shadowElevation = 16.dp) {}
                Surface(modifier = Modifier.size(110.dp), shape = CircleShape, color = BrownPrimary) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.FilterCenterFocus, null, Modifier.size(50.dp), Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
            Text(text = "WASTRA-CHECK", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = BrownPrimary, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Kenali & Lestarikan Wastra\nNusantara", fontSize = 18.sp, color = TextGray, textAlign = TextAlign.Center, lineHeight = 26.sp)
        }

        // 4. Bottom Footer
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.width(220.dp).height(4.dp).clip(RoundedCornerShape(2.dp)).background(Color(0xFF4F5CBF).copy(alpha = 0.15f))) {
                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.35f).background(Color(0xFF4F5CBF)))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(Icons.Default.AutoAwesome, null, Modifier.size(14.dp).offset(x = (-8).dp), Color.LightGray)
                Text(text = "CURATED BY WASTRA\nCURATOR", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.LightGray, textAlign = TextAlign.Center, letterSpacing = 1.sp, lineHeight = 15.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}
