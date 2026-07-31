package com.example.astros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =============================================================================
// CatalogScreen — tela placeholder do Catálogo de Corpos Celestes
//
// Por enquanto exibe uma mensagem de "em construção" centralizada.
// Na próxima fase, será substituída pela lista de planetas/luas.
// =============================================================================
@Composable
fun CatalogScreen() {
    // Column: organiza os filhos verticalmente, um embaixo do outro
    Column(
        modifier = Modifier
            .fillMaxSize()       // ocupa toda a tela disponível
            .padding(24.dp),     // margem interna de 24dp em todos os lados
        verticalArrangement   = Arrangement.Center,   // centraliza verticalmente
        horizontalAlignment   = Alignment.CenterHorizontally  // centraliza horizontalmente
    ) {
        // Ícone decorativo
        Icon(
            imageVector         = Icons.Default.Explore,
            contentDescription  = "Ícone do catálogo",
            modifier            = Modifier.size(72.dp),
            tint                = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text       = "Catálogo de Corpos Celestes",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text  = "Em breve: planetas, luas e muito mais!",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}
