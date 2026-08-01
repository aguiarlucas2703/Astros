package com.example.astros.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// =============================================================================
// QuestionBank — Repositório de Questões (JAVA PURO)
//
// 🔧 PONTO PARA DEFESA AO VIVO:
// Um banco vasto com 50 questões garante que o Quiz seja altamente rejogável
// e reduz a chance de perguntas repetidas.
// =============================================================================
public class QuestionBank {

    public static List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        // 1 a 10
        questions.add(new Question("Qual é o maior planeta do Sistema Solar?", Arrays.asList("Terra", "Saturno", "Júpiter", "Urano"), 2));
        questions.add(new Question("Qual é a estrela mais próxima da Terra?", Arrays.asList("Proxima Centauri", "Sol", "Sirius", "Betelgeuse"), 1));
        questions.add(new Question("Qual planeta é conhecido por seus anéis visíveis?", Arrays.asList("Marte", "Vênus", "Saturno", "Netuno"), 2));
        questions.add(new Question("Quem foi o primeiro humano a viajar para o espaço?", Arrays.asList("Neil Armstrong", "Yuri Gagarin", "Buzz Aldrin", "John Glenn"), 1));
        questions.add(new Question("Qual é a galáxia em que vivemos?", Arrays.asList("Andrômeda", "Via Láctea", "Sombrero", "Triângulo"), 1));
        questions.add(new Question("Quantas luas tem o planeta Marte?", Arrays.asList("Nenhuma", "Uma", "Duas", "Dezenas"), 2));
        questions.add(new Question("Qual planeta é conhecido como 'Planeta Vermelho'?", Arrays.asList("Mercúrio", "Vênus", "Marte", "Júpiter"), 2));
        questions.add(new Question("O que é o cinturão de Kuiper?", Arrays.asList("Uma região de asteroides", "Uma região de cometas e gelo", "Um planeta", "Uma constelação"), 1));
        questions.add(new Question("Qual é o planeta mais quente do Sistema Solar?", Arrays.asList("Mercúrio", "Vênus", "Marte", "Júpiter"), 1));
        questions.add(new Question("Qual lua de Júpiter tem o maior oceano subterrâneo?", Arrays.asList("Io", "Calisto", "Europa", "Ganimedes"), 2));

        // 11 a 20
        questions.add(new Question("O que é um Buraco Negro?", Arrays.asList("Um buraco no espaço", "Uma estrela anã", "Uma região com gravidade infinita", "Um planeta destruído"), 2));
        questions.add(new Question("Qual elemento é mais abundante no Sol?", Arrays.asList("Oxigênio", "Hélio", "Carbono", "Hidrogênio"), 3));
        questions.add(new Question("Como se chama a explosão final de uma estrela massiva?", Arrays.asList("Supernova", "Nebulosa", "Pulsar", "Quasar"), 0));
        questions.add(new Question("Qual destes NÃO é um planeta do Sistema Solar?", Arrays.asList("Urano", "Netuno", "Plutão", "Mercúrio"), 2));
        questions.add(new Question("Quem formulou as leis do movimento planetário?", Arrays.asList("Isaac Newton", "Johannes Kepler", "Galileu Galilei", "Copérnico"), 1));
        questions.add(new Question("Qual é o nome da nossa galáxia vizinha mais próxima?", Arrays.asList("Magalhães", "Triângulo", "Andrômeda", "Órion"), 2));
        questions.add(new Question("O que é um 'Ano-Luz'?", Arrays.asList("Tempo de vida de uma estrela", "Distância que a luz viaja em um ano", "Velocidade da luz", "Medida de luminosidade"), 1));
        questions.add(new Question("Qual é a lua mais vulcânica do Sistema Solar?", Arrays.asList("Titan", "Europa", "Io", "Ganimedes"), 2));
        questions.add(new Question("Qual sonda espacial foi a primeira a passar por Plutão?", Arrays.asList("Voyager 1", "Cassini", "New Horizons", "Galileo"), 2));
        questions.add(new Question("Qual é a estrela mais brilhante no céu noturno?", Arrays.asList("Polaris", "Sirius", "Betelgeuse", "Rigel"), 1));

