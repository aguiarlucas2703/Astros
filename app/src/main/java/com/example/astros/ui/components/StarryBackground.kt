package com.example.astros.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import java.util.Random

@Composable
fun StarryBackground(modifier: Modifier = Modifier) {
    // Usamos remember com uma seed fixa para que as estrelas 
    // não mudem de lugar toda vez que o Compose redesenhar a tela
    val stars = remember {
        val random = Random(42)
        List(150) {
            Star(
                x = random.nextFloat(),
                y = random.nextFloat(),
                radius = random.nextFloat() * 3f + 1f,
                alpha = random.nextFloat() * 0.7f + 0.3f
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        // 1. Fundo Espacial (Degradê escuro)
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF040B16), // Azul super escuro
                    Color(0xFF102542)  // Azul noite
                )
            )
        )

        // 2. Desenhando as estrelas
        stars.forEach { star ->
            drawCircle(
                color = Color.White.copy(alpha = star.alpha),
                radius = star.radius,
                center = Offset(
                    x = star.x * size.width,
                    y = star.y * size.height
                )
            )
        }
    }
}

data class Star(val x: Float, val y: Float, val radius: Float, val alpha: Float)
