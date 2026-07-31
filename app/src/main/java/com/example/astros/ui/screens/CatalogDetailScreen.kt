package com.example.astros.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.astros.data.CelestialBody

// =============================================================================
// CatalogDetailScreen — Tela de Detalhes
//
// Mostra a imagem grande carregada da NASA, nome e descrição completa.
// =============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogDetailScreen(
    body: CelestialBody,
    onBackClick: () -> Unit
) {
    // Scaffold é o "esqueleto" da tela. Ele já arruma espaço para a barra
    // superior (TopAppBar) e garante que o conteúdo não fique por baixo dela.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(body.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        // Usamos AutoMirrored para a setinha virar caso o 
                        // celular esteja em idioma da direita pra esquerda (árabe)
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues -> // O paddingValues é o espaço que a TopAppBar ocupa
        
        // Column com verticalScroll permite rolar a tela se o texto for muito longo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Imagem Grande no topo
            if (body.imageUrl != null) {
                AsyncImage(
                    model = body.imageUrl,
                    contentDescription = body.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Altura fixa para a imagem de capa
                )
            } else {
                // Se a imagem ainda estiver null (ou não achou na NASA), 
                // mostramos só um quadrado escuro de placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
            
            // Textos
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = body.name,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = body.shortDescription,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = body.detailedDescription,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}
