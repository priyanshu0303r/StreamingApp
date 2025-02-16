package com.example.streamingapp.data

import android.util.Log
import com.example.streamingapp.data.local.StreamingDataDao
import com.example.streamingapp.data.local.StreamingDataEntity
import com.example.streamingapp.data.remote.StreamingWebSocketListener
import com.example.streamingapp.model.StreamData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import okhttp3.WebSocket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class StreamingRepository @Inject constructor(
    private val webSocket: WebSocket,
    private val webSocketListener: StreamingWebSocketListener,
    private val streamingDataDao: StreamingDataDao
) {


    open val streamDataFlow: Flow<StreamData> =
        webSocketListener.getStreamDataFlow().onEach { data ->
            Log.d(TAG, "Received data: ${data.content}")
            saveStreamDataToCache(data)
        }

    private suspend fun saveStreamDataToCache(streamData: StreamData) {
        withContext(Dispatchers.IO) {
            val entity = StreamingDataEntity(
                content = streamData.content,
                timestamp = streamData.timestamp
            )
            streamingDataDao.insert(entity)
        }
    }


    fun getCachedStreamData(): Flow<List<StreamingDataEntity>> =
        streamingDataDao.getAllStreamingData()


    fun sendEcho(message: String) {
        Log.d(TAG, "Sending message: $message")
        webSocket.send(message)
    }

    companion object {
        private const val TAG: String = "StreamingRepository"
    }
}
