package com.example.streamingapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StreamingDataDao {

    @Query("SELECT * FROM streaming_data ORDER BY timestamp DESC")
    fun getAllStreamingData(): Flow<List<StreamingDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: StreamingDataEntity):Long
}

