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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate("explore") {
                    popUpTo("register") { inclusive = true }
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
                .height(160.dp)
                .background(BrownPrimary),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "CREATE ACCOUNT", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
                Text(text = "Join the Wastra-Check Community", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Full Name Field dengan Font Paling Bold (Black)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Full Name", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, null, tint = BrownPrimary) },
                    placeholder = { Text("Enter your full name", fontWeight = FontWeight.Bold, color = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrownPrimary,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = BrownPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field dengan Font Paling Bold (Black)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Email Address", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, null, tint = BrownPrimary) },
                    placeholder = { Text("Enter your email", fontWeight = FontWeight.Bold, color = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrownPrimary,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = BrownPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field dengan Font Paling Bold (Black)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Password", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = BrownPrimary) },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text("Create a password", fontWeight = FontWeight.Bold, color = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrownPrimary,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = BrownPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Field dengan Font Paling Bold (Black)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Confirm Password", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.Black)
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = BrownPrimary) },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text("Repeat your password", fontWeight = FontWeight.Bold, color = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.Black),
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
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                        if (password == confirmPassword) authViewModel.register(name, email, password)
                        else Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                shape = RoundedCornerShape(12.dp),
                enabled = authState != AuthState.Loading
            ) {
                if (authState == AuthState.Loading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Black)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Already have an account? ", color = Color.Gray)
                Text("Sign In", color = BrownPrimary, fontWeight = FontWeight.Black, modifier = Modifier.clickable { navController.popBackStack() })
            }
        }
    }
}
