package com.example.wastracheck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastracheck.LibraryViewModel
import com.example.wastracheck.data.WastraMotif
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    val savedMotifs by viewModel.savedMotifs.collectAsState()
    val selectedRegion by viewModel.selectedRegion.collectAsState()

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
                    label = { Text("JELAJAH") },
                    selected = false,
                    onClick = { navController.navigate("explore") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CenterFocusStrong, null) },
                    label = { Text("PINDAI") },
                    selected = false,
                    onClick = { navController.navigate("scan") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, null) },
                    label = { Text("KOLEKSI") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.EmojiEvents, null) },
                    label = { Text("TANTANGAN") },
                    selected = false,
                    onClick = { navController.navigate("batik_challenge") }
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
            Text("Koleksi Saya", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Kumpulan warisan budaya Indonesia pilihan Anda.", fontSize = 14.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Filter Berdasarkan Wilayah (Logic Room)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val regions = listOf("Semua", "Jawa Barat", "Jawa Tengah", "Jawa Timur")
                regions.forEach { region ->
                    FilterChip(
                        selected = selectedRegion == region,
                        onClick = { viewModel.setRegion(region) },
                        label = { Text(region) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BrownPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (savedMotifs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada koleksi di wilayah ini", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(savedMotifs, key = { it.id }) { motif ->
                        SavedMotifCard(motif) {
                            navController.navigate("encyclopedia_detail/${motif.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedMotifCard(motif: WastraMotif, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(120.dp).background(Color.LightGray)) {
                if (motif.imageRes != null) {
                    Image(
                        painter = painterResource(id = motif.imageRes),
                        contentDescription = motif.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = motif.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Text(
                    text = motif.region,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
