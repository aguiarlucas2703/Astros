package com.example.astros.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssScreen(openDrawer: () -> Unit, viewModel: IssViewModel = viewModel()) {
    val issPosition by viewModel.issPosition.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    // Animação de pulso para o marcador da ISS
    val infiniteTransition = rememberInfiniteTransition(label = "iss_pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_radius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha"
    )

    // Carrega a imagem do mapa-múndi dos assets
    val mapBitmap = remember {
        val stream = context.assets.open("world_map.jpg")
        val bmp = BitmapFactory.decodeStream(stream)
        stream.close()
        bmp.asImageBitmap()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Onde está a ISS?") },
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
                .background(Color(0xFF0a0a1a)) // Fundo espacial
        ) {
            // Mapa-múndi + marcador da ISS usando Canvas nativo do Compose
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f) // Proporção 2:1 do mapa equiretangular
                    .align(Alignment.Center)
            ) {
                // Desenha o mapa-múndi
                drawImage(mapBitmap, dstSize = androidx.compose.ui.unit.IntSize(size.width.toInt(), size.height.toInt()))

                // Calcula a posição do pixel a partir de lat/lon (projeção equiretangular)
                issPosition?.let { pos ->
                    val x = ((pos.longitude + 180.0) / 360.0 * size.width).toFloat()
                    val y = ((90.0 - pos.latitude) / 180.0 * size.height).toFloat()

                    // Anel de pulso animado
                    drawCircle(
                        color = Color(0xFFFFD700).copy(alpha = pulseAlpha),
                        radius = pulseRadius,
                        center = Offset(x, y),
                        style = Stroke(width = 2f)
                    )

                    // Ponto central da ISS (amarelo dourado)
                    drawCircle(
                        color = Color(0xFFFFD700),
                        radius = 7f,
                        center = Offset(x, y)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 3f,
                        center = Offset(x, y)
                    )
                }
            }

            // Card com dados reais da ISS
            issPosition?.let { pos ->
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1a1a2e).copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "🛰️", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Estação Espacial Internacional",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFFFFD700)
                                )
                                Text(
                                    text = "Rastreamento ao vivo • atualizado a cada 5s",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IssDataItem(label = "Latitude", value = String.format("%.4f°", pos.latitude))
                            VerticalDivider(modifier = Modifier.height(40.dp), color = Color.White.copy(alpha = 0.15f))
                            IssDataItem(label = "Longitude", value = String.format("%.4f°", pos.longitude))
                            VerticalDivider(modifier = Modifier.height(40.dp), color = Color.White.copy(alpha = 0.15f))
                            IssDataItem(label = "Velocidade", value = "${pos.velocity.toInt()} km/h")
                        }
                    }
                }
            }

            // Loading
            if (issPosition == null && error == null) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1a1a2e).copy(alpha = 0.9f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = Color(0xFFFFD700)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Localizando a ISS...", fontSize = 14.sp, color = Color.White)
                    }
                }
            }

            // Erro
            error?.let {
                Card(
                    modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text("⚠️  $it", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        }
    }
}

@Composable
private fun IssDataItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}
