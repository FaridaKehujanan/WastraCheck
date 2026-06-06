package com.example.wastracheck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(navController: NavController) {
    var adminId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        // Admin Icon
        Surface(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(16.dp),
            color = BrownPrimary
        ) {
            Icon(
                Icons.Default.Shield,
                contentDescription = null,
                modifier = Modifier.padding(16.dp),
                tint = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Wastra-Check Admin",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = BrownPrimary
        )
        Text(
            text = "Cultural Heritage Preservation Portal",
            fontSize = 14.sp,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Secure Login",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownPrimary
                )
                Text(
                    text = "Access the museum curator management system.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("ADMIN ID / EMAIL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                OutlinedTextField(
                    value = adminId,
                    onValueChange = { adminId = it },
                    placeholder = { Text("Enter your credentials") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("PASSWORD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Text("Forgot password?", fontSize = 12.sp, color = Color(0xFF4F5CBF))
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Visibility, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = { 
                        // Simple logic: navigate if not empty
                        if (adminId.isNotEmpty()) {
                            navController.navigate("admin_motif")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Authenticate", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Authorized access only. All actions are logged under federal preservation guidelines.",
                    fontSize = 10.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).background(Color(0xFF00C853), CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Text("System Gateway Secure", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        }
        Text("v2.4.0 • © 2024 Wastra-Check Technology", fontSize = 10.sp, color = Color.LightGray, modifier = Modifier.padding(bottom = 24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AdminLoginScreenPreview() {
    AdminLoginScreen(rememberNavController())
}
