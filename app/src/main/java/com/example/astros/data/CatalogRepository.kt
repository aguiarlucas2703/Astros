package com.example.astros.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// =============================================================================
// CatalogRepository — Fonte de Dados do Catálogo
//
// Utiliza nasa_id para garantir que a imagem retornada pela API seja exata
// e de alta qualidade, otimizando as buscas.
// =============================================================================
class CatalogRepository {

    private val _celestialBodies = listOf(
        // ================= ESTRELAS =================
        CelestialBody(
            id = "sol",
            name = "Sol",
            category = "Estrelas",
            shortDescription = "A estrela central do Sistema Solar.",
            detailedDescription = "O Sol é uma estrela anã amarela e a principal fonte de energia para a vida na Terra. Corresponde a 99,86% da massa do Sistema Solar.",
            nasaSearchTerm = "PIA09320" // Full Disk Image of the Sun
        ),
        CelestialBody(
            id = "sirius",
            name = "Sirius",
            category = "Estrelas",
            shortDescription = "A estrela mais brilhante do céu noturno.",
            detailedDescription = "Sirius é um sistema estelar binário e a estrela mais brilhante vista da Terra. Está localizada na constelação de Cão Maior.",
            nasaSearchTerm = "GSFC_20171208_Archive_e001783" // Imagem estelar (Sirius)
        ),
        CelestialBody(
            id = "betelgeuse",
            name = "Betelgeuse",
            category = "Estrelas",
            shortDescription = "Supergigante vermelha em Órion.",
            detailedDescription = "Betelgeuse é uma das maiores e mais luminosas estrelas conhecidas. Se estivesse no centro do Sistema Solar, engoliria a órbita de Marte e possivelmente a de Júpiter.",
            nasaSearchTerm = "PIA16680" 
        ),
        CelestialBody(
            id = "alphacentauri",
            name = "Alpha Centauri",
            category = "Estrelas",
            shortDescription = "O sistema estelar mais próximo do Sol.",
            detailedDescription = "A apenas 4,37 anos-luz de distância, Alpha Centauri é um sistema triplo de estrelas que abriga a estrela Proxima Centauri, a mais próxima da Terra (fora o Sol).",
            nasaSearchTerm = "GSFC_20171208_Archive_e000214" 
        ),

        // ================= PLANETAS =================
        CelestialBody(
            id = "mercurio",
            name = "Mercúrio",
            category = "Planetas",
            shortDescription = "O menor planeta do Sistema Solar.",
            detailedDescription = "Mercúrio é o planeta mais próximo do Sol. Não possui satélites naturais e sua superfície é marcada por crateras.",
            nasaSearchTerm = "PIA11364" 
        ),
        CelestialBody(
            id = "venus",
            name = "Vênus",
            category = "Planetas",
            shortDescription = "O planeta mais quente do Sistema Solar.",
            detailedDescription = "Vênus tem uma atmosfera densa de dióxido de carbono, causando um forte efeito estufa.",
            nasaSearchTerm = "PIA00248" 
        ),
        CelestialBody(
            id = "terra",
            name = "Terra",
            category = "Planetas",
            shortDescription = "O nosso lar e o único com vida conhecida.",
            detailedDescription = "A Terra é o terceiro planeta e o único corpo celeste conhecido a abrigar vida.",
            nasaSearchTerm = "PIA18033" 
        ),
        CelestialBody(
            id = "marte",
            name = "Marte",
            category = "Planetas",
            shortDescription = "O famoso Planeta Vermelho.",
            detailedDescription = "Marte é conhecido pela abundância de óxido de ferro em sua superfície.",
            nasaSearchTerm = "PIA01591" 
        ),
        CelestialBody(
            id = "jupiter",
            name = "Júpiter",
            category = "Planetas",
            shortDescription = "O maior planeta do Sistema Solar.",
            detailedDescription = "Júpiter é um gigante gasoso colossal, famoso por sua Grande Mancha Vermelha.",
            nasaSearchTerm = "PIA22946" 
        ),
        CelestialBody(
            id = "saturno",
            name = "Saturno",
            category = "Planetas",
            shortDescription = "O planeta dos anéis.",
            detailedDescription = "Saturno é um gigante gasoso conhecido por seu complexo sistema de anéis.",
            nasaSearchTerm = "PIA01364" 
        ),
        CelestialBody(
            id = "plutao",
            name = "Plutão",
            category = "Planetas",
            shortDescription = "O planeta anão mais famoso.",
            detailedDescription = "Rebaixado a planeta anão em 2006, Plutão possui uma superfície incrivelmente complexa com planícies de gelo em formato de coração.",
            nasaSearchTerm = "GSFC_20171208_Archive_e000682" 
        ),

        // ================= LUAS =================
        CelestialBody(
            id = "lua",
            name = "Lua",
            category = "Luas",
            shortDescription = "O satélite natural da Terra.",
            detailedDescription = "A Lua é o único satélite natural da Terra e o quinto maior do Sistema Solar. Sua gravidade é responsável pelas marés oceânicas.",
            nasaSearchTerm = "PIA00405" 
        ),
        CelestialBody(
            id = "tita",
            name = "Titã",
            category = "Luas",
            shortDescription = "A maior lua de Saturno.",
            detailedDescription = "Titã é a única lua do Sistema Solar com uma atmosfera densa, e o único objeto além da Terra onde foram encontrados corpos líquidos estáveis na superfície.",
            nasaSearchTerm = "PIA14602" 
        ),
        CelestialBody(
            id = "europa",
            name = "Europa",
            category = "Luas",
            shortDescription = "Uma lua de Júpiter coberta de gelo.",
            detailedDescription = "Acredita-se que Europa possua um oceano global de água líquida abaixo de sua crosta de gelo, o que a torna um dos melhores lugares para procurar vida extraterrestre.",
            nasaSearchTerm = "PIA00502" 
        ),
        CelestialBody(
            id = "ganimedes",
            name = "Ganimedes",
            category = "Luas",
            shortDescription = "A maior lua do Sistema Solar.",
            detailedDescription = "Maior até que o planeta Mercúrio, Ganimedes é a maior lua de Júpiter e a única lua conhecida a ter seu próprio campo magnético.",
            nasaSearchTerm = "PIA01987" 
        ),
        CelestialBody(
            id = "io",
            name = "Io",
            category = "Luas",
            shortDescription = "A lua mais vulcânica do sistema.",
            detailedDescription = "Io é o corpo mais vulcanicamente ativo do Sistema Solar. O intenso campo gravitacional de Júpiter gera calor interno que causa as erupções.",
            nasaSearchTerm = "PIA01368" 
        ),

        // ================= ASTEROIDES E COMETAS =================
        CelestialBody(
            id = "halley",
            name = "Cometa Halley",
            category = "Asteroides e Cometas",
            shortDescription = "O cometa mais famoso do mundo.",
            detailedDescription = "O Halley é um cometa periódico visível da Terra a cada 75-76 anos. É o único cometa de curto período que é regularmente visível a olho nu.",
            nasaSearchTerm = "PIA17485" // Atualizado para ID do cometa Halley
        ),
        CelestialBody(
            id = "ceres",
            name = "Ceres",
            category = "Asteroides e Cometas",
            shortDescription = "O maior objeto do cinturão de asteroides.",
            detailedDescription = "Ceres é o único planeta anão localizado no cinturão de asteroides entre Marte e Júpiter. Contém uma grande quantidade de gelo de água.",
            nasaSearchTerm = "PIA17830" // Ceres dwarf planet
        ),
        CelestialBody(
            id = "vesta",
            name = "Vesta",
            category = "Asteroides e Cometas",
            shortDescription = "Um gigantesco asteroide rochoso.",
            detailedDescription = "Vesta é o segundo maior objeto no cinturão de asteroides. É o asteroide mais brilhante no céu noturno.",
            nasaSearchTerm = "PIA15794" 
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
