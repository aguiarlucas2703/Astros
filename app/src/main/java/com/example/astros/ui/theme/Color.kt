package com.example.astros.ui.theme

import androidx.compose.ui.graphics.Color

// =============================================================================
// PALETA DE CORES — APP ASTROS
// Inspirada no logo: tons de azul profundo, ciano e branco estelar.
//
// Convenção de nomenclatura Material3:
//   - "primary"   = cor principal do app (botões, FABs, ícones ativos)
//   - "Container" = fundo de chips, cards destacados
//   - "on*"       = cor do texto/ícone que fica SOBRE aquela cor
// =============================================================================

// --- Azuis base (extraídos do logo) ------------------------------------------
val SpaceNavy       = Color(0xFF0A0F2E)   // azul-marinho escuríssimo (fundo night)
val SpaceBlue       = Color(0xFF0D1B4B)   // azul espacial profundo
val CosmicBlue      = Color(0xFF1565C0)   // azul primário rico
val StellarBlue     = Color(0xFF1E88E5)   // azul médio vibrante
val NebulaCyan      = Color(0xFF4DD0E1)   // ciano nebulosa (accent)
val StarWhite       = Color(0xFFE3F2FD)   // branco azulado (texto sobre fundo escuro)

// --- Variantes claras (modo claro / containers) ------------------------------
val LightSky        = Color(0xFFBBDEFB)   // azul céu claro
val PaleSky         = Color(0xFFE3F2FD)   // azul muito claro (fundo light)
val DeepOcean       = Color(0xFF0D47A1)   // azul escuro para containers dark
val CyanDark        = Color(0xFF00838F)   // ciano escuro para secondary dark

// --- Neutros -----------------------------------------------------------------
val OnDarkSurface   = Color(0xFFCAD9F0)   // cinza-azulado para textos secundários
val SurfaceDark     = Color(0xFF111C44)   // superfície escura de cards
val SurfaceLight    = Color(0xFFF0F7FF)   // superfície clara

// =============================================================================
// ESQUEMA CLARO (Light Mode)
// Estruturado aqui como constantes para referência fácil no Theme.kt
// =============================================================================
val md_light_primary                = CosmicBlue
val md_light_onPrimary              = Color.White
val md_light_primaryContainer       = LightSky
val md_light_onPrimaryContainer     = SpaceBlue
val md_light_secondary              = StellarBlue
val md_light_onSecondary            = Color.White
val md_light_secondaryContainer     = PaleSky
val md_light_onSecondaryContainer   = DeepOcean
val md_light_background             = Color(0xFFF8FBFF)
val md_light_onBackground           = Color(0xFF0A0F2E)
val md_light_surface                = SurfaceLight
val md_light_onSurface              = Color(0xFF0A0F2E)

// =============================================================================
// ESQUEMA ESCURO (Dark Mode) — já preparado para ativar no futuro
// Basta passar darkTheme = true no AstrosTheme para usar estas cores.
// =============================================================================
val md_dark_primary                 = Color(0xFF90CAF9)   // azul claro sobre fundo escuro
val md_dark_onPrimary               = SpaceNavy
val md_dark_primaryContainer        = CosmicBlue
val md_dark_onPrimaryContainer      = LightSky
val md_dark_secondary               = NebulaCyan
val md_dark_onSecondary             = Color(0xFF003641)
val md_dark_secondaryContainer      = CyanDark
val md_dark_onSecondaryContainer    = Color(0xFFA0F0FF)
val md_dark_background              = SpaceNavy
val md_dark_onBackground            = StarWhite
val md_dark_surface                 = SurfaceDark
val md_dark_onSurface               = StarWhite