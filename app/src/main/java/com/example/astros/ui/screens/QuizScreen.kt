package com.example.astros.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    openDrawer: () -> Unit,
    viewModel: QuizViewModel = viewModel()
) {
    val gameState by viewModel.gameState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Missão: Quiz") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Abrir Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (gameState) {
                QuizGameState.START -> QuizStartScreen(viewModel)
                QuizGameState.PLAYING -> QuizPlayingScreen(viewModel)
                QuizGameState.END -> QuizEndScreen(viewModel)
            }
        }
    }
}

@Composable
fun QuizStartScreen(viewModel: QuizViewModel) {
    val highScore by viewModel.highScore.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Troféu",
            modifier = Modifier.size(120.dp),
            tint = Color(0xFFFFC107) // Dourado
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Pronto para o Desafio?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Teste seus conhecimentos astronômicos em 10 perguntas sorteadas.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Seu Recorde Atual", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(
                    text = "$highScore / 10",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = { viewModel.startQuiz() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("INICIAR QUIZ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuizPlayingScreen(viewModel: QuizViewModel) {
    val question by viewModel.currentQuestion.collectAsState()
    val questionNumber by viewModel.questionNumber.collectAsState()
    val score by viewModel.score.collectAsState()
    val selectedOptionIndex by viewModel.selectedOptionIndex.collectAsState()
    val isAnswerCorrect by viewModel.isAnswerCorrect.collectAsState()

    // Se a pergunta for nula (carregando), não renderiza
    if (question == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Cabeçalho: Número da Pergunta e Pontuação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pergunta $questionNumber de 10",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "Pontos: $score",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // O Texto da Pergunta
        Text(
            text = question!!.text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(40.dp))

        // As 4 opções de resposta
        question!!.options.forEachIndexed { index, optionText ->
            
            // Lógica do Feedback Visual (Cores)
            var buttonColor = MaterialTheme.colorScheme.surface
            var textColor = MaterialTheme.colorScheme.onSurface
            var borderColor = MaterialTheme.colorScheme.outlineVariant

            if (selectedOptionIndex != null) {
                // O usuário já clicou em algo
                if (index == question!!.correctOptionIndex) {
                    // Esta é a opção correta! Fica verde para mostrar a resposta
                    buttonColor = Color(0xFF4CAF50)
                    textColor = Color.White
                    borderColor = Color(0xFF388E3C)
                } else if (index == selectedOptionIndex && isAnswerCorrect == false) {
                    // O usuário clicou nesta e errou! Fica vermelha
                    buttonColor = Color(0xFFF44336)
                    textColor = Color.White
                    borderColor = Color(0xFFD32F2F)
                }
            }

            OutlinedButton(
                onClick = { viewModel.submitAnswer(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonColor,
                    contentColor = textColor
                ),
                border = BorderStroke(1.dp, borderColor)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Letra da alternativa (A, B, C, D)
                    val letter = ('A' + index).toString()
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (buttonColor == MaterialTheme.colorScheme.surface) 
                            MaterialTheme.colorScheme.primaryContainer 
                            else buttonColor.copy(alpha = 0.8f)
                    ) {
                        Text(
                            text = letter,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Bold,
                            color = if (buttonColor == MaterialTheme.colorScheme.surface) 
                                MaterialTheme.colorScheme.onPrimaryContainer 
                                else Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = optionText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun QuizEndScreen(viewModel: QuizViewModel) {
    val score by viewModel.score.collectAsState()
    val isNewRecord by viewModel.isNewRecord.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isNewRecord) {
            Text(
                text = "🎉 NOVO RECORDE! 🎉",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF4CAF50),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Missão Concluída",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Você acertou:",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        
        Text(
            text = "$score / 10",
            fontSize = 64.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = { viewModel.startQuiz() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Icon(Icons.Default.Replay, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("JOGAR NOVAMENTE", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
