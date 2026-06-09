package com.example.wastracheck.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastracheck.ExploreViewModel
import com.example.wastracheck.data.WastraMotif
import com.example.wastracheck.ui.theme.BackgroundLight
import com.example.wastracheck.ui.theme.BrownPrimary
import com.example.wastracheck.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatikChallengeScreen(
    navController: NavController,
    viewModel: ExploreViewModel
) {
    val motifs by viewModel.motifs.collectAsState()
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    
    var selectedOption by remember { mutableStateOf<WastraMotif?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    // Sekarang mengambil semua data yang tersedia (20 motif)
    val challengeMotifs = motifs

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("BATIK CHALLENGE", fontWeight = FontWeight.Bold, color = BrownPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrownPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (challengeMotifs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BrownPrimary)
                }
            } else if (showResult) {
                ChallengeResultView(score = score, total = challengeMotifs.size) {
                    score = 0
                    currentQuestionIndex = 0
                    showResult = false
                    isAnswered = false
                    selectedOption = null
                }
            } else {
                val currentMotif = challengeMotifs[currentQuestionIndex]
                
                Text(
                    "Pertanyaan ${currentQuestionIndex + 1} dari ${challengeMotifs.size}",
                    fontSize = 14.sp,
                    color = TextGray
                )
                
                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1).toFloat() / challengeMotifs.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(8.dp),
                    color = BrownPrimary,
                    trackColor = Color.LightGray,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.size(220.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    if (currentMotif.imageRes != null) {
                        Image(
                            painter = painterResource(id = currentMotif.imageRes),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Placeholder jika gambar belum ada
                        Box(modifier = Modifier.fillMaxSize().background(Color.LightGray), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Image, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(64.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "Apa nama motif batik di atas?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = BrownPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                val options = remember(currentQuestionIndex) {
                    (challengeMotifs.filter { it.id != currentMotif.id }.shuffled().take(3) + currentMotif).shuffled()
                }

                options.forEach { option ->
                    val isCorrect = option.id == currentMotif.id
                    val isSelected = selectedOption?.id == option.id
                    
                    val buttonColor by animateColorAsState(
                        targetValue = when {
                            isAnswered && isCorrect -> Color(0xFF4CAF50)
                            isAnswered && isSelected && !isCorrect -> Color(0xFFF44336)
                            isSelected -> BrownPrimary.copy(alpha = 0.1f)
                            else -> Color.White
                        }, label = "color"
                    )

                    val textColor = if (isAnswered && (isCorrect || (isSelected && !isCorrect))) Color.White else BrownPrimary

                    Button(
                        onClick = {
                            if (!isAnswered) {
                                selectedOption = option
                                isAnswered = true
                                if (isCorrect) score++
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(if (isAnswered) 0.dp else 2.dp),
                        enabled = !isAnswered || isSelected || isCorrect
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(option.name, color = textColor, fontWeight = FontWeight.Medium)
                            if (isAnswered && isCorrect) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                            } else if (isAnswered && isSelected && !isCorrect) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.Cancel, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (isAnswered) {
                    Button(
                        onClick = {
                            if (currentQuestionIndex < challengeMotifs.size - 1) {
                                currentQuestionIndex++
                                isAnswered = false
                                selectedOption = null
                            } else {
                                showResult = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            if (currentQuestionIndex < challengeMotifs.size - 1) "PERTANYAAN BERIKUTNYA" else "LIHAT HASIL",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChallengeResultView(score: Int, total: Int, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Stars,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = BrownPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Tantangan Selesai!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BrownPrimary)
        
        val feedbackText = when {
            score == total -> "Luar Biasa! Kamu Ahli Batik!"
            score >= total / 2 -> "Bagus! Kamu cukup mengenal Wastra kita."
            else -> "Ayo belajar lagi tentang motif Batik!"
        }
        
        Text(feedbackText, fontSize = 16.sp, color = BrownPrimary, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
        
        Spacer(modifier = Modifier.height(8.dp))
        Text("Skor Kamu: $score / $total", fontSize = 18.sp, color = TextGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Coba Lagi")
        }
    }
}
