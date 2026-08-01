package com.example.astros.quiz;

import java.util.List;

// =============================================================================
// Question — Modelo de Dados do Quiz (JAVA PURO)
//
// 🔧 PONTO PARA DEFESA AO VIVO:
// Esta classe é escrita inteiramente em Java para cumprir os requisitos da
// disciplina. Ela representa o modelo de domínio sem nenhuma dependência do Android.
// =============================================================================
public class Question {
    private String text;
    private List<String> options;
    private int correctOptionIndex; // 0 para A, 1 para B, 2 para C, 3 para D

    public Question(String text, List<String> options, int correctOptionIndex) {
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    // Métdo de negócio para validar se a resposta está certa
    public boolean isCorrect(int selectedIndex) {
        return selectedIndex == correctOptionIndex;
    }

    // Embaralha as opções de resposta e atualiza o índice da resposta correta
    public void shuffleOptions() {
        String correctAnswerText = options.get(correctOptionIndex);
        java.util.Collections.shuffle(options);
        correctOptionIndex = options.indexOf(correctAnswerText);
    }
}