        // 21 a 30
        questions.add(new Question("Em qual braço espiral da Via Láctea o Sol está localizado?", Arrays.asList("Braço de Perseus", "Braço de Sagitário", "Braço de Órion", "Braço de Centauro"), 2));
        questions.add(new Question("O que é o Telescópio James Webb?", Arrays.asList("Telescópio Ótico", "Telescópio de Raio-X", "Telescópio Infravermelho", "Telescópio de Rádio"), 2));
        questions.add(new Question("Qual é o maior vulcão conhecido no Sistema Solar?", Arrays.asList("Monte Everest", "Monte Olimpo (Marte)", "Mauna Kea", "Vulcão Krakatoa"), 1));
        questions.add(new Question("Qual planeta tem o eixo de rotação mais inclinado?", Arrays.asList("Terra", "Vênus", "Saturno", "Urano"), 3));
        questions.add(new Question("O que causa as marés na Terra?", Arrays.asList("O vento", "A rotação", "A gravidade do Sol", "A gravidade da Lua"), 3));
        questions.add(new Question("Quem descobriu que a Terra gira em torno do Sol?", Arrays.asList("Ptolomeu", "Nicolau Copérnico", "Tycho Brahe", "Aristóteles"), 1));
        questions.add(new Question("Qual é a cor das estrelas mais quentes?", Arrays.asList("Vermelhas", "Amarelas", "Azuis", "Brancas"), 2));
        questions.add(new Question("Quantos planetas rochosos existem no Sistema Solar?", Arrays.asList("Dois", "Quatro", "Seis", "Oito"), 1));
        questions.add(new Question("Qual a idade estimada do Universo?", Arrays.asList("4,5 Bilhões de anos", "13,8 Bilhões de anos", "10 Bilhões de anos", "50 Bilhões de anos"), 1));
        questions.add(new Question("Qual planeta é conhecido como o 'Gêmeo da Terra'?", Arrays.asList("Marte", "Mercúrio", "Vênus", "Saturno"), 2));

        // 31 a 40
        questions.add(new Question("O que é uma anã branca?", Arrays.asList("Estrela recém-nascida", "Resto de uma estrela morta", "Planeta de gelo", "Galáxia anã"), 1));
        questions.add(new Question("Qual é a maior lua de Saturno?", Arrays.asList("Europa", "Titã", "Encélado", "Reia"), 1));
        questions.add(new Question("Qual o nome do primeiro satélite artificial enviado ao espaço?", Arrays.asList("Apollo 11", "Voyager", "Sputnik 1", "Hubble"), 2));
        questions.add(new Question("Onde está localizado o Cinturão de Asteroides principal?", Arrays.asList("Entre Terra e Marte", "Além de Plutão", "Entre Marte e Júpiter", "Próximo ao Sol"), 2));
        questions.add(new Question("Qual gás dá a coloração azulada para Urano e Netuno?", Arrays.asList("Oxigênio", "Metano", "Hélio", "Nitrogênio"), 1));
        questions.add(new Question("O que mede a Escala de Kardashev?", Arrays.asList("Tamanho de buracos negros", "Avanço tecnológico de uma civilização", "Brilho de estrelas", "Distância de galáxias"), 1));
        questions.add(new Question("Quem inventou o telescópio reflexivo?", Arrays.asList("Galileu", "Isaac Newton", "Kepler", "Einstein"), 1));
        questions.add(new Question("Qual lua do Sistema Solar tem uma atmosfera densa?", Arrays.asList("Lua (Terra)", "Io", "Titã", "Ganimedes"), 2));
        questions.add(new Question("Qual é a constelação que abriga a Estrela Polar?", Arrays.asList("Ursa Maior", "Órion", "Cruzeiro do Sul", "Ursa Menor"), 3));
        questions.add(new Question("Qual planeta do Sistema Solar tem o dia mais longo?", Arrays.asList("Terra", "Vênus", "Júpiter", "Mercúrio"), 1));

        // 41 a 50
        questions.add(new Question("Qual força mantém os planetas em órbita?", Arrays.asList("Eletromagnetismo", "Força Nuclear Forte", "Gravidade", "Força Centrífuga"), 2));
        questions.add(new Question("O que é uma Nebulosa?", Arrays.asList("Um aglomerado de planetas", "Uma nuvem de poeira e gás", "Uma galáxia distante", "Um buraco negro inativo"), 1));
        questions.add(new Question("Em qual país fica o maior radiotelescópio do mundo (FAST)?", Arrays.asList("EUA", "Rússia", "China", "Brasil"), 2));
        questions.add(new Question("Qual é a estrela principal da constelação do Cruzeiro do Sul?", Arrays.asList("Acrux", "Sirius", "Betelgeuse", "Antares"), 0));
        questions.add(new Question("O que são manchas solares?", Arrays.asList("Oceanos no Sol", "Regiões mais frias da superfície", "Buracos na coroa solar", "Erupções de lava"), 1));
        questions.add(new Question("Quantos anéis principais Saturno possui?", Arrays.asList("Quatro", "Sete", "Dez", "Doze"), 1));
        questions.add(new Question("Qual foi a missão que levou o homem à Lua?", Arrays.asList("Apollo 13", "Gemini 4", "Apollo 11", "Artemis I"), 2));
        questions.add(new Question("Qual é a única lua do Sistema Solar que orbita retrogradamente?", Arrays.asList("Tritão (Netuno)", "Titã (Saturno)", "Europa (Júpiter)", "Fobos (Marte)"), 0));
        questions.add(new Question("O que protege a Terra dos ventos solares?", Arrays.asList("Camada de Ozônio", "Campo Magnético", "A Lua", "A atmosfera densa"), 1));
        questions.add(new Question("Qual cientista propôs a Teoria da Relatividade?", Arrays.asList("Isaac Newton", "Stephen Hawking", "Albert Einstein", "Carl Sagan"), 2));

        return questions;
    }
}
