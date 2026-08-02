package com.example.astros.quiz;

import java.util.Collections;
import java.util.List;

// =============================================================================
// GuessEngine — Controlador Lógico de Adivinhação
// =============================================================================
public class GuessEngine {
    private List<GuessItem> currentItems;
    private int currentIndex;
    private int score;
    private final int MAX_ROUNDS = 5; // Partidas rápidas de 5 acertos

    public GuessEngine() {
        startNewGame();
    }

    public void startNewGame() {
        currentItems = GuessBank.getCuratedItems();
        Collections.shuffle(currentItems);
        
        // Garante no máximo 5 rodadas
        if (currentItems.size() > MAX_ROUNDS) {
            currentItems = currentItems.subList(0, MAX_ROUNDS);
        }
        
        currentIndex = 0;
        score = 0;
    }

    public GuessItem getCurrentItem() {
        if (isFinished()) return null;
        return currentItems.get(currentIndex);
    }

    public boolean submitAnswer(String input) {
        GuessItem item = getCurrentItem();
        if (item != null && item.validate(input)) {
            score++;
            return true;
        }
        return false;
    }

    public boolean moveToNext() {
        currentIndex++;
        return !isFinished();
    }

    public boolean isFinished() {
        return currentIndex >= currentItems.size();
    }

    public int getScore() {
        return score;
    }

    public int getCurrentRound() {
        return currentIndex + 1;
    }

    public int getTotalRounds() {
        return currentItems.size();
    }
}
