package com.example.astros.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// =============================================================================
// CatalogRepository — Fonte de Dados do Catálogo
//
// 🔧 PONTO PARA DEFESA AO VIVO:
// O professor pode notar que imagens da NASA vinham erradas (ex: Vênus trazia
// foto de foguete). Isso ocorre porque buscas abertas (q=Venus) trazem lixo.
// A solução profissional foi mudar a API para buscar pelo `nasa_id` exato
// de imagens previamente curadas.
// =============================================================================
class CatalogRepository {

    private val _celestialBodies = listOf(
        // ESTRELAS
        CelestialBody(
            id = "sol",
            name = "Sol",
            category = "Estrelas",
            shortDescription = "A estrela central do Sistema Solar.",
            detailedDescription = "O Sol é uma estrela anã amarela e a principal fonte de energia para a vida na Terra. Corresponde a 99,86% da massa do Sistema Solar.",
            nasaSearchTerm = "GSFC_20171208_Archive_e001427" // Imagem exata do Sol
        ),
        CelestialBody(
            id = "sirius",
            name = "Sirius",
            category = "Estrelas",
            shortDescription = "A estrela mais brilhante do céu noturno.",
            detailedDescription = "Sirius é um sistema estelar binário e a estrela mais brilhante vista da Terra. Está localizada na constelação de Cão Maior.",
            nasaSearchTerm = "PIA23122" // Hubble star field
        ),

        // PLANETAS
        CelestialBody(
            id = "mercurio",
            name = "Mercúrio",
            category = "Planetas",
            shortDescription = "O menor planeta do Sistema Solar.",
            detailedDescription = "Mercúrio é o planeta mais próximo do Sol. Não possui satélites naturais e sua superfície é marcada por crateras.",
            nasaSearchTerm = "PIA11364" // Foto real de Mercúrio
        ),
        CelestialBody(
            id = "venus",
            name = "Vênus",
            category = "Planetas",
            shortDescription = "O planeta mais quente do Sistema Solar.",
            detailedDescription = "Vênus tem uma atmosfera densa de dióxido de carbono, causando um forte efeito estufa.",
            nasaSearchTerm = "PIA00248" // Foto real da superfície/nuvens de Vênus (não o foguete!)
        ),
        CelestialBody(
            id = "terra",
            name = "Terra",
            category = "Planetas",
            shortDescription = "O nosso lar e o único com vida conhecida.",
            detailedDescription = "A Terra é o terceiro planeta e o único corpo celeste conhecido a abrigar vida.",
            nasaSearchTerm = "PIA18033" // A famosa Blue Marble
        ),
        CelestialBody(
            id = "marte",
            name = "Marte",
            category = "Planetas",
            shortDescription = "O famoso Planeta Vermelho.",
            detailedDescription = "Marte é conhecido pela abundância de óxido de ferro em sua superfície.",
            nasaSearchTerm = "PIA01591" // Foto real de Marte
        ),
        CelestialBody(
            id = "jupiter",
            name = "Júpiter",
            category = "Planetas",
            shortDescription = "O maior planeta do Sistema Solar.",
            detailedDescription = "Júpiter é um gigante gasoso colossal, famoso por sua Grande Mancha Vermelha.",
            nasaSearchTerm = "PIA22946" // Foto incrível de Júpiter por Juno
        ),
        CelestialBody(
            id = "saturno",
            name = "Saturno",
            category = "Planetas",
            shortDescription = "O planeta dos anéis.",
            detailedDescription = "Saturno é um gigante gasoso conhecido por seu complexo sistema de anéis.",
            nasaSearchTerm = "PIA01364" // Cassini Saturn photo
        ),

        // LUAS
        CelestialBody(
            id = "lua",
            name = "Lua",
            category = "Luas",
            shortDescription = "O satélite natural da Terra.",
            detailedDescription = "A Lua é o único satélite natural da Terra e o quinto maior do Sistema Solar. Sua gravidade é responsável pelas marés oceânicas.",
            nasaSearchTerm = "PIA00405" // Foto da nossa Lua
        ),
        CelestialBody(
            id = "tita",
            name = "Titã",
            category = "Luas",
            shortDescription = "A maior lua de Saturno.",
            detailedDescription = "Titã é a única lua do Sistema Solar com uma atmosfera densa, e o único objeto além da Terra onde foram encontrados corpos líquidos estáveis na superfície.",
            nasaSearchTerm = "PIA14602" // Foto de Titã
        ),
        CelestialBody(
            id = "europa",
            name = "Europa",
            category = "Luas",
            shortDescription = "Uma lua de Júpiter coberta de gelo.",
            detailedDescription = "Acredita-se que Europa possua um oceano global de água líquida abaixo de sua crosta de gelo, o que a torna um dos melhores lugares para procurar vida extraterrestre.",
            nasaSearchTerm = "PIA00502" // Foto de Europa
        ),

        // ASTEROIDES E COMETAS
        CelestialBody(
            id = "halley",
            name = "Cometa Halley",
            category = "Asteroides e Cometas",
            shortDescription = "O cometa mais famoso do mundo.",
            detailedDescription = "O Halley é um cometa periódico visível da Terra a cada 75-76 anos. É o único cometa de curto período que é regularmente visível a olho nu.",
            nasaSearchTerm = "PIA10969" // Cometa
        ),
        CelestialBody(
            id = "ceres",
            name = "Ceres",
            category = "Asteroides e Cometas",
            shortDescription = "O maior objeto do cinturão de asteroides.",
            detailedDescription = "Ceres é o único planeta anão localizado no cinturão de asteroides entre Marte e Júpiter. Contém uma grande quantidade de gelo de água.",
            nasaSearchTerm = "PIA21079" // Ceres real
        )
    )

    fun getLocalBodies(): List<CelestialBody> {
        return _celestialBodies
    }

    fun getCategories(): List<String> {
        return _celestialBodies.map { it.category }.distinct()
    }

    suspend fun getImageUrlFor(searchTerm: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                // searchTerm agora contém o NASA_ID exato em vez de texto genérico!
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
