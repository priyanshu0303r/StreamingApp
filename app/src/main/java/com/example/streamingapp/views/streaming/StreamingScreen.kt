package com.example.streamingapp.views.streaming

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stream
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.streamingapp.data.local.StreamingDataEntity
import com.example.streamingapp.model.StreamData

@Composable
fun StreamingScreen(viewModel: StreamingViewModel) {

    val streamDataList by viewModel.streamDataList.collectAsState()

    val cachedData by viewModel.cachedStreamData.collectAsState(initial = emptyList())


    var outgoingMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Real-Time Streaming Data") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    text = "How to Use Streaming Data:",
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(8.dp))
                StepItem(
                    step = "Step 1",
                    instruction = "The app automatically connects to the streaming server on launch.",
                    icon = Icons.Filled.Stream
                )
                StepItem(
                    step = "Step 2",
                    instruction = "Type a message below and tap Send to test the echo functionality.",
                    icon = Icons.Filled.Send
                )
                StepItem(
                    step = "Step 3",
                    instruction = "Both live and cached messages will be displayed below.",
                    icon = Icons.Filled.Check
                )
                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    OutlinedTextField(
                        value = outgoingMessage,
                        onValueChange = { outgoingMessage = it },
                        label = { Text("Enter message") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (outgoingMessage.isNotBlank()) {
                                viewModel.sendMessage(outgoingMessage)
                                outgoingMessage = ""
                            }
                        },
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text("Send")
                    }
                }


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    item {
                        Text(
                            text = "Live Stream Data:",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(streamDataList) { data: StreamData ->
                        DataCard(message = data.content, timestamp = data.timestamp)
                    }

                    item {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                        )
                    }

                    item {
                        Text(
                            text = "Cached Data:",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(cachedData) { entity: StreamingDataEntity ->
                        DataCard(message = entity.content, timestamp = entity.timestamp)
                    }
                }
            }
        }
    )
}

@Composable
fun DataCard(message: String, timestamp: Long) {
    Card(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = message, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Timestamp: $timestamp", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun StepItem(step: String, instruction: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = step,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("$step: $instruction", style = MaterialTheme.typography.body1)
    }
    Spacer(modifier = Modifier.height(8.dp))
}
