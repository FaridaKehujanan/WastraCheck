package com.example.wastracheck.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wastracheck.AuthState
import com.example.wastracheck.AuthViewModel
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate("explore") {
                    popUpTo("login") { inclusive = true }
                }
                authViewModel.resetState()
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                authViewModel.resetState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(BrownPrimary)
                .clickable { navController.navigate("admin_login") },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "WASTRA-CHECK", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                Text(text = "Auntetikasi Warisan Budaya Melalui AI", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Selamat Datang Kembali", fontSize = 24.sp, fontWeight = FontWeight.Black, color = BrownPrimary)
            Text(text = "Temukan Kisah Yang Terjalin di Setiap Helai Benang.", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(32.dp))

            // Email Field dengan Font Paling Bold (Black/W900)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Alamat Email", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, null, tint = BrownPrimary) },
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.Black),
                    placeholder = { Text("pengguna@gmail.com", fontWeight = FontWeight.Bold, color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrownPrimary,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = BrownPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field dengan Font Paling Bold (Black/W900)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Kata Sandi", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    TextButton(onClick = { }) { Text("Lupa Kata Sandi?", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Black) }
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = BrownPrimary) },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.Black),
                    placeholder = { Text("********", fontWeight = FontWeight.Bold, color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrownPrimary,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = BrownPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { 
                    if (email.isNotBlank() && password.isNotBlank()) authViewModel.login(email, password)
                    else Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                shape = RoundedCornerShape(12.dp),
                enabled = authState != AuthState.Loading
            ) {
                if (authState == AuthState.Loading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("Masuk", fontSize = 18.sp, fontWeight = FontWeight.Black)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    append("belum punya akun? ")
                    withStyle(style = SpanStyle(color = BrownPrimary, fontWeight = FontWeight.Black)) { append("Daftar Sekarang") }
                },
                modifier = Modifier.clickable { navController.navigate("Daftar") },
                fontSize = 14.sp, color = Color.Gray
            )
        }
    }
}
