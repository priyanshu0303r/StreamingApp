package com.example.streamingapp.model

data class StreamData(
    val id: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
