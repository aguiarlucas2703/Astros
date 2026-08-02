package com.example.astros.quiz;

import java.text.Normalizer;

// =============================================================================
// GuessItem — Dados do Mini-game "Adivinhe o Astro"
//
// Esta classe representa um item a ser adivinhado. 
// A validação trata strings para remover acentos e ignorar maiúsculas/minúsculas.
// =============================================================================
public class GuessItem {
    private String correctName;
    private String nasaImageId;
    private String hint;

    public GuessItem(String correctName, String nasaImageId, String hint) {
        this.correctName = correctName;
        this.nasaImageId = nasaImageId;
        this.hint = hint;
    }

    public String getCorrectName() {
        return correctName;
    }

    public String getNasaImageId() {
        return nasaImageId;
    }

    public String getHint() {
        return hint;
    }

    // Valida a resposta do usuário
    public boolean validate(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) return false;

        String normalizedInput = removeAccents(userInput.trim().toLowerCase());
        String normalizedCorrect = removeAccents(correctName.trim().toLowerCase());

        return normalizedInput.equals(normalizedCorrect);
    }

    private String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
