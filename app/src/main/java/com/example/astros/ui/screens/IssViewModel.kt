package com.example.astros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astros.network.IssNetwork
import com.example.astros.network.IssResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class IssViewModel : ViewModel() {
    private val _issPosition = MutableStateFlow<IssResponse?>(null)
    val issPosition: StateFlow<IssResponse?> = _issPosition.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    val response = IssNetwork.issApiService.getCurrentPosition()
                    _issPosition.value = response
                    _error.value = null
                } catch (e: Exception) {
                    _error.value = "Erro de conexão: ${e.message}"
                }
                delay(5000) // Poll every 5 seconds
            }
        }
    }
}
