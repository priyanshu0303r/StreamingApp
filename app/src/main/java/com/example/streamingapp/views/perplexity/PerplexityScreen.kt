package com.example.streamingapp.views.perplexity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PerplexityScreen(viewModel: PerplexityViewModel) {

    val inputText by viewModel.inputText.collectAsState()
    val perplexity by viewModel.perplexity.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perplexity Calculator") },
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
                    text = "How to Calculate Perplexity:",
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Step-by-step instructions with emotion icons.
                StepItem(
                    step = "Step 1",
                    instruction = "Enter a sequence of probabilities as comma-separated values.",
                    icon = Icons.Filled.SentimentSatisfied
                )
                StepItem(
                    step = "Step 2",
                    instruction = "Ensure all values are between 0 and 1.",
                    icon = Icons.Filled.Info
                )
                StepItem(
                    step = "Step 3",
                    instruction = "The calculator will update perplexity in real time.",
                    icon = Icons.Filled.Check
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { viewModel.onInputChanged(it) },
                    label = { Text("Enter probabilities (e.g., 0.1, 0.2, 0.3)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Calculate,
                        contentDescription = "Calculator Icon",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Perplexity: ${
                            if (perplexity != null) String.format("%.4f", perplexity) else "Invalid input"
                        }",
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    )
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
