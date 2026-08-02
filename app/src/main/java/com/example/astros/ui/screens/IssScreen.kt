package com.example.astros.ui.screens

import android.graphics.BitmapFactory
import android.location.Geocoder
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale
import androidx.compose.foundation.Canvas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssScreen(openDrawer: () -> Unit, viewModel: IssViewModel = viewModel()) {
    val issPosition by viewModel.issPosition.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    // Estado de zoom e pan do mapa
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 8f)
        offset += panChange
    }

    // Nome do país via Geocoder (built-in, sem API key)
    var countryName by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(issPosition) {
        issPosition?.let { pos ->
            try {
                Geocoder(context, Locale.getDefault()).getFromLocation(
                    pos.latitude, pos.longitude, 1
                ) { addresses ->
                    countryName = when {
                        addresses.isNotEmpty() && !addresses[0].countryName.isNullOrBlank() ->
                            addresses[0].countryName
                        else -> "Oceano Internacional"
                    }
                }
            } catch (e: Exception) {
                countryName = null
            }
        }
    }

    // Animação de pulso para o marcador da ISS
    val infiniteTransition = rememberInfiniteTransition(label = "iss_pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 8f, targetValue = 26f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "pulse_radius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.9f, targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "pulse_alpha"
    )

    // Carrega a imagem do mapa-múndi dos assets uma única vez
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
                .background(Color(0xFF0a0a1a))
        ) {
            // Container do mapa com suporte a zoom/pan
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .align(Alignment.Center)
                    .clipToBounds()                         // Evita que o mapa saia da área
                    .transformable(state = transformableState) // Habilita gestos de zoom/pan
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                ) {
                    // Desenha o mapa-múndi
                    drawImage(
                        image = mapBitmap,
                        dstSize = androidx.compose.ui.unit.IntSize(
                            size.width.toInt(), size.height.toInt()
                        )
                    )

                    // Calcula posição do pixel a partir de lat/lon (projeção equiretangular)
                    issPosition?.let { pos ->
                        val x = ((pos.longitude + 180.0) / 360.0 * size.width).toFloat()
                        val y = ((90.0 - pos.latitude) / 180.0 * size.height).toFloat()
                        val center = Offset(x, y)

                        // Anel de pulso animado
                        drawCircle(
                            color = Color(0xFFFFD700).copy(alpha = pulseAlpha),
                            radius = pulseRadius,
                            center = center,
                            style = Stroke(width = 2.5f)
                        )
                        // Ponto central
                        drawCircle(color = Color(0xFFFFD700), radius = 7f, center = center)
                        drawCircle(color = Color.White, radius = 3f, center = center)
                    }
                }
            }

            // Dica de gesto (aparece sempre)
            Text(
                text = "🔍 Faça pinch para dar zoom",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.4f)
            )

            // Card com dados reais da ISS
            issPosition?.let { pos ->
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1a1a2e).copy(alpha = 0.97f)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Cabeçalho
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("🛰️", fontSize = 20.sp)
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
                                    color = Color.White.copy(alpha = 0.55f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(10.dp))

                        // País em destaque
                        if (!countryName.isNullOrBlank()) {
                            Text(
                                text = "📍 $countryName",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Linha de dados numéricos
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IssDataItem("Latitude",  String.format("%.4f°", pos.latitude))
                            VerticalDivider(modifier = Modifier.height(36.dp), color = Color.White.copy(alpha = 0.12f))
                            IssDataItem("Longitude", String.format("%.4f°", pos.longitude))
                            VerticalDivider(modifier = Modifier.height(36.dp), color = Color.White.copy(alpha = 0.12f))
                            IssDataItem("Velocidade","${pos.velocity.toInt()} km/h")
                        }
                    }
                }
            }

            // Loading inicial
            if (issPosition == null && error == null) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1a1a2e).copy(alpha = 0.9f)
                    ),
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

            // Erro de rede
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
        Text(label, fontSize = 10.sp, color = Color.White.copy(alpha = 0.55f))
        Spacer(modifier = Modifier.height(3.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}
