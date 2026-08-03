package com.example.astros.quiz;

import java.util.ArrayList;
import java.util.List;

// =============================================================================
// GuessBank — Repositório de astros para o Mini-game
//
// Inclui planetas, luas, nebulosas, galáxias e outros corpos celestes.
// Apenas nasa_ids testados e verificados para garantir imagens corretas.
// =============================================================================
public class GuessBank {

    public static List<GuessItem> getCuratedItems() {
        List<GuessItem> items = new ArrayList<>();

        // === PLANETAS ===
        items.add(new GuessItem("Sol",      "PIA09320",  "É a estrela central do nosso sistema solar."));
        items.add(new GuessItem("Terra",    "PIA18033",  "O pálido ponto azul, nosso lar no cosmos."));
        items.add(new GuessItem("Júpiter",  "PIA22946",  "Gigante gasoso com uma enorme tempestade vermelha."));
        items.add(new GuessItem("Saturno",  "PIA01364",  "Famoso pelos seus anéis compostos de gelo e rocha."));
        items.add(new GuessItem("Marte",    "PIA01591",  "O Planeta Vermelho, com o maior vulcão do sistema solar."));
        items.add(new GuessItem("Vênus",    "PIA00248",  "O planeta mais quente, com efeito estufa descontrolado."));
        items.add(new GuessItem("Mercúrio", "PIA11364",  "O menor planeta e o mais rápido a orbitar o Sol."));
        items.add(new GuessItem("Urano",    "PIA18182",  "Gigante de gelo que orbita o Sol de lado."));
        items.add(new GuessItem("Netuno",   "PIA01492",  "O planeta mais distante, com ventos supersônicos."));

        // === LUAS ===
        items.add(new GuessItem("Lua",      "PIA00405",  "O único satélite natural da Terra, que afeta as marés."));
        items.add(new GuessItem("Titã",     "PIA14602",  "A maior lua de Saturno, com atmosfera densa de nitrogênio."));
        items.add(new GuessItem("Europa",   "PIA19048",  "Lua de Júpiter com um oceano líquido sob sua crosta de gelo."));
        items.add(new GuessItem("Io",       "PIA00375",  "A lua mais vulcanicamente ativa do sistema solar, de Júpiter."));
        items.add(new GuessItem("Ganimedes","PIA02278",  "A maior lua do sistema solar, maior até que Mercúrio."));

        // === PLANETAS ANÕES E CORPOS MENORES ===
        items.add(new GuessItem("Plutão",   "GSFC_20171208_Archive_e000682", "Planeta anão com uma planície em forma de coração."));
        items.add(new GuessItem("Ceres",    "PIA19168",  "O maior objeto do cinturão de asteroides."));

        // === NEBULOSAS ===
        items.add(new GuessItem("Nebulosa de Orion",   "PIA03519",  "Uma das nebulosas mais fotografadas, visível a olho nu."));

        return items;
    }
}
