package com.example.astros.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// =============================================================================
// QuizEngine — O Cérebro do Jogo (JAVA PURO)
//
// 🔧 PONTO PARA DEFESA AO VIVO:
// Esta classe é responsável por aplicar as regras de negócio:
// - Sortear 10 perguntas do banco de 50 (sem repetição na mesma partida).
// - Validar as respostas.
// - Contabilizar os pontos.
// =============================================================================
public class QuizEngine {
    private List<Question> currentSessionQuestions;
    private int currentQuestionIndex;
    private int score;
    private final int QUESTIONS_PER_GAME = 10;

    public QuizEngine() {
        currentSessionQuestions = new ArrayList<>();
        startNewGame();
    }

    public void startNewGame() {
        // 1. Pega todas as 50 questões
        List<Question> allQuestions = QuestionBank.getAllQuestions();
        
        // 2. Embaralha a lista inteira (Isso garante que nunca venham na mesma ordem)
        Collections.shuffle(allQuestions);
        
        // 3. Pega as primeiras 10 questões da lista embaralhada
        currentSessionQuestions.clear();
        for (int i = 0; i < QUESTIONS_PER_GAME && i < allQuestions.size(); i++) {
            Question q = allQuestions.get(i);
            q.shuffleOptions(); // Embaralha as alternativas (A, B, C, D) dessa pergunta!
            currentSessionQuestions.add(q);
        }

        // 4. Zera os contadores
        currentQuestionIndex = 0;
        score = 0;
    }

    public Question getCurrentQuestion() {
        if (isFinished()) {
            return null;
        }
        return currentSessionQuestions.get(currentQuestionIndex);
    }

    // Retorna true se a resposta estiver certa e já computa o ponto
    public boolean submitAnswer(int selectedIndex) {
        Question q = getCurrentQuestion();
        if (q != null && q.isCorrect(selectedIndex)) {
            score++;
            return true;
        }
        return false;
    }

    // Avança para a próxima pergunta. Retorna true se ainda houver jogo
    public boolean moveToNextQuestion() {
        currentQuestionIndex++;
        return !isFinished();
    }

    public boolean isFinished() {
        return currentQuestionIndex >= currentSessionQuestions.size();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return currentSessionQuestions.size();
    }
    
    public int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }
}
