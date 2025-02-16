package com.example.streamingapp

import com.example.streamingapp.views.perplexity.PerplexityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PerplexityViewModelTest {

    @Test
    fun testValidInput() = runTest {
        val viewModel = PerplexityViewModel()
        viewModel.onInputChanged("0.1, 0.2, 0.3")
        val perplexity = viewModel.perplexity.first()
        assertNotNull("Perplexity should be computed for valid input", perplexity)
    }

    @Test
    fun testInvalidInput() = runTest {
        val viewModel = PerplexityViewModel()
        viewModel.onInputChanged("a, b, c")
        val perplexity = viewModel.perplexity.first()
        assertNull("Perplexity should be null for invalid input", perplexity)
    }
}
