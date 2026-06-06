package com.example.wastracheck

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BatikAiViewModel : ViewModel() {

    // PENTING: API Key Gemini HARUS diawali dengan "AIza...". 
    // Kunci "AQ.Ab8..." yang Anda gunakan saat ini SALAH (kemungkinan itu token lain).
    // Silakan dapatkan kunci yang benar di: https://aistudio.google.com/app/apikey
    private val apiKey = "AQ.Ab8RN6Lv_ymmZvYNwSlVmXpzU21lPqhbL8I_KHksCSnmemR9Ag"

    private val config = generationConfig {
        temperature = 0.4f
        topK = 32
        topP = 1f
    }

    // Nama model standar yang benar adalah "gemini-1.5-flash"
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        generationConfig = config
    )

    private val _uiState = MutableStateFlow<AiUiState>(AiUiState.Idle)
    val uiState: StateFlow<AiUiState> = _uiState

    fun analyzeBatik(bitmap: Bitmap) {
        // Validasi format API Key sebelum melakukan request ke server
        if (!apiKey.startsWith("AIza")) {
            _uiState.value = AiUiState.Error("API Key TIDAK VALID! Kunci Gemini harus diawali dengan 'AIza'. Silakan ganti apiKey di BatikAiViewModel.kt dengan kunci yang benar dari Google AI Studio.")
            return
        }

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
                
                val response = generativeModel.generateContent(
                    content {
                        image(resizedBitmap)
                        text("Identifikasi motif batik dalam gambar ini. Sebutkan Nama Motif, Asal Daerah, dan Filosofinya. Gunakan Bahasa Indonesia. FORMAT: Nama Motif: [Nama], Asal: [Asal], Filosofi: [Filosofi]")
                    }
                )
                
                val resultText = response.text
                if (!resultText.isNullOrBlank()) {
                    _uiState.value = AiUiState.Success(resultText)
                } else {
                    _uiState.value = AiUiState.Error("AI tidak memberikan respon. Pastikan foto batik terlihat jelas.")
                }
            } catch (e: Exception) {
                Log.e("BatikAI", "Error: ${e.message}")
                val errorMsg = when {
                    e.message?.contains("404") == true -> "Model Tidak Ditemukan (404). Ini hampir pasti karena API Key Anda SALAH. Pastikan menggunakan API Key dari Google AI Studio (diawali 'AIza')."
                    e.message?.contains("403") == true -> "Akses Ditolak (403). Cek apakah API Key Anda aktif dan memiliki izin untuk model Gemini 1.5 Flash."
                    else -> "Gagal Scan: ${e.localizedMessage ?: "Terjadi kesalahan koneksi"}"
                }
                _uiState.value = AiUiState.Error(errorMsg)
            }
        }
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
