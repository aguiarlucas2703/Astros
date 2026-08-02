package com.example.astros.quiz;

import java.util.ArrayList;
import java.util.List;

// =============================================================================
// GuessBank — Repositório restrito para o Mini-game
//
// Apenas astros com nasa_ids perfeitamente testados para evitar inconsistências
// e reclamações de imagens genéricas.
// =============================================================================
public class GuessBank {

    public static List<GuessItem> getCuratedItems() {
        List<GuessItem> items = new ArrayList<>();
        
        items.add(new GuessItem("Sol", "PIA09320", "É a estrela central do nosso sistema."));
        items.add(new GuessItem("Terra", "PIA18033", "O pálido ponto azul onde moramos."));
        items.add(new GuessItem("Júpiter", "PIA22946", "Gigante gasoso com uma enorme mancha vermelha."));
        items.add(new GuessItem("Saturno", "PIA01364", "Famoso por seus anéis incrivelmente brilhantes."));
        items.add(new GuessItem("Lua", "PIA00405", "Nosso satélite natural que afeta as marés."));
        items.add(new GuessItem("Marte", "PIA01591", "Conhecido como o Planeta Vermelho."));
        items.add(new GuessItem("Plutão", "GSFC_20171208_Archive_e000682", "O planeta anão com uma planície em forma de coração."));
        items.add(new GuessItem("Vênus", "PIA00248", "Planeta extremamente quente com efeito estufa descontrolado."));
        items.add(new GuessItem("Titã", "PIA14602", "Maior lua de Saturno, famosa por sua atmosfera densa."));
        items.add(new GuessItem("Mercúrio", "PIA11364", "O menor planeta e o mais rápido a orbitar o Sol."));
        
        return items;
    }
}
