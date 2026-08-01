package com.example.astros.data

import java.time.LocalDate

// =============================================================================
// EventsRepository — Fonte de Dados dos Eventos
// =============================================================================
class EventsRepository {

    // Lista fixa de eventos (mockada) para o ano de 2026/2027
    // O aplicativo calculará se o evento já passou baseado na data atual do celular.
    private val _events = listOf(
        // === Eventos que já passaram (considerando hoje como Agosto 2026+) ===
        AstronomyEvent(
            id = "eclipse_lunar_26",
            title = "Eclipse Lunar Penumbral",
            description = "A Lua passa pela parte externa (penumbra) da sombra da Terra, causando um escurecimento sutil.",
            date = LocalDate.of(2026, 3, 3), // Passado (Março)
            type = EventType.ECLIPSE
        ),
        AstronomyEvent(
            id = "liridas_26",
            title = "Chuva de Meteoros Lirídeas",
            description = "Uma chuva de meteoros anual conhecida por produzir meteoros brilhantes com rastros luminosos persistentes.",
            date = LocalDate.of(2026, 4, 22), // Passado (Abril)
            type = EventType.METEOR_SHOWER
        ),
        AstronomyEvent(
            id = "eta_aquaridas_26",
            title = "Chuva de Meteoros Eta Aquáridas",
            description = "Criada por detritos do famoso Cometa Halley. É melhor vista no hemisfério sul.",
            date = LocalDate.of(2026, 5, 6), // Passado (Maio)
            type = EventType.METEOR_SHOWER
        ),

        // === Eventos que vão acontecer (a partir de Agosto 2026) ===
        AstronomyEvent(
            id = "eclipse_solar_26",
            title = "Eclipse Solar Total",
            description = "A Lua cobrirá totalmente o Sol. Será visível na Europa (Espanha, Islândia e Rússia).",
            date = LocalDate.of(2026, 8, 12), // Futuro (Agosto)
            type = EventType.ECLIPSE
        ),
        AstronomyEvent(
            id = "perseidas_26",
            title = "Chuva de Meteoros Perseidas",
            description = "Uma das melhores chuvas do ano, produzindo até 60 meteoros por hora em seu pico.",
            date = LocalDate.of(2026, 8, 13), // Futuro (Agosto)
            type = EventType.METEOR_SHOWER
        ),
        AstronomyEvent(
            id = "superlua_colheita",
            title = "Superlua da Colheita",
            description = "A Lua cheia estará mais próxima da Terra, parecendo ligeiramente maior e mais brilhante que o normal.",
            date = LocalDate.of(2026, 9, 26), // Futuro (Setembro)
            type = EventType.MOON
        ),
        AstronomyEvent(
            id = "conjucao_venus_jupiter",
            title = "Conjunção: Vênus e Júpiter",
            description = "Os dois planetas mais brilhantes do céu noturno aparecerão incrivelmente próximos um do outro logo após o pôr do sol.",
            date = LocalDate.of(2026, 11, 2), // Futuro (Novembro)
            type = EventType.PLANETARY
        ),
        AstronomyEvent(
            id = "geminidas_26",
            title = "Chuva de Meteoros Geminídeas",
            description = "Considerada a rainha das chuvas de meteoros, produzindo até 120 meteoros multicoloridos por hora.",
            date = LocalDate.of(2026, 12, 14), // Futuro (Dezembro)
            type = EventType.METEOR_SHOWER
        ),

        // === Eventos distantes (2027) ===
        AstronomyEvent(
            id = "eclipse_solar_27",
            title = "Eclipse Solar Total (O Grande Eclipse)",
            description = "Um dos eclipses solares mais longos do século, cruzando todo o norte da África e península Arábica.",
            date = LocalDate.of(2027, 8, 2), // Futuro Distante
            type = EventType.ECLIPSE
        )
    )

    fun getAllEvents(): List<AstronomyEvent> {
        return _events
    }
}
