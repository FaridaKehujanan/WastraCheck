package com.example.wastracheck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.ExploreViewModel
import com.example.wastracheck.data.WastraMotif
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = viewModel()
) {
    val motifs by viewModel.motifs.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            placeholder = { 
                                Text(
                                    "Cari batik...", 
                                    fontSize = 16.sp, 
                                    color = Color.Gray 
                                ) 
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = BrownPrimary
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            trailingIcon = {
                                IconButton(onClick = { 
                                    viewModel.onSearchQueryChange("")
                                    isSearchActive = false 
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Close", tint = BrownPrimary)
                                }
                            }
                        )
                    } else {
                        Text("Explore Wastra", fontWeight = FontWeight.Bold, color = BrownPrimary)
                    }
                },
                actions = {
                    if (!isSearchActive) {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = BrownPrimary)
                        }
                    }
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = BrownPrimary)
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
                    icon = { Icon(Icons.Default.EmojiEvents, null) },
                    label = { Text("CHALLENGE") },
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
        ) {
            if (!isSearchActive) {
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
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BrownPrimary)
                }
            } else if (error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error!!, color = Color.Red)
                }
            } else if (motifs.isEmpty() && searchQuery.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.SearchOff, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Motif '$searchQuery' tidak ditemukan", color = Color.Gray)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(motifs, key = { it.id }) { motif ->
                        MotifCard(motif) {
                            navController.navigate("encyclopedia_detail/${motif.id}")
                        }
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
                if (motif.imageRes != null) {
                    Image(
                        painter = painterResource(id = motif.imageRes),
                        contentDescription = motif.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Pattern,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).size(60.dp),
                        tint = BrownPrimary.copy(alpha = 0.3f)
                    )
                }
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
