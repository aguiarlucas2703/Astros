package com.example.astros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.astros.ui.screens.CatalogTab
import com.example.astros.ui.screens.EventsScreen
import com.example.astros.ui.screens.QuizScreen
import com.example.astros.ui.theme.AstrosTheme

// =============================================================================
// MainActivity — ponto de entrada do app
//
// No Android, toda tela principal começa em uma Activity.
// Aqui usamos ComponentActivity (base do Jetpack Compose).
//
// onCreate() é chamado quando o app inicia. Dentro dele:
//   - enableEdgeToEdge() → faz o conteúdo ocupar toda a tela (sob status bar)
//   - setContent {}      → define a UI em Compose (substitui o XML de layout)
// =============================================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // AstrosTheme aplica nossa paleta de azuis em tod o app
            AstrosTheme {
                AstrosApp()
            }
        }
    }
}

// =============================================================================
// AppDestinations — enum que define as 3 abas do app
//
// Um enum é um tipo que só pode ter os valores listados (CATALOG, EVENTS, QUIZ).
// Cada valor carrega consigo:
//   - label: texto exibido na aba
//   - icon:  ícone do Material Design (ImageVector)
//
// 🔧 PONTO DE ALTERAÇÃO AO VIVO: se o professor pedir para adicionar/remover
//    uma aba, é só adicionar/remover uma entrada neste enum.
// =============================================================================
enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    CATALOG("Catálogo", Icons.Default.Explore),   // bússola/exploração → catálogo
    EVENTS ("Eventos",  Icons.Default.Event),     // calendário         → eventos
    QUIZ   ("Quiz",     Icons.Default.Quiz),      // ponto de interrogação → quiz
}

// =============================================================================
// AstrosApp — Composable raiz que monta a navegação por abas
//
// NavigationSuiteScaffold é o componente do Material3 que exibe:
//   - BottomNavigationBar (celulares, portrait)
//   - NavigationRail (tablets, landscape)
//   - NavigationDrawer (telas grandes)
// ...automaticamente, sem código extra!
//
// Estado:
//   - `currentDestination` guarda qual aba está ativa
//   - `rememberSaveable` mantém o valor mesmo ao girar a tela
//   - `by` é um delegate Kotlin: lê/escreve como variável normal
// =============================================================================
@Composable
fun AstrosApp() {
    // Estado da aba ativa — começa no Catálogo
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CATALOG) }

    NavigationSuiteScaffold(
        // Constrói os itens da barra de navegação iterando sobre o enum
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon     = { Icon(destination.icon, contentDescription = destination.label) },
                    label    = { Text(destination.label) },
                    selected = destination == currentDestination,  // destaque na aba ativa
                    onClick  = { currentDestination = destination }  // troca de aba ao clicar
                )
            }
        }
    ) {
        // Conteúdo exibido no corpo da tela, conforme a aba selecionada
        // `when` é o equivalente Kotlin de um switch/case, mas mais poderoso
        when (currentDestination) {
            AppDestinations.CATALOG -> CatalogTab()
            AppDestinations.EVENTS  -> EventsScreen()
            AppDestinations.QUIZ    -> QuizScreen()
        }
    }
}