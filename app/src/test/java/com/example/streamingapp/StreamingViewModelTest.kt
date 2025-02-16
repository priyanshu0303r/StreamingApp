package com.example.streamingapp.ui.streaming

import com.example.streamingapp.data.StreamingRepository
import com.example.streamingapp.data.local.StreamingDataDao
import com.example.streamingapp.data.local.StreamingDataEntity
import com.example.streamingapp.data.remote.StreamingWebSocketListener
import com.example.streamingapp.model.StreamData
import com.example.streamingapp.views.streaming.StreamingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// Minimal dummy for the WebSocketListener.
private val dummyWebSocketListener = object : StreamingWebSocketListener() {
    override fun getStreamDataFlow(): Flow<StreamData> = flowOf()
}

// Minimal dummy for the StreamingDataDao.
private val dummyStreamingDataDao = object : StreamingDataDao {
    override suspend fun insert(streamData: StreamingDataEntity): Long = 0L
    override fun getAllStreamingData() = flowOf(emptyList<StreamingDataEntity>())
}

// Minimal dummy WebSocket instance.
private val dummyWebSocket = object : WebSocket {
    override fun request(): Request = Request.Builder().url("wss://dummy").build()
    override fun send(text: String): Boolean = true
    override fun send(bytes: ByteString): Boolean = true
    override fun close(code: Int, reason: String?): Boolean = true
    override fun queueSize(): Long {
        return 0L
    }

    override fun cancel() {}
}

// A simple fake repository for testing.
class FakeStreamingRepository : StreamingRepository(
    webSocket = dummyWebSocket,
    webSocketListener = dummyWebSocketListener,
    streamingDataDao = dummyStreamingDataDao
) {
    override val streamDataFlow: Flow<StreamData> = flow {
        emit(StreamData("1", "Test message 1", 1000))
        emit(StreamData("2", "Test message 2", 2000))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class StreamingViewModelTest {

    private lateinit var viewModel: StreamingViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = StreamingViewModel(FakeStreamingRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testStreamingDataCollected() = runTest {
        // Advance time to allow emissions.
        testDispatcher.scheduler.advanceUntilIdle()
        val list = viewModel.streamDataList.value
        // Expect two messages (newest first).
        assertEquals(2, list.size)
        assertEquals("Test message 2", list[0].content)
        assertEquals("Test message 1", list[1].content)
    }
}
