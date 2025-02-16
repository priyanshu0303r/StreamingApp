package com.example.streamingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StreamingDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun streamingDataDao(): StreamingDataDao
}
