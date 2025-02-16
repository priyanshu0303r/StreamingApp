package com.example.streamingapp.di

import android.util.Log
import com.example.streamingapp.data.remote.StreamingWebSocketListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val WEBSOCKET_URL = "wss://echo.websocket.events"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideWebSocket(okHttpClient: OkHttpClient, listener: StreamingWebSocketListener): WebSocket {
        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()
        return okHttpClient.newWebSocket(request, listener)
    }
}
