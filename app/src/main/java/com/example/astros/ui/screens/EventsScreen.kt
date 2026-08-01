package com.example.astros.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    openDrawer: () -> Unit,
    viewModel: EventsViewModel = viewModel()
) {
    val eventsState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eventos Astronômicos") },
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
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(eventsState) { uiModel ->
                EventCard(uiModel)
            }
        }
    }
}

@Composable
fun EventCard(uiModel: EventUiModel) {
    // Cores dinâmicas dependendo se é passado ou futuro
    val cardColor = if (uiModel.isPast) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
    val tagColor = if (uiModel.isPast) Color.Gray else Color(0xFF4CAF50) // Cinza ou Verde
    val tagText = if (uiModel.isPast) "Já ocorreu" else "Em breve"
    
    // Formatador de data simples: "dd/MM/yyyy"
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = if (uiModel.isPast) 2.dp else 6.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Linha superior com Tag e Data
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TAG visual
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = tagColor.copy(alpha = 0.2f),
                    contentColor = tagColor
                ) {
                    Text(
                        text = tagText,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Data
                Text(
                    text = uiModel.event.date.format(dateFormatter),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Título e Descrição
            Text(
                text = uiModel.event.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = uiModel.event.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            
            // Rodapé: Contagem de Dias
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (uiModel.isPast) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                
                val daysText = if (uiModel.isPast) {
                    "Aconteceu há ${Math.abs(uiModel.daysDifference)} dias"
                } else {
                    if (uiModel.daysDifference == 0L) "Acontece hoje!"
                    else "Faltam ${uiModel.daysDifference} dias"
                }
                
                Text(
                    text = daysText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
