package com.example.astros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astros.ui.screens.CatalogTab
import com.example.astros.ui.screens.EventsScreen
import com.example.astros.ui.screens.ProfileScreen
import com.example.astros.ui.screens.QuizScreen
import com.example.astros.ui.screens.SettingsScreen
import com.example.astros.ui.theme.AstrosTheme
import kotlinx.coroutines.launch

// =============================================================================
// MainActivity — ponto de entrada do app
// =============================================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AstrosTheme {
                AstrosApp()
            }
        }
    }
}

// =============================================================================
// AppDestinations — enum que define os destinos do app
// Agora temos 4 destinos, mas Configurações só aparecerá no Menu Sanduíche.
// =============================================================================
enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    CATALOG("Catálogo", Icons.Default.Explore),
    EVENTS ("Eventos",  Icons.Default.Event),
    QUIZ   ("Quiz",     Icons.Default.Quiz),
    PROFILE("Meu Perfil", Icons.Default.Person),
    SETTINGS("Configurações", Icons.Default.Settings)
}

// =============================================================================
// AstrosApp — Composable raiz com Redundância de Navegação
// ModalNavigationDrawer por fora para criar o Menu Sanduíche.
// Por dentro, mantem-se o NavigationSuiteScaffold para a barra inferior.
// A tela de Configurações só aparece no Drawer, garantindo hierarquia de UI.
// =============================================================================
@Composable
fun AstrosApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CATALOG) }
    
    // Controle de estado do Menu Lateral e Coroutine para abrí-lo/fechá-lo
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // O Drawer envolve tod o aplicativo
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Astros App",
                    modifier = Modifier.padding(24.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
                
                // Monta os botões do Menu Sanduíche (incluindo Configurações)
                AppDestinations.entries.forEach { destination ->
                    NavigationDrawerItem(
                        label = { Text(destination.label) },
                        icon = { Icon(destination.icon, contentDescription = null) },
                        selected = destination == currentDestination,
                        onClick = {
                            currentDestination = destination
                            scope.launch { drawerState.close() } // Fecha o menu ao clicar
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        // O conteúdo interno continua sendo gerenciado pela barra inferior
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                // Filtramos a tela de configurações para ELA NÃO APARECER na barra inferior
                val bottomBarItems = listOf(
                    AppDestinations.CATALOG, 
                    AppDestinations.EVENTS, 
                    AppDestinations.QUIZ
                )
                
                bottomBarItems.forEach { destination ->
                    item(
                        icon     = { Icon(destination.icon, contentDescription = destination.label) },
                        label    = { Text(destination.label) },
                        selected = destination == currentDestination,
                        onClick  = { currentDestination = destination }
                    )
                }
            }
        ) {
            // Função que repassamos para as telas poderem abrir o menu no clique do TopAppBar
            val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }

            when (currentDestination) {
                AppDestinations.CATALOG -> CatalogTab(openDrawer = openDrawer)
                AppDestinations.EVENTS  -> EventsScreen(openDrawer = openDrawer)
                AppDestinations.QUIZ    -> QuizScreen(openDrawer = openDrawer)
                AppDestinations.PROFILE -> ProfileScreen(openDrawer = openDrawer)
                AppDestinations.SETTINGS -> SettingsScreen(openDrawer = openDrawer)
            }
        }
    }
}