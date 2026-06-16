package com.example.wastracheck.data.remote.groq

import android.util.Log
import retrofit2.Response

class GroqRepository {

    private val groqService = RetrofitClient.instance

    suspend fun getBatikIdentification(base64Image: String): String? {
        val request = ChatRequest(
            model = "meta-llama/llama-4-scout-17b-16e-instruct",
            messages = listOf(
                Message(
                    role = "user",
                    content = listOf(
                        ContentItem(
                            type = "text",
                            text = "Identifikasi motif batik dalam gambar ini. Sebutkan Nama Motif, Asal Daerah, dan Filosofinya. Gunakan Bahasa Indonesia. FORMAT: Nama Motif: [Nama], Asal: [Asal], Filosofi: [Filosofi]"
                        ),
                        ContentItem(
                            type = "image_url",
                            imageUrl = ImageUrl(url = "data:image/jpeg;base64,$base64Image")
                        )
                    )
                )
            ),
            maxTokens = 1024
        )

        return try {
            val response = groqService.getChatCompletion(
                token = RetrofitClient.API_KEY,
                request = request
            )
            
            if (response.isSuccessful) {
                val content = response.body()?.choices?.firstOrNull()?.message?.content
                if (content.isNullOrBlank()) {
                    Log.e("GroqRepository", "API Success but content is empty")
                    null
                } else {
                    content
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("GroqRepository", "API Error: ${response.code()} - $errorBody")
                "ERROR: ${response.code()} - ${response.message()}"
            }
        } catch (e: Exception) {
            Log.e("GroqRepository", "Exception: ${e.message}")
            "ERROR: Exception - ${e.localizedMessage}"
        }
    }
}
