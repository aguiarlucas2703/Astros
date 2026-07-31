package com.example.astros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =============================================================================
// QuizScreen — tela placeholder do Quiz Educativo
//
// ATENÇÃO — Separação de linguagens (Kotlin + Java):
//   - Esta tela (UI/Compose) está em KOTLIN
//   - A lógica do quiz (perguntas, pontuação, SharedPreferences) será
//     implementada em JAVA, em classes como:
//       • QuizQuestion.java  → modelo de dado (pergunta + alternativas)
//       • QuizManager.java   → lógica de pontuação e persistência
//   - Esta tela chamará essas classes Java normalmente, pois Kotlin e Java
//     são 100% interoperáveis no Android.
//
// Isso permite ao professor testar/alterar a lógica Java separadamente da UI.
// =============================================================================
@Composable
fun QuizScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement  = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector        = Icons.Default.Quiz,
            contentDescription = "Ícone do quiz",
            modifier           = Modifier.size(72.dp),
            tint               = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text       = "Quiz Astronômico",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text  = "Em breve: perguntas e pontuação!",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        // para faser na (Fase 3): chamar QuizManager.java e exibir as perguntas aqui
    }
}
