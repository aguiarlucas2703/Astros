package com.example.astros.ui.components

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.astros.R

// =============================================================================
// SoundManager — Efeitos Sonoros (SoundPool) + Música de Fundo (MediaPlayer)
//
// SoundPool: carrega MP3s curtos na memória → toca sem delay (efeitos de jogo)
// MediaPlayer: reproduz o quiz.mp3 em loop como trilha sonora de fundo
// =============================================================================
object SoundManager {

    // --- SoundPool (efeitos curtos) ---
    private var soundPool: SoundPool? = null
    private var idCorrect: Int = 0
    private var idWrong: Int = 0
    private var idWin: Int = 0

    // --- MediaPlayer (música de fundo em loop) --- pré-carregado no init()
    private var backgroundPlayer: MediaPlayer? = null
    private var initialized = false

    fun init(context: Context) {
        if (initialized) return

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(attributes)
            .build()

        val pool = soundPool!!
        idCorrect = pool.load(context, R.raw.correto, 1)
        idWrong   = pool.load(context, R.raw.erro, 1)
        idWin     = pool.load(context, R.raw.winner_gamingsound, 1)

        // Pré-carrega a música de fundo — .start() será instantâneo quando o jogo começar
        backgroundPlayer = MediaPlayer.create(context.applicationContext, R.raw.quiz).apply {
            isLooping = true
            setVolume(0.4f, 0.4f)
            // Não inicia aqui, só prepara na memória
        }

        initialized = true
    }

    // -------------------------------------------------------------------------
    // Efeitos Sonoros
    // -------------------------------------------------------------------------

    fun playCorrect() {
        soundPool?.play(idCorrect, 1f, 1f, 1, 0, 1f)
    }

    fun playWrong() {
        soundPool?.play(idWrong, 1f, 1f, 1, 0, 1f)
    }

    fun playGameComplete() {
        stopBackgroundMusic()           // Para a música antes do som de vitória
        soundPool?.play(idWin, 1f, 1f, 1, 0, 1f)
    }

    // -------------------------------------------------------------------------
    // Música de Fundo
    // -------------------------------------------------------------------------

    fun startBackgroundMusic() {
        backgroundPlayer?.start()   // Instantâneo — arquivo já está pré-carregado
    }

    fun stopBackgroundMusic() {
        // pause() + seekTo(0) mantém o player preparado para a próxima partida
        // (stop() desmontaria o player e causaria delay novamente)
        backgroundPlayer?.pause()
        backgroundPlayer?.seekTo(0)
    }

    fun pauseBackgroundMusic() {
        backgroundPlayer?.pause()
    }

    fun resumeBackgroundMusic() {
        backgroundPlayer?.start()
    }

    // -------------------------------------------------------------------------
    // Liberação de recursos
    // -------------------------------------------------------------------------

    fun release() {
        backgroundPlayer?.stop()
        backgroundPlayer?.release()
        backgroundPlayer = null
        soundPool?.release()
        soundPool = null
        initialized = false
    }
}
