package com.example.streamingapp.data.remote

import android.util.Log
import com.example.streamingapp.model.StreamData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class StreamingWebSocketListener @Inject constructor() : WebSocketListener() {

    private val _streamChannel = Channel<StreamData>(Channel.BUFFERED)
    open fun getStreamDataFlow(): Flow<StreamData> = _streamChannel.receiveAsFlow()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("WebSocket", "Connection opened")
        // Optionally, send a message or subscribe to a topic
        webSocket.send("Hello from StreamingApp!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "Message received: $text")
        val streamData = StreamData(
            id = System.currentTimeMillis().toString(),
            content = text,
            timestamp = System.currentTimeMillis()
        )
        val result = _streamChannel.trySend(streamData)
        if (!result.isSuccess) {
            Log.e("WebSocket", "Failed to send streamData: ${result.exceptionOrNull()}")
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        onMessage(webSocket, bytes.utf8())
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Connection failed: ${t.localizedMessage}")
        // TODO: Implement reconnection logic if needed.
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("WebSocket", "Closing: $code / $reason")
        webSocket.close(1000, null)
    }
}
