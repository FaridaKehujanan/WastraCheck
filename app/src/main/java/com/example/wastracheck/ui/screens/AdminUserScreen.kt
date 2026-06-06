package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Wastra-Check Admin", fontSize = 16.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Surface(shape = CircleShape, color = BrownPrimary.copy(alpha = 0.1f), modifier = Modifier.size(32.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("AU", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BrownPrimary)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Dashboard, null) },
                    label = { Text("DASHBOARD") },
                    selected = false,
                    onClick = { /* TODO */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Palette, null) },
                    label = { Text("MOTIFS") },
                    selected = false,
                    onClick = { navController.navigate("admin_motif") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Group, null) },
                    label = { Text("USERS") },
                    selected = true,
                    onClick = { }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
                .padding(16.dp)
        ) {
            Text("User Management", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Control access levels and monitor community curator activity.", fontSize = 14.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.FileDownload, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Export List")
                }
                Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary)) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Invite User")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Stat Cards
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AdminStatCard("TOTAL USERS", "1,284", "+12%", Color(0xFF4CAF50), Icons.Default.Groups, Modifier.weight(1f))
                AdminStatCard("CURATORS", "42", "Stable", Color.Gray, Icons.Default.VerifiedUser, Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search & List
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search users by name or email...") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(10) {
                            UserItem()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminStatCard(label: String, value: String, trend: String, trendColor: Color, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(icon, contentDescription = null, tint = BrownPrimary, modifier = Modifier.size(20.dp))
                Text(trend, color = trendColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 10.sp, color = Color.Gray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun UserItem() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(40.dp)) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Siti Rahayu", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("siti.rahayu@museum.id", fontSize = 12.sp, color = Color.Gray)
        }
        SuggestionChip(
            onClick = {},
            label = { Text("CURATOR", fontSize = 10.sp) },
            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFE8EAF6), labelColor = Color(0xFF3F51B5))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdminUserScreenPreview() {
    AdminUserScreen(rememberNavController())
}
