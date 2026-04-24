package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncyclopediaDetailScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("WASTRA-CHECK", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BrownPrimary) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = BrownPrimary) } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = BrownPrimary) } },
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
        ) {
            // Main Image with Header
            Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(Color.DarkGray)) {
                Column(
                    modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
                ) {
                    Surface(color = Color.Black.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp)) {
                        Text("Keraton Yogyakarta", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = Color.White, fontSize = 10.sp)
                    }
                    Text("Parang Rusak", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Chips
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SuggestionChip(onClick = {}, label = { Text("CENTRAL JAVA", fontSize = 10.sp) })
                    SuggestionChip(onClick = {}, label = { Text("16TH CENTURY", fontSize = 10.sp) })
                    SuggestionChip(onClick = {}, label = { Text("NOBILITY", fontSize = 10.sp) })
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Philosophical Meaning", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BrownPrimary)
                Spacer(modifier = Modifier.height(12.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF7F5))) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Waves, contentDescription = null, tint = BrownPrimary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "Representing the eternal struggle between human resilience and evil, the slanted 'S' shape symbolizes a continuous wave that never breaks.",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = BrownPrimary)
                            Text("PROTECTION & STRENGTH", textAlign = TextAlign.Center, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.PriorityHigh, contentDescription = null, tint = BrownPrimary)
                            Text("NOBLE STATUS", textAlign = TextAlign.Center, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("The History", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BrownPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "The Parang motif is one of the oldest batik motifs in Indonesia. Originating from the Mataram Kingdom era, it was once reserved exclusively for the royal family and nobility...",
                    fontSize = 14.sp, color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text("Traditional Usage", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BrownPrimary)
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    UsageItem("Royal Weddings", "Used by the groom and bride to symbolize a strong foundation.")
                    UsageItem("Sacred Dances", "Commonly worn during traditional dances in the Keraton.")
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        
        // FAB
        Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(onClick = {}, containerColor = BrownPrimary, contentColor = Color.White) {
                Icon(Icons.Default.Bookmark, contentDescription = null)
            }
        }
    }
}

@Composable
fun UsageItem(title: String, desc: String) {
    Column(modifier = Modifier.width(200.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(12.dp)).background(Color.LightGray))
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(desc, fontSize = 12.sp, color = TextGray)
    }
}

@Preview(showBackground = true)
@Composable
fun EncyclopediaDetailScreenPreview() {
    EncyclopediaDetailScreen()
}
