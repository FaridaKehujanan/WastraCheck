package com.example.wastracheck.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.AuthViewModel
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.Menu, contentDescription = null) } },
                actions = { IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Settings, contentDescription = null) } }
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
                    selected = false,
                    onClick = { navController.navigate("library") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.EmojiEvents, null) },
                    label = { Text("TANTANGAN") },
                    selected = true,
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Picture
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = Color.LightGray,
                    border = BorderStroke(4.dp, BrownPrimary.copy(alpha = 0.1f))
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(20.dp),
                        tint = Color.Gray
                    )
                }
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    color = Color(0xFF4F5CBF),
                    shadowElevation = 4.dp
                ) {
                    Icon(
                        Icons.Default.Verified,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentUser?.name ?: "Guest",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = currentUser?.email ?: "Not logged in",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat("12", "Saved Motifs")
                VerticalDivider(modifier = Modifier.height(40.dp))
                ProfileStat("5", "Scans Today")
                VerticalDivider(modifier = Modifier.height(40.dp))
                ProfileStat("2", "Level")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F6FF)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = CircleShape, color = Color(0xFF4F5CBF), modifier = Modifier.size(40.dp)) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color.White, modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Heritage Level 2", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                        Text("450 points until Master Curator status", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { 0.6f },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = Color(0xFF4F5CBF),
                            trackColor = Color(0xFFD0D4F5)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Settings Sections
            SettingsSection("Account Preferences") {
                SettingsItem(
                    icon = Icons.Default.PersonOutline, 
                    title = "Edit Profile",
                    onClick = { navController.navigate("edit_profile") }
                )
                SettingsItem(
                    icon = Icons.Default.NotificationsNone, 
                    title = "Notification Settings",
                    onClick = { navController.navigate("notifications") }
                )
                SettingsItem(
                    icon = Icons.Default.Language, 
                    title = "Language", 
                    subtitle = "Bahasa Indonesia",
                    onClick = { navController.navigate("language") }
                )
            }

            SettingsSection("Legal & Security") {
                SettingsItem(
                    icon = Icons.Default.PrivacyTip, 
                    title = "Privacy Policy",
                    onClick = { navController.navigate("privacy_policy") }
                )
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.Logout, 
                    title = "Logout",
                    textColor = Color(0xFFFF1744), // Brighter Red
                    onClick = {
                        authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF4F5CBF))
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector, 
    title: String, 
    subtitle: String? = null, 
    textColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = if (textColor == Color.Black) Color.DarkGray else textColor)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, color = textColor, fontWeight = FontWeight.Medium)
            if (subtitle != null) {
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.LightGray)
    }
}
