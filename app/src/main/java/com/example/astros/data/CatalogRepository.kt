package com.example.astros.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// =============================================================================
// CatalogRepository — Fonte de Dados do Catálogo
// =============================================================================
class CatalogRepository {

    // Adicionamos categorias e novos itens (Luas, Cometas, Estrelas)
    private val _celestialBodies = listOf(
        // ESTRELAS
        CelestialBody(
            id = "sol",
            name = "Sol",
            category = "Estrelas",
            shortDescription = "A estrela central do Sistema Solar.",
            detailedDescription = "O Sol é uma estrela anã amarela e a principal fonte de energia para a vida na Terra. Corresponde a 99,86% da massa do Sistema Solar.",
            nasaSearchTerm = "Sun star"
        ),
        CelestialBody(
            id = "sirius",
            name = "Sirius",
            category = "Estrelas",
            shortDescription = "A estrela mais brilhante do céu noturno.",
            detailedDescription = "Sirius é um sistema estelar binário e a estrela mais brilhante vista da Terra. Está localizada na constelação de Cão Maior, a apenas 8,6 anos-luz de distância.",
            nasaSearchTerm = "Sirius star"
        ),

        // PLANETAS
        CelestialBody(
            id = "mercurio",
            name = "Mercúrio",
            category = "Planetas",
            shortDescription = "O menor planeta do Sistema Solar.",
            detailedDescription = "Mercúrio é o planeta mais próximo do Sol. Não possui satélites naturais e sua superfície é marcada por crateras.",
            nasaSearchTerm = "Mercury planet"
        ),
        CelestialBody(
            id = "venus",
            name = "Vênus",
            category = "Planetas",
            shortDescription = "O planeta mais quente do Sistema Solar.",
            detailedDescription = "Vênus tem uma atmosfera densa de dióxido de carbono, causando um forte efeito estufa.",
            nasaSearchTerm = "Venus planet"
        ),
        CelestialBody(
            id = "terra",
            name = "Terra",
            category = "Planetas",
            shortDescription = "O nosso lar e o único com vida conhecida.",
            detailedDescription = "A Terra é o terceiro planeta e o único corpo celeste conhecido a abrigar vida.",
            nasaSearchTerm = "Earth from space"
        ),
        CelestialBody(
            id = "marte",
            name = "Marte",
            category = "Planetas",
            shortDescription = "O famoso Planeta Vermelho.",
            detailedDescription = "Marte é conhecido pela abundância de óxido de ferro em sua superfície.",
            nasaSearchTerm = "Mars planet"
        ),
        CelestialBody(
            id = "jupiter",
            name = "Júpiter",
            category = "Planetas",
            shortDescription = "O maior planeta do Sistema Solar.",
            detailedDescription = "Júpiter é um gigante gasoso colossal, famoso por sua Grande Mancha Vermelha.",
            nasaSearchTerm = "Jupiter planet"
        ),
        CelestialBody(
            id = "saturno",
            name = "Saturno",
            category = "Planetas",
            shortDescription = "O planeta dos anéis.",
            detailedDescription = "Saturno é um gigante gasoso conhecido por seu complexo sistema de anéis.",
            nasaSearchTerm = "Saturn planet"
        ),

        // LUAS
        CelestialBody(
            id = "lua",
            name = "Lua",
            category = "Luas",
            shortDescription = "O satélite natural da Terra.",
            detailedDescription = "A Lua é o único satélite natural da Terra e o quinto maior do Sistema Solar. Sua gravidade é responsável pelas marés oceânicas.",
            nasaSearchTerm = "Earth Moon"
        ),
        CelestialBody(
            id = "tita",
            name = "Titã",
            category = "Luas",
            shortDescription = "A maior lua de Saturno.",
            detailedDescription = "Titã é a única lua do Sistema Solar com uma atmosfera densa, e o único objeto além da Terra onde foram encontrados corpos líquidos estáveis na superfície.",
            nasaSearchTerm = "Titan moon Saturn"
        ),
        CelestialBody(
            id = "europa",
            name = "Europa",
            category = "Luas",
            shortDescription = "Uma lua de Júpiter coberta de gelo.",
            detailedDescription = "Acredita-se que Europa possua um oceano global de água líquida abaixo de sua crosta de gelo, o que a torna um dos melhores lugares para procurar vida extraterrestre.",
            nasaSearchTerm = "Europa moon Jupiter"
        ),

        // ASTEROIDES E COMETAS
        CelestialBody(
            id = "halley",
            name = "Cometa Halley",
            category = "Asteroides e Cometas",
            shortDescription = "O cometa mais famoso do mundo.",
            detailedDescription = "O Halley é um cometa periódico visível da Terra a cada 75-76 anos. É o único cometa de curto período que é regularmente visível a olho nu.",
            nasaSearchTerm = "Halley's Comet"
        ),
        CelestialBody(
            id = "ceres",
            name = "Ceres",
            category = "Asteroides e Cometas",
            shortDescription = "O maior objeto do cinturão de asteroides.",
            detailedDescription = "Ceres é o único planeta anão localizado no cinturão de asteroides entre Marte e Júpiter. Contém uma grande quantidade de gelo de água.",
            nasaSearchTerm = "Ceres dwarf planet"
        )
    )

    fun getLocalBodies(): List<CelestialBody> {
        return _celestialBodies
    }

    // Extrai todas as categorias únicas que existem na lista local
    fun getCategories(): List<String> {
        // map pega só as categorias, distinct remove repetidas
        return _celestialBodies.map { it.category }.distinct()
    }

    suspend fun getImageUrlFor(searchTerm: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = NasaApi.retrofitService.searchImages(searchTerm)
                val items = response.collection.items
                if (items.isNotEmpty() && !items[0].links.isNullOrEmpty()) {
                    items[0].links!![0].href
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
