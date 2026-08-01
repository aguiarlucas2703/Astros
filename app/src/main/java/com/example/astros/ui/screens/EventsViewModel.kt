package com.example.astros.ui.screens

import androidx.lifecycle.ViewModel
import com.example.astros.data.AstronomyEvent
import com.example.astros.data.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// Modelo específico para a UI, que já inclui a lógica calculada
data class EventUiModel(
    val event: AstronomyEvent,
    val isPast: Boolean,
    val daysDifference: Long // Dias até o evento (positivo) ou dias desde o evento (negativo)
)

// =============================================================================
// EventsViewModel — Processa as lógicas de tempo e ordenação
// =============================================================================
class EventsViewModel : ViewModel() {
    private val repository = EventsRepository()

    private val _uiState = MutableStateFlow<List<EventUiModel>>(emptyList())
    val uiState: StateFlow<List<EventUiModel>> = _uiState.asStateFlow()

    init {
        loadAndProcessEvents()
    }

    private fun loadAndProcessEvents() {
        // 1. Pega a data exata em que o usuário abriu a aba
        val today = LocalDate.now() 
        val rawEvents = repository.getAllEvents()

        // 2. Calcula para cada evento se ele é passado ou futuro
        val processedList = rawEvents.map { event ->
            val isPast = event.date.isBefore(today)
            val daysDiff = ChronoUnit.DAYS.between(today, event.date)
            EventUiModel(event, isPast, daysDiff)
        }

        // 3. Regra de Negócio (Ordenação):
        //    - Eventos futuros vêm PRIMEIRO (ordenados do mais próximo para o mais distante)
        //    - Eventos passados vêm DEPOIS (ordenados do mais recente que passou para o mais antigo)
        val sortedList = processedList.sortedWith(
            compareBy<EventUiModel> { it.isPast } // Futuros (isPast=false) vêm antes de Passados (isPast=true)
                .thenBy { 
                    if (it.isPast) -it.event.date.toEpochDay() // Ordem decrescente para passados
                    else it.event.date.toEpochDay()            // Ordem crescente para futuros
                }
        )

        _uiState.value = sortedList
    }
}
