package com.example.astros.ui.screens

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.astros.data.CatalogRepository
import com.example.astros.quiz.GuessEngine
import com.example.astros.quiz.GuessItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class GuessGameState {
    START, PLAYING, RESULT
}

class GuessViewModel(application: Application) : AndroidViewModel(application) {
    private val guessEngine = GuessEngine()
    private val repository = CatalogRepository()
    private val prefs = application.getSharedPreferences("AstrosQuizPrefs", Context.MODE_PRIVATE)

    private val _gameState = MutableStateFlow(GuessGameState.START)
    val gameState: StateFlow<GuessGameState> = _gameState.asStateFlow()

    private val _currentItem = MutableStateFlow<GuessItem?>(null)
    val currentItem: StateFlow<GuessItem?> = _currentItem.asStateFlow()
    
    private val _currentImageUrl = MutableStateFlow<String?>(null)
    val currentImageUrl: StateFlow<String?> = _currentImageUrl.asStateFlow()

    private val _round = MutableStateFlow(1)
    val round: StateFlow<Int> = _round.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()
    
    private val _isCorrect = MutableStateFlow(false)
    val isCorrect: StateFlow<Boolean> = _isCorrect.asStateFlow()

    fun startGame() {
        guessEngine.startNewGame()
        _score.value = 0
        _gameState.value = GuessGameState.PLAYING
        loadCurrentItem()
    }

    private fun loadCurrentItem() {
        val item = guessEngine.currentItem
        _currentItem.value = item
        _round.value = guessEngine.currentRound
        _currentImageUrl.value = null
        _showError.value = false
        _isCorrect.value = false
        
        if (item != null) {
            viewModelScope.launch {
                val url = repository.getImageUrlFor(item.nasaImageId)
                _currentImageUrl.value = url
            }
        }
    }

    fun submitGuess(input: String) {
        if (_isCorrect.value) return // Previne duplos envios rápidos
        
        val isMatch = guessEngine.submitAnswer(input)
        
        if (isMatch) {
            _isCorrect.value = true
            _score.value = guessEngine.score
            
            // Gamificação: Adivinhar requer mais esforço mental (Input Text), vale 20 XP!
            val currentXp = prefs.getInt("TOTAL_XP", 0)
            prefs.edit().putInt("TOTAL_XP", currentXp + 20).apply()
            
            viewModelScope.launch {
                delay(1500)
                val hasNext = guessEngine.moveToNext()
                if (hasNext) {
                    loadCurrentItem()
                } else {
                    _gameState.value = GuessGameState.RESULT
                }
            }
        } else {
            _showError.value = true
            viewModelScope.launch {
                delay(2000)
                _showError.value = false
            }
        }
    }
    
    fun giveUp() {
        _gameState.value = GuessGameState.START
    }
}
