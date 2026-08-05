package com.example.astros.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astros.data.MatchRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    openDrawer: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val totalXp by viewModel.totalXp.collectAsState()
    val matchHistory by viewModel.matchHistory.collectAsState()

    LaunchedEffect(Unit) { viewModel.refreshXp() }

    val rank = viewModel.getCurrentRank(totalXp)
    val progress = if (rank.maxXp == 9999) 1f
    else ((totalXp - rank.minXp).toFloat() / (rank.maxXp - rank.minXp + 1).toFloat()).coerceIn(0f, 1f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Perfil") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            // === Avatar + Patente ===
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(rank.emoji, fontSize = 64.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(rank.title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text("$totalXp XP", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                }
            }

            // === Barra de Progresso ===
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Progresso para próxima patente", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("$totalXp XP", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (rank.maxXp != 9999)
                                Text("${rank.maxXp + 1} XP", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            else
                                Text("Nível Máximo 🏆", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            // === Histórico de Partidas ===
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Histórico de Partidas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (matchHistory.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Nenhuma partida ainda.\nJogue o Quiz ou o Adivinhe para registrar aqui!",
                            modifier = Modifier.padding(24.dp).fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(matchHistory) { record ->
                    MatchHistoryCard(record)
                }
            }

            // Espaço no final
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun MatchHistoryCard(record: MatchRecord) {
    val isQuiz = record.gameType == "Quiz"
    val icon = if (isQuiz) Icons.Default.EmojiEvents else Icons.Default.Search
    val accentColor = if (isQuiz) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary

    val dateStr = remember(record.timestamp) {
        SimpleDateFormat("dd/MM/yyyy  HH:mm", Locale.forLanguageTag("pt-BR")).format(Date(record.timestamp))
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone do tipo de jogo
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Info central
            Column(modifier = Modifier.weight(1f)) {
                Text(record.gameType, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(dateStr, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Pontuação e XP
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${record.score}/${record.total}",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = accentColor
                )
                Text(
                    text = "+${record.xpEarned} XP",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
