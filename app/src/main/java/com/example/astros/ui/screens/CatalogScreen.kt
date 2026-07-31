package com.example.astros.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tsunami
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.astros.data.CelestialBody

// =============================================================================
// CatalogTab — Controlador de Navegação em 3 Níveis
// =============================================================================
@Composable
fun CatalogTab(openDrawer: () -> Unit, viewModel: CatalogViewModel = viewModel()) {
    // Nível 1: Categoria selecionada
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    // Nível 2: Corpo celeste selecionado (Detalhes)
    var selectedBody by remember { mutableStateOf<CelestialBody?>(null) }

    // Intercepta o botão "Voltar" físico do Android
    BackHandler(enabled = selectedCategory != null || selectedBody != null) {
        if (selectedBody != null) {
            selectedBody = null // Volta do detalhe pra lista
        } else if (selectedCategory != null) {
            selectedCategory = null // Volta da lista pras categorias
        }
    }

    when {
        // NÍVEL 3: Mostra detalhes
        selectedBody != null -> {
            CatalogDetailScreen(
                body = selectedBody!!,
                onBackClick = { selectedBody = null }
            )
        }
        
        // NÍVEL 2: Mostra a lista da categoria
        selectedCategory != null -> {
            CatalogListScreen(
                category = selectedCategory!!,
                viewModel = viewModel,
                onBodyClick = { clickedBody -> selectedBody = clickedBody },
                onBackClick = { selectedCategory = null }
            )
        }
        
        // NÍVEL 1: Mostra a grade de categorias (Tela Inicial)
        else -> {
            CategoryGridScreen(
                categories = viewModel.categories,
                openDrawer = openDrawer,
                onCategoryClick = { category ->
                    selectedCategory = category
                    // O PULO DO GATO: Carrega imagens da NASA só ao entrar na categoria!
                    viewModel.loadImagesForCategory(category) 
                }
            )
        }
    }
}

// =============================================================================
// NÍVEL 1: Tela de Categorias (Grade / Grid)
// =============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryGridScreen(
    categories: List<String>,
    openDrawer: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Text(
                text = "Explorar",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(24.dp)
            )

            // LazyVerticalGrid é perfeito para criar um visual de "Menu em blocos"
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 colunas
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category = category, onClick = { onCategoryClick(category) })
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Faz o card ficar quadrado (altura = largura)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Um ícone diferente pra cada categoria
            val icon = when (category) {
                "Planetas" -> Icons.Default.Public
                "Estrelas" -> Icons.Default.Star
                "Luas" -> Icons.Default.Tsunami
                else -> Icons.Default.AutoAwesome
            }

            Icon(
                imageVector = icon,
                contentDescription = category,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = category,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

// =============================================================================
// NÍVEL 2: Tela da Lista (Reaproveitada)
// =============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogListScreen(
    category: String,
    viewModel: CatalogViewModel,
    onBodyClick: (CelestialBody) -> Unit,
    onBackClick: () -> Unit
) {
    // Filtramos os itens baseados na categoria clicada
    val bodiesInCategory = viewModel.getBodiesByCategory(category)
    // Coletamos o estado só para forçar o Compose a redesenhar quando as imagens chegarem
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category) },
                navigationIcon = {
                    // Botão de voltar pra tela de categorias
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Em vez de passar a lista toda, passamos só a lista filtrada (bodiesInCategory)
            // Mas precisamos pegar a versão mais recente dela do uiState (que tem as imagens)
            val updatedBodies = uiState.filter { it.category == category }
            
            items(updatedBodies) { body ->
                CelestialBodyCard(body = body, onClick = { onBodyClick(body) })
            }
        }
    }
}

// =============================================================================
// CelestialBodyCard — O layout de 1 item da lista (igual antes)
// =============================================================================
@Composable
fun CelestialBodyCard(body: CelestialBody, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (body.imageUrl != null) {
                AsyncImage(
                    model = body.imageUrl,
                    contentDescription = body.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(24.dp)
                )
            }

            Column {
                Text(
                    text = body.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = body.shortDescription,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
