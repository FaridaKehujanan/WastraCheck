package com.example.wastracheck.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wastracheck.AuthViewModel
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var adminEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val loginState by authViewModel.currentUser.collectAsState()

    // Jika login berhasil, pindah ke dashboard admin
    LaunchedEffect(loginState) {
        if (loginState?.email == "admin@gmail.com") {
            navController.navigate("admin_motif") {
                popUpTo("admin_login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
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
                
                Text("ADMIN EMAIL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                OutlinedTextField(
                    value = adminEmail,
                    onValueChange = { adminEmail = it },
                    placeholder = { Text("admin@gmail.com") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("PASSWORD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = { 
                        if (adminEmail.isNotEmpty() && password.isNotEmpty()) {
                            authViewModel.login(adminEmail, password)
                            // Jika bukan admin, beri peringatan
                            if (adminEmail != "admin@gmail.com") {
                                Toast.makeText(context, "Akses Ditolak: Gunakan Akun Admin", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
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
                    text = "Authorized access only. All actions are logged under preservation guidelines.",
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
