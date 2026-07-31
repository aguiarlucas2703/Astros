package com.example.astros.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// =============================================================================
// ESQUEMA DE CORES — LIGHT MODE
// lightColorScheme() cria um ColorScheme do Material3 com os campos que
// passamos. Os campos não informados ficam com o padrão do Material3.
// =============================================================================
private val LightColorScheme = lightColorScheme(
    primary                = md_light_primary,
    onPrimary              = md_light_onPrimary,
    primaryContainer       = md_light_primaryContainer,
    onPrimaryContainer     = md_light_onPrimaryContainer,
    secondary              = md_light_secondary,
    onSecondary            = md_light_onSecondary,
    secondaryContainer     = md_light_secondaryContainer,
    onSecondaryContainer   = md_light_onSecondaryContainer,
    background             = md_light_background,
    onBackground           = md_light_onBackground,
    surface                = md_light_surface,
    onSurface              = md_light_onSurface,
)

// =============================================================================
// ESQUEMA DE CORES — DARK MODE
// Já definido e pronto! Basta remover o `darkTheme = false` no AstrosTheme
// (ou deixar o sistema escolher via isSystemInDarkTheme()) para ativá-lo.
// =============================================================================
private val DarkColorScheme = darkColorScheme(
    primary                = md_dark_primary,
    onPrimary              = md_dark_onPrimary,
    primaryContainer       = md_dark_primaryContainer,
    onPrimaryContainer     = md_dark_onPrimaryContainer,
    secondary              = md_dark_secondary,
    onSecondary            = md_dark_onSecondary,
    secondaryContainer     = md_dark_secondaryContainer,
    onSecondaryContainer   = md_dark_onSecondaryContainer,
    background             = md_dark_background,
    onBackground           = md_dark_onBackground,
    surface                = md_dark_surface,
    onSurface              = md_dark_onSurface,
)

// =============================================================================
// AstrosTheme — função de tema principal do app
//
// Como funciona:
//   1. `darkTheme` recebe automaticamente o modo do sistema (isSystemInDarkTheme)
//   2. Escolhemos o esquema de cores baseado nisso
//   3. Passamos tudo pro MaterialTheme, que distribui as cores para todos os
//      componentes do app automaticamente (botões, textos, cards, etc.)
//
// NOTA: Desabilitamos `dynamicColor` (cores dinâmicas do Android 12+) de propósito
// para garantir que NOSSA paleta astronômica seja sempre usada, independente do
// papel de parede do usuário.
// =============================================================================
@Composable
fun AstrosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}