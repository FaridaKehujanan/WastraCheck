package com.example.wastracheck.data.remote.groq

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqService {
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") token: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}
