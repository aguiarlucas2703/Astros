package com.example.astros.ui.screens

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.astros.quiz.Question
import com.example.astros.quiz.QuizEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class QuizGameState {
    START, PLAYING, END
}

// =============================================================================
// QuizViewModel — Adaptador de Interface
//
// Este ViewModel gerencia a integração dos dados do QuizEngine (Java)
// convertendo-os em 'StateFlows' para a interface (Kotlin/Jetpack Compose).
// Utiliza SharedPreferences para persistência do recorde.
// =============================================================================
class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val quizEngine = QuizEngine()
    private val prefs = application.getSharedPreferences("AstrosQuizPrefs", Context.MODE_PRIVATE)

    // Estados da Interface
    private val _gameState = MutableStateFlow(QuizGameState.START)
    val gameState: StateFlow<QuizGameState> = _gameState.asStateFlow()

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _questionNumber = MutableStateFlow(1)
    val questionNumber: StateFlow<Int> = _questionNumber.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _highScore = MutableStateFlow(prefs.getInt("HIGH_SCORE", 0))
    val highScore: StateFlow<Int> = _highScore.asStateFlow()

    private val _isNewRecord = MutableStateFlow(false)
    val isNewRecord: StateFlow<Boolean> = _isNewRecord.asStateFlow()

    // Estado Visual (Feedback de Acerto/Erro)
    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex.asStateFlow()

    private val _isAnswerCorrect = MutableStateFlow<Boolean?>(null)
    val isAnswerCorrect: StateFlow<Boolean?> = _isAnswerCorrect.asStateFlow()
    
    // Evita duplos cliques
    private var isProcessingAnswer = false

    fun startQuiz() {
        quizEngine.startNewGame()
        _isNewRecord.value = false
        updateUiState()
        _gameState.value = QuizGameState.PLAYING
    }

    fun submitAnswer(selectedIndex: Int) {
        if (isProcessingAnswer) return
        isProcessingAnswer = true

        val isCorrect = quizEngine.submitAnswer(selectedIndex)
        
        // Atualiza UI para mostrar Feedback Visual (Verde/Vermelho)
        _selectedOptionIndex.value = selectedIndex
        _isAnswerCorrect.value = isCorrect
        _score.value = quizEngine.score

        // Espera 1.5s para o usuário ver o resultado e depois avança
        viewModelScope.launch {
            delay(1500)
            
            val hasNext = quizEngine.moveToNextQuestion()
            
            // Limpa o feedback visual
            _selectedOptionIndex.value = null
            _isAnswerCorrect.value = null
            
            if (hasNext) {
                updateUiState()
            } else {
                finishQuiz()
            }
            
            isProcessingAnswer = false
        }
    }

    private fun updateUiState() {
        _currentQuestion.value = quizEngine.currentQuestion
        _questionNumber.value = quizEngine.currentQuestionNumber
        _score.value = quizEngine.score
    }

    private fun finishQuiz() {
        val finalScore = quizEngine.score
        val currentHigh = _highScore.value
        
        if (finalScore > currentHigh) {
            _isNewRecord.value = true
            _highScore.value = finalScore
            prefs.edit().putInt("HIGH_SCORE", finalScore).apply()
        }
        
        // Gamificação: Soma XP Total (10 XP por acerto)
        val currentXp = prefs.getInt("TOTAL_XP", 0)
        prefs.edit().putInt("TOTAL_XP", currentXp + (finalScore * 10)).apply()
        
        _gameState.value = QuizGameState.END
    }

    fun giveUp() {
        isProcessingAnswer = false
        _selectedOptionIndex.value = null
        _isAnswerCorrect.value = null
        _gameState.value = QuizGameState.START
    }

    fun returnToStart() {
        _gameState.value = QuizGameState.START
    }
}
