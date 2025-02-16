package com.example.streamingapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streaming_data")
data class StreamingDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val timestamp: Long
)
