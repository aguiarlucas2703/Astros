package com.example.astros.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunnyBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // 1. Céu Diurno (Degradê azul claro)
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF4FC3F7), // Azul claro em cima
                    Color(0xFFE1F5FE)  // Azul bem clarinho embaixo
                )
            )
        )

        // 2. O Sol (Canto superior esquerdo ou direito)
        val sunCenter = Offset(size.width * 0.8f, size.height * 0.15f)
        val sunRadius = 80f
        
        drawCircle(
            color = Color(0xFFFFD54F),
            radius = sunRadius,
            center = sunCenter
        )

        // 3. Raios de Sol
        val numRays = 12
        val innerRadius = sunRadius + 20f
        val outerRadius = sunRadius + 80f

        for (i in 0 until numRays) {
            val angle = (i * (360f / numRays)) * (Math.PI / 180f)
            val startX = sunCenter.x + (cos(angle) * innerRadius).toFloat()
            val startY = sunCenter.y + (sin(angle) * innerRadius).toFloat()
            val endX = sunCenter.x + (cos(angle) * outerRadius).toFloat()
            val endY = sunCenter.y + (sin(angle) * outerRadius).toFloat()

            drawLine(
                color = Color(0xFFFFD54F).copy(alpha = 0.6f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 10f
            )
        }
    }
}
