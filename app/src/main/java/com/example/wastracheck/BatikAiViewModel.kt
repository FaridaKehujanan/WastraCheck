package com.example.wastracheck

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastracheck.data.remote.groq.GroqRepository
import com.example.wastracheck.data.remote.groq.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class BatikAiViewModel : ViewModel() {

    private val repository = GroqRepository()
    private val _uiState = MutableStateFlow<AiUiState>(AiUiState.Idle)
    val uiState: StateFlow<AiUiState> = _uiState

    fun analyzeBatik(bitmap: Bitmap) {
        _uiState.value = AiUiState.Loading
        viewModelScope.launch {
            try {
                // Optimasi gambar: Mengecilkan ukuran agar proses scan lebih cepat dan stabil
                val resizedBitmap = if (bitmap.width > 720 || bitmap.height > 720) {
                    val scale = 720f / maxOf(bitmap.width, bitmap.height)
                    Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), true)
                } else {
                    bitmap
                }

                val base64Image = bitmapToBase64(resizedBitmap)
                val resultText = repository.getBatikIdentification(base64Image)

                if (resultText != null) {
                    if (resultText.startsWith("ERROR:")) {
                        val errorMessage = when {
                            resultText.contains("401") -> "API Key tidak valid atau telah kedaluwarsa."
                            resultText.contains("404") -> "Model tidak ditemukan. Silakan hubungi pengembang."
                            resultText.contains("429") -> "Terlalu banyak permintaan. Silakan coba lagi nanti."
                            else -> "Terjadi kesalahan pada server AI: ${resultText.removePrefix("ERROR:")}"
                        }
                        _uiState.value = AiUiState.Error(errorMessage)
                    } else if (resultText.isNotBlank()) {
                        _uiState.value = AiUiState.Success(resultText)
                    } else {
                        _uiState.value = AiUiState.Error("Gagal mendapatkan analisis dari Groq API. Pastikan koneksi internet stabil.")
                    }
                } else {
                    _uiState.value = AiUiState.Error("Gagal mendapatkan analisis dari Groq API. Pastikan koneksi internet stabil.")
                }
            } catch (e: Exception) {
                Log.e("BatikAI", "Exception: ${e.message}")
                _uiState.value = AiUiState.Error("Gagal Scan: ${e.localizedMessage ?: "Terjadi kesalahan koneksi"}")
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    fun resetState() {
        _uiState.value = AiUiState.Idle
    }
}

sealed class AiUiState {
    object Idle : AiUiState()
    object Loading : AiUiState()
    data class Success(val result: String) : AiUiState()
    data class Error(val message: String) : AiUiState()
}
