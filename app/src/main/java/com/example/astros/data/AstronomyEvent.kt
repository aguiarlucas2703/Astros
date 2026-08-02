package com.example.astros.data

import java.time.LocalDate

// =============================================================================
// AstronomyEvent — Modelo de Dados para a aba de Eventos
//
// Utiliza `java.time.LocalDate` (API nativa do Java 8+) para
// representar a data do evento, facilitando a comparação
// com a data atual (isBefore, isAfter, isEqual).
// =============================================================================
data class AstronomyEvent(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate,
    val type: EventType
)

enum class EventType {
    ECLIPSE,
    METEOR_SHOWER, // Chuva de meteoros
    PLANETARY,     // Alinhamento planetário, conjunção
    MOON           // Superlua, lua azul
}
