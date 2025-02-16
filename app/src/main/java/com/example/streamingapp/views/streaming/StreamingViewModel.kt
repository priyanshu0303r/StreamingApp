package com.example.streamingapp.views.streaming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingapp.data.StreamingRepository
import com.example.streamingapp.data.local.StreamingDataEntity
import com.example.streamingapp.model.StreamData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreamingViewModel @Inject constructor(
    private val repository: StreamingRepository
) : ViewModel() {


    private val _streamDataList = MutableStateFlow<List<StreamData>>(emptyList())
    val streamDataList: StateFlow<List<StreamData>> = _streamDataList


    val cachedStreamData: Flow<List<StreamingDataEntity>> = repository.getCachedStreamData()

    init {
        subscribeToStreamingData()
    }

    private fun subscribeToStreamingData() {
        viewModelScope.launch {
            repository.streamDataFlow.collect { data ->
                _streamDataList.value = listOf(data) + _streamDataList.value
            }
        }
    }


    fun sendMessage(message: String) {
        repository.sendEcho(message)
    }
}
