package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
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
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("WASTRA-CHECK", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BrownPrimary) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = BrownPrimary) } },
                actions = { IconButton(onClick = { navController.navigate("profile") }) { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = BrownPrimary) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Explore, null) },
                    label = { Text("EXPLORE") },
                    selected = false,
                    onClick = { navController.navigate("encyclopedia_detail") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CenterFocusStrong, null) },
                    label = { Text("SCAN") },
                    selected = false,
                    onClick = { navController.navigate("scan") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.LibraryBooks, null) },
                    label = { Text("LIBRARY") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, null) },
                    label = { Text("ENCYCLOPEDIA") },
                    selected = false,
                    onClick = { navController.navigate("encyclopedia_detail") }
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
            Text("Library", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Your curated collection of Indonesian heritage.", fontSize = 14.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = true,
                    onClick = {},
                    label = { Text("All Collections") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = BrownPrimary, selectedLabelColor = Color.White)
                )
                FilterChip(selected = false, onClick = {}, label = { Text("Central Java") })
                FilterChip(selected = false, onClick = {}, label = { Text("West Java") })
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(6) { index ->
                    Card(
                        modifier = Modifier.clickable { navController.navigate("encyclopedia_detail") },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column {
                            Box(modifier = Modifier.fillMaxWidth().height(120.dp).background(Color.LightGray))
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Parang Rusak", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Oct 12, 2023", fontSize = 10.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    LibraryScreen(rememberNavController())
}
