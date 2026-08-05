package com.example.astros.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

// =============================================================================
// MatchHistoryRepository — Repositório Singleton de Histórico de Partidas
//
// Singleton: todos os ViewModels compartilham a MESMA instância e o MESMO
// StateFlow. Quando o Quiz salva uma partida, o ProfileScreen atualiza
// automaticamente sem precisar recarregar.
//
// Uso: MatchHistoryRepository.getInstance(context)
// =============================================================================
class MatchHistoryRepository private constructor(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences("AstrosMatchHistory", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val type = object : TypeToken<List<MatchRecord>>() {}.type

    companion object {
        private const val KEY_HISTORY  = "match_history"
        private const val MAX_RECORDS  = 10

        @Volatile
        private var INSTANCE: MatchHistoryRepository? = null

        // Thread-safe singleton — todos os ViewModels recebem a mesma instância
        fun getInstance(context: Context): MatchHistoryRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MatchHistoryRepository(context).also { INSTANCE = it }
            }
        }
    }

    // StateFlow compartilhado — qualquer observador vê as atualizações em tempo real
    private val _history = MutableStateFlow<List<MatchRecord>>(emptyList())
    val history: Flow<List<MatchRecord>> = _history.asStateFlow()

    init {
        _history.value = loadFromPrefs()
    }

    suspend fun insert(record: MatchRecord) = withContext(Dispatchers.IO) {
        val current = loadFromPrefs().toMutableList()
        current.add(0, record)
        val trimmed = if (current.size > MAX_RECORDS) current.take(MAX_RECORDS) else current
        prefs.edit().putString(KEY_HISTORY, gson.toJson(trimmed)).apply()
        _history.value = trimmed  // Notifica TODOS os observadores imediatamente
    }

    private fun loadFromPrefs(): List<MatchRecord> {
        val json = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        return try { gson.fromJson(json, type) ?: emptyList() } catch (e: Exception) { emptyList() }
    }
}
