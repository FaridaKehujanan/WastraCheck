package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.wastracheck.ui.theme.TextGray

data class WastraMotif(val id: String, val name: String, val origin: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController) {
    val motifs = listOf(
        WastraMotif("1", "Parang Kusumo", "Solo", "Melambangkan perjuangan jiwa melawan hawa nafsu."),
        WastraMotif("2", "Mega Mendung", "Cirebon", "Melambangkan dunia atas yang luas dan bebas."),
        WastraMotif("3", "Sido Mukti", "Solo", "Harapan agar pemakainya mencapai kebahagiaan lahir batin."),
        WastraMotif("4", "Sekar Jagad", "Yogyakarta", "Melambangkan keragaman suku bangsa di dunia."),
        WastraMotif("5", "Kawung", "Yogyakarta", "Melambangkan keadilan dan keperkasaan."),
        WastraMotif("6", "Truntum", "Solo", "Melambangkan cinta yang tumbuh kembali."),
        WastraMotif("7", "Sidoluhur", "Solo", "Melambangkan harapan untuk menjadi teladan."),
        WastraMotif("8", "Slobog", "Solo", "Melambangkan keteguhan hati."),
        WastraMotif("9", "Pring Sedapur", "Magetan", "Melambangkan persatuan dan kerukunan."),
        WastraMotif("10", "Sidomulyo", "Solo", "Melambangkan kemuliaan dan hidup berkecukupan.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Wastra", fontWeight = FontWeight.Bold, color = BrownPrimary) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Explore, null) },
                    label = { Text("EXPLORE") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CenterFocusStrong, null) },
                    label = { Text("SCAN") },
                    selected = false,
                    onClick = { navController.navigate("scan") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, null) },
                    label = { Text("LIBRARY") },
                    selected = false,
                    onClick = { navController.navigate("library") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("PROFILE") },
                    selected = false,
                    onClick = { navController.navigate("profile") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Discover Heritage",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownPrimary
                )
                Text(
                    "Explore the beauty of Indonesian motifs",
                    fontSize = 14.sp,
                    color = TextGray
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(motifs) { motif ->
                    MotifCard(motif) {
                        navController.navigate("encyclopedia_detail")
                    }
                }
            }
        }
    }
}

@Composable
fun MotifCard(motif: WastraMotif, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(BrownPrimary.copy(alpha = 0.1f))
            ) {
                Icon(
                    Icons.Default.Pattern,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center).size(60.dp),
                    tint = BrownPrimary.copy(alpha = 0.3f)
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = motif.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = BrownPrimary,
                    maxLines = 1
                )
                Text(
                    text = motif.origin,
                    fontSize = 12.sp,
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = motif.description,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    lineHeight = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    ExploreScreen(rememberNavController())
}
