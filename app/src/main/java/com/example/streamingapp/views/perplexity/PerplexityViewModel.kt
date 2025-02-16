package com.example.streamingapp.views.perplexity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.exp
import kotlin.math.ln

class PerplexityViewModel : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _perplexity = MutableStateFlow<Double?>(null)
    val perplexity: StateFlow<Double?> = _perplexity

    fun onInputChanged(newInput: String) {
        _inputText.value = newInput
        calculatePerplexity(newInput)
    }

    private fun calculatePerplexity(input: String) {
        val probabilities = input.split(",")
            .mapNotNull { it.trim().toDoubleOrNull() }
            .filter { it in 0.0..1.0 }

        if (probabilities.isEmpty() || probabilities.any { it <= 0.0 }) {
            _perplexity.value = null
            return
        }

        val n = probabilities.size.toDouble()
        val logSum = probabilities.sumOf { ln(it) }
        val perplexityValue = exp(-logSum / n)
        _perplexity.value = perplexityValue
    }
}
