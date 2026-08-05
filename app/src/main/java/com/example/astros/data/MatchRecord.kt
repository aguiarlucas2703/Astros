package com.example.astros.data

// =============================================================================
// MatchRecord — Modelo de dados de uma partida concluída
//
// Armazena o tipo de jogo, pontuação, total de questões, XP ganho e timestamp.
// Serializado como JSON via Gson para persistência em SharedPreferences.
// =============================================================================
data class MatchRecord(
    val id: Long        = System.currentTimeMillis(),
    val gameType: String,   // "Quiz" ou "Adivinhe"
    val score: Int,
    val total: Int,
    val xpEarned: Int,
    val timestamp: Long = System.currentTimeMillis()
)
