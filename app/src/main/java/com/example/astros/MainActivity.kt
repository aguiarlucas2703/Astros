package com.example.astros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astros.ui.components.StarryBackground
import com.example.astros.ui.components.SunnyBackground
import com.example.astros.ui.screens.AboutScreen
import com.example.astros.ui.screens.CatalogTab
import com.example.astros.ui.screens.EventsScreen
import com.example.astros.ui.screens.GuessScreen
import com.example.astros.ui.screens.IssScreen
import com.example.astros.ui.screens.ProfileScreen
import com.example.astros.ui.screens.QuizScreen
import com.example.astros.ui.screens.SettingsScreen
import com.example.astros.ui.screens.SettingsViewModel
import com.example.astros.ui.screens.ThemeMode
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
            val settingsViewModel: SettingsViewModel = viewModel()
            val themeMode by settingsViewModel.themeMode.collectAsState()

            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT  -> false
                ThemeMode.DARK   -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            AstrosTheme(darkTheme = darkTheme) {
                AstrosApp(darkTheme)
            }
        }
    }
}

// =============================================================================
// AppDestinations — enum que define todos os destinos de navegação do app.
// SETTINGS e ABOUT só aparecem no Menu Sanduíche (não na barra inferior).
// =============================================================================
enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    CATALOG    ("Catálogo",       Icons.Default.Explore),
    EVENTS     ("Eventos",        Icons.Default.Event),
    QUIZ       ("Quiz",           Icons.Default.Quiz),
    GUESS_GAME ("Adivinhe",       Icons.Default.Search),
    ISS_TRACKER("Rastreador ISS", Icons.Default.Satellite),
    PROFILE    ("Meu Perfil",     Icons.Default.Person),
    SETTINGS   ("Configurações",  Icons.Default.Settings),
    ABOUT      ("Sobre",          Icons.Default.Info),
}

// =============================================================================
// AstrosApp — Composable raiz.
// ModalNavigationDrawer (menu sanduíche) envolve o NavigationSuiteScaffold
// (barra de navegação inferior). Configurações e Sobre só ficam no drawer.
// =============================================================================
@Composable
fun AstrosApp(darkTheme: Boolean) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CATALOG) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }

    // Intercepta o botão/gesto de voltar do Android:
    // 1º) Fecha o drawer se estiver aberto
    // 2º) Volta para o Catálogo se estiver em outra tela
    // 3º) Se já no Catálogo, deixa o sistema agir (minimiza o app)
    BackHandler(enabled = drawerState.isOpen || currentDestination != AppDestinations.CATALOG) {
        when {
            drawerState.isOpen -> scope.launch { drawerState.close() }
            else -> currentDestination = AppDestinations.CATALOG
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color.Transparent) {
                // Aplica o tema correto para que ícones e textos tenham contraste adequado
                AstrosTheme(darkTheme = darkTheme) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Fundo adaptativo: estrelas no escuro, sol no claro
                        if (darkTheme) StarryBackground() else SunnyBackground()

                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Astros App",
                                modifier = Modifier.padding(24.dp),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

                            AppDestinations.entries.forEach { destination ->
                                NavigationDrawerItem(
                                    label    = { Text(destination.label) },
                                    icon     = { Icon(destination.icon, contentDescription = null) },
                                    selected = destination == currentDestination,
                                    onClick  = {
                                        currentDestination = destination
                                        scope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                                    colors   = NavigationDrawerItemDefaults.colors(
                                        unselectedContainerColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        // Barra de navegação inferior — apenas as 3 abas principais
        val bottomBarItems = listOf(
            AppDestinations.CATALOG,
            AppDestinations.EVENTS,
            AppDestinations.QUIZ,
        )

        NavigationSuiteScaffold(
            navigationSuiteItems = {
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
            when (currentDestination) {
                AppDestinations.CATALOG     -> CatalogTab(openDrawer = openDrawer)
                AppDestinations.EVENTS      -> EventsScreen(openDrawer = openDrawer)
                AppDestinations.QUIZ        -> QuizScreen(openDrawer = openDrawer)
                AppDestinations.GUESS_GAME  -> GuessScreen(openDrawer = openDrawer)
                AppDestinations.ISS_TRACKER -> IssScreen(openDrawer = openDrawer)
                AppDestinations.PROFILE     -> ProfileScreen(openDrawer = openDrawer)
                AppDestinations.SETTINGS    -> SettingsScreen(openDrawer = openDrawer)
                AppDestinations.ABOUT       -> AboutScreen(openDrawer = openDrawer)
            }
        }
    }
}