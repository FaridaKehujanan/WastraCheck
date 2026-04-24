package com.example.wastracheck.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("WASTRA-CHECK", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BrownPrimary) },
                navigationIcon = { IconButton(onClick = { navController?.navigateUp() }) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrownPrimary) } },
                actions = { IconButton(onClick = { navController?.navigate("profile") }) { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = BrownPrimary) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Main Image Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black)
            ) {
                // Confidence Badge
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Text("CAPTURE CONFIDENCE", color = Color.White, fontSize = 10.sp)
                        Spacer(modifier = Modifier.width(20.dp))
                        Text("98.4%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                SuggestionChip(
                    onClick = {},
                    label = { Text("Verified Motif", fontSize = 10.sp) },
                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFE8EAF6))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("ID: WC-8829", fontSize = 10.sp, color = Color.Gray)
            }
            
            Text(
                text = "Parang Kusumo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = BrownPrimary
            )
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Solo (Surakarta), Central Java", color = Color.Gray, fontSize = 14.sp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Symbolic Meaning Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = BrownPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Symbolic Meaning", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "The word Kusumo means flower. Historically, this sacred motif was reserved for royalty in the Solo courts. It symbolizes the struggle of the soul against worldly desires...",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Buttons
            Button(
                onClick = { navController?.navigate("library") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save to Collection")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.MenuBook, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Learn More")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen()
}
