package com.example.astros.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// =============================================================================
// NASA API — Estrutura de Retorno (JSON)
//
// A API da NASA retorna um JSON complexo. Criamos estas Data Classes (DTOs)
// apenas com os campos que nos interessam para que o Gson (conversor do Retrofit)
// preencha os objetos automaticamente para nós.
//
// Exemplo do JSON da NASA:
// {
//   "collection": {
//     "items": [
//       { "links": [ { "href": "https://link-da-imagem.jpg" } ] }
//     ]
//   }
// }
// =============================================================================

data class NasaResponse(val collection: NasaCollection)
data class NasaCollection(val items: List<NasaItem>)
data class NasaItem(val links: List<NasaLink>?)
data class NasaLink(val href: String)

// =============================================================================
// NasaApiService — Interface do Retrofit
//
// Define OS ENDPOINTS (as URLs) que vamos chamar.
// O @GET indica que é uma requisição HTTP GET.
// A função é `suspend` porque requisições de rede demoram e devem rodar em
// background (Coroutines) para não travar a tela do celular (a UI Thread).
// =============================================================================
interface NasaApiService {
    // A URL final será: https://images-api.nasa.gov/search?media_type=image&nasa_id=ID
    @GET("search?media_type=image")
    suspend fun searchImages(@Query("nasa_id") query: String): NasaResponse
}

// =============================================================================
// NasaApi — O Objeto Singleton (Único)
//
// Esta classe constrói o cliente Retrofit. Usamos `by lazy` para garantir
// que o Retrofit só será instanciado na primeira vez que alguém tentar usá-lo,
// economizando memória.
// =============================================================================
object NasaApi {
    private const val BASE_URL = "https://images-api.nasa.gov/"

    val retrofitService: NasaApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON -> Kotlin
            .baseUrl(BASE_URL)
            .build()
            .create(NasaApiService::class.java)
    }
}
