package com.example.astros.ui.screens

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RankInfo(
    val title: String,
    val emoji: String,
    val minXp: Int,
    val maxXp: Int
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("AstrosQuizPrefs", Context.MODE_PRIVATE)

    private val _totalXp = MutableStateFlow(prefs.getInt("TOTAL_XP", 0))
    val totalXp: StateFlow<Int> = _totalXp.asStateFlow()

    fun refreshXp() {
        _totalXp.value = prefs.getInt("TOTAL_XP", 0)
    }

    fun getCurrentRank(xp: Int): RankInfo {
        return when {
            xp <= 100 -> RankInfo("Turista Espacial", "🎒", 0, 100)
            xp <= 300 -> RankInfo("Cadete da Frota Estelar", "🚀", 101, 300)
            xp <= 600 -> RankInfo("Astronauta Veterano", "👨‍🚀", 301, 600)
            else -> RankInfo("Lenda Galáctica", "🌌", 601, 9999) // Max rank
        }
    }
}
