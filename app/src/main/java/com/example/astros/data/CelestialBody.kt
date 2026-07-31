package com.example.astros.data

// =============================================================================
// CelestialBody — Modelo de Dados
//
// Representa um corpo celeste no nosso catálogo.
// Como não temos banco de dados real, esta classe estrutura as informações
// que vamos guardar na nossa lista local.
// =============================================================================
data class CelestialBody(
    val id: String,
    val name: String,
    val category: String,            // Nova propriedade de Categoria
    val shortDescription: String,
    val detailedDescription: String,
    val nasaSearchTerm: String,      // Termo exato para buscar na NASA API (ex: "Jupiter planet")
    var imageUrl: String? = null     // A URL da imagem que a NASA vai nos devolver (começa nulo)
)
