package com.example.astros.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astros.data.CatalogRepository
import com.example.astros.data.CelestialBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// =============================================================================
// CatalogViewModel — Gerenciador de Estado do Catálogo
// =============================================================================
class CatalogViewModel : ViewModel() {
    private val repository = CatalogRepository()

    // O Estado da nossa tela: lista completa de corpos celestes
    private val _uiState = MutableStateFlow<List<CelestialBody>>(emptyList())
    val uiState: StateFlow<List<CelestialBody>> = _uiState.asStateFlow()

    // Lista de categorias disponíveis (Planetas, Estrelas, etc)
    val categories: List<String> = repository.getCategories()

    init {
        // Apenas carrega os dados locais (textos). Não bate na NASA no início!
        _uiState.value = repository.getLocalBodies()
    }

    // Retorna apenas os corpos celestes de uma determinada categoria
    fun getBodiesByCategory(category: String): List<CelestialBody> {
        return _uiState.value.filter { it.category == category }
    }

    // =========================================================================
    // Utiliza Lazy Loading. As imagens não são carregadas de uma vez no início.
    // O app realiza chamadas à API apenas ao selecionar a categoria,
    // economizando banda e otimizando a performance inicial.
    // =========================================================================
    fun loadImagesForCategory(category: String) {
        val bodiesToLoad = getBodiesByCategory(category)
        
        bodiesToLoad.forEach { body ->
            // Só faz a requisição se a imagem ainda estiver nula
            if (body.imageUrl == null) {
                viewModelScope.launch {
                    val imageUrl = repository.getImageUrlFor(body.nasaSearchTerm)
                    if (imageUrl != null) {
                        updateImageForBody(body.id, imageUrl)
                    }
                }
            }
        }
    }

    private fun updateImageForBody(id: String, imageUrl: String) {
        val currentList = _uiState.value
        val updatedList = currentList.map { body ->
            if (body.id == id) body.copy(imageUrl = imageUrl) else body
        }
        _uiState.value = updatedList
    }
}
