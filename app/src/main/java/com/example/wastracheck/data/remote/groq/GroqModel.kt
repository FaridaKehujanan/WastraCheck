package com.example.wastracheck.data.remote.groq

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @SerializedName("messages") val messages: List<Message>,
    @SerializedName("model") val model: String = "meta-llama/llama-4-scout-17b-16e-instruct",
    @SerializedName("temperature") val temperature: Double? = null,
    @SerializedName("max_tokens") val maxTokens: Int? = null,
    @SerializedName("top_p") val topP: Double? = null,
    @SerializedName("stream") val stream: Boolean = false
)

data class Message(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: Any // Can be String or List<ContentItem>
)

data class ContentItem(
    @SerializedName("type") val type: String,
    @SerializedName("text") val text: String? = null,
    @SerializedName("image_url") val imageUrl: ImageUrl? = null
)

data class ImageUrl(
    @SerializedName("url") val url: String // Base64: "data:image/jpeg;base64,{base64_image}"
)

data class ChatResponse(
    @SerializedName("id") val id: String,
    @SerializedName("object") val obj: String,
    @SerializedName("created") val created: Long,
    @SerializedName("model") val model: String,
    @SerializedName("choices") val choices: List<Choice>,
    @SerializedName("usage") val usage: Usage? = null
)

data class Choice(
    @SerializedName("index") val index: Int,
    @SerializedName("message") val message: ResponseMessage,
    @SerializedName("finish_reason") val finishReason: String
)

data class ResponseMessage(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: String
)

data class Usage(
    @SerializedName("prompt_tokens") val promptTokens: Int,
    @SerializedName("completion_tokens") val completionTokens: Int,
    @SerializedName("total_tokens") val totalTokens: Int
)
