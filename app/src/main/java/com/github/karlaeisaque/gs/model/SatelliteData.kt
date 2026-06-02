package com.github.karlaeisaque.gs.model


data class Satellite(
    val id: Int,
    val name: String,
    val type: String,
    val altitude: Double,       // km
    val inclination: Double,    // graus
    val status: SatelliteStatus,
    val country: String,
    val launchYear: Int,
    val description: String,
    val iconEmoji: String
)

enum class SatelliteStatus(val label: String, val color: Long) {
    ACTIVE("Ativo", 0xFF00FF9C),
    INACTIVE("Inativo", 0xFFFF6B35),
    MAINTENANCE("Manutenção", 0xFFFFD700)
}


data class SpaceEvent(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val type: EventType,
    val iconEmoji: String
)

enum class EventType(val label: String) {
    LAUNCH("Lançamento"),
    FLYOVER("Sobrevoo"),
    MISSION("Missão"),
    DISCOVERY("Descoberta")
}


data class TelemetryData(
    val satelliteId: Int,
    val timestamp: String,
    val temperature: Double,    // °C
    val batteryLevel: Double,   // %
    val signalStrength: Double, // dBm
    val orbitProgress: Double   // %
)


object SatelliteRepository {

    fun getAllSatellites(): List<Satellite> = listOf(
        Satellite(
            id = 1,
            name = "ISS - Estação Espacial Internacional",
            type = "Estação Espacial",
            altitude = 408.0,
            inclination = 51.6,
            status = SatelliteStatus.ACTIVE,
            country = "Internacional",
            launchYear = 1998,
            description = "Laboratório orbital habitado que orbita a Terra a cada ~90 minutos. Suporta pesquisa científica avançada em microgravidade.",
            iconEmoji = "🛸"
        ),
        Satellite(
            id = 2,
            name = "Hubble Space Telescope",
            type = "Telescópio",
            altitude = 547.0,
            inclination = 28.5,
            status = SatelliteStatus.ACTIVE,
            country = "EUA",
            launchYear = 1990,
            description = "Telescópio espacial que captura imagens do universo com resolução sem precedentes, revolucionando a astronomia moderna.",
            iconEmoji = "🔭"
        ),
        Satellite(
            id = 3,
            name = "Starlink-1234",
            type = "Comunicações",
            altitude = 550.0,
            inclination = 53.0,
            status = SatelliteStatus.ACTIVE,
            country = "EUA",
            launchYear = 2022,
            description = "Parte da constelação Starlink da SpaceX, provendo internet de alta velocidade para regiões remotas do Brasil.",
            iconEmoji = "📡"
        ),
        Satellite(
            id = 4,
            name = "CBERS-4A (AMAZONIA-1)",
            type = "Observação da Terra",
            altitude = 614.0,
            inclination = 97.9,
            status = SatelliteStatus.ACTIVE,
            country = "Brasil",
            launchYear = 2021,
            description = "Satélite brasileiro de observação da Terra. Monitora o desmatamento na Amazônia e apoio ao agronegócio.",
            iconEmoji = "🌎"
        ),
        Satellite(
            id = 5,
            name = "GPS IIF-12",
            type = "Navegação",
            altitude = 20200.0,
            inclination = 55.0,
            status = SatelliteStatus.ACTIVE,
            country = "EUA",
            launchYear = 2016,
            description = "Satélite de posicionamento global que integra a constelação GPS, fornecendo dados de localização para todo o planeta.",
            iconEmoji = "🗺️"
        ),
        Satellite(
            id = 6,
            name = "James Webb Space Telescope",
            type = "Telescópio",
            altitude = 1500000.0,
            inclination = 0.0,
            status = SatelliteStatus.ACTIVE,
            country = "Internacional",
            launchYear = 2021,
            description = "O mais poderoso telescópio já construído. Opera no infravermelho e já capturou imagens de galáxias há 13 bilhões de anos-luz.",
            iconEmoji = "✨"
        ),
        Satellite(
            id = 7,
            name = "GOES-18",
            type = "Meteorológico",
            altitude = 35786.0,
            inclination = 0.0,
            status = SatelliteStatus.ACTIVE,
            country = "EUA",
            launchYear = 2022,
            description = "Satélite meteorológico geoestacionário que monitora padrões climáticos e auxilia na prevenção de desastres naturais.",
            iconEmoji = "⛅"
        ),
        Satellite(
            id = 8,
            name = "Landsat 9",
            type = "Observação da Terra",
            altitude = 705.0,
            inclination = 98.2,
            status = SatelliteStatus.ACTIVE,
            country = "EUA",
            launchYear = 2021,
            description = "Monitora mudanças na superfície terrestre há décadas. Essencial para análise de uso do solo e saúde ambiental.",
            iconEmoji = "🌿"
        ),
        Satellite(
            id = 9,
            name = "Sentinel-6 Michael Freilich",
            type = "Oceanografia",
            altitude = 1336.0,
            inclination = 66.0,
            status = SatelliteStatus.ACTIVE,
            country = "Europa",
            launchYear = 2020,
            description = "Monitora o nível dos oceanos com altimetria de alta precisão, dados críticos para estudos de mudanças climáticas.",
            iconEmoji = "🌊"
        ),
        Satellite(
            id = 10,
            name = "DMSP F-19",
            type = "Meteorológico",
            altitude = 830.0,
            inclination = 98.7,
            status = SatelliteStatus.MAINTENANCE,
            country = "EUA",
            launchYear = 2014,
            description = "Satélite meteorológico de defesa que monitora condições climáticas globais. Atualmente em modo de manutenção.",
            iconEmoji = "🔧"
        )
    )

    fun getSatelliteById(id: Int): Satellite? =
        getAllSatellites().find { it.id == id }

    fun getByType(type: String): List<Satellite> =
        if (type == "Todos") getAllSatellites()
        else getAllSatellites().filter { it.type == type }

    fun getSatelliteTypes(): List<String> =
        listOf("Todos") + getAllSatellites().map { it.type }.distinct().sorted()

    fun getTelemetry(satelliteId: Int): TelemetryData =
        TelemetryData(
            satelliteId = satelliteId,
            timestamp = "2026-05-26 19:45:00 UTC",
            temperature = (-20..80).random().toDouble(),
            batteryLevel = (60..100).random().toDouble(),
            signalStrength = (-80..-30).random().toDouble(),
            orbitProgress = (0..100).random().toDouble()
        )
}

/**
 * Dados mockados dos eventos espaciais.
 */
object SpaceEventRepository {

    fun getAllEvents(): List<SpaceEvent> = listOf(
        SpaceEvent(
            id = 1,
            title = "Lançamento Artemis III",
            description = "Primeira missão tripulada da NASA a pousar na Lua desde 1972. Astronautas explorarão o polo sul lunar por 6 dias.",
            date = "2026-09-15",
            type = EventType.LAUNCH,
            iconEmoji = "🚀"
        ),
        SpaceEvent(
            id = 2,
            title = "Passagem da ISS — São Paulo",
            description = "A Estação Espacial Internacional será visível a olho nu no céu de São Paulo por aproximadamente 6 minutos.",
            date = "2026-05-28",
            type = EventType.FLYOVER,
            iconEmoji = "👁️"
        ),
        SpaceEvent(
            id = 3,
            title = "Starship - Missão Mars Cargo",
            description = "SpaceX lança primeira missão de carga para Marte, testando tecnologias para futura colonização humana do planeta.",
            date = "2026-11-03",
            type = EventType.MISSION,
            iconEmoji = "🔴"
        ),
        SpaceEvent(
            id = 4,
            title = "Webb detecta exoplaneta habitável",
            description = "James Webb Space Telescope confirmou sinais de vapor d'água e temperatura amena em exoplaneta a 40 anos-luz da Terra.",
            date = "2026-05-10",
            type = EventType.DISCOVERY,
            iconEmoji = "🌍"
        ),
        SpaceEvent(
            id = 5,
            title = "AMAZONIA-2 — Lançamento Brasileiro",
            description = "Brasil lança seu segundo satélite de observação da Terra, expandindo o monitoramento ambiental da Amazônia.",
            date = "2026-08-20",
            type = EventType.LAUNCH,
            iconEmoji = "🇧🇷"
        ),
        SpaceEvent(
            id = 6,
            title = "Eclipse Solar Total — Sul do Brasil",
            description = "Eclipse solar total visível no Rio Grande do Sul e Santa Catarina. Fenômeno raro com totalidade de 4 minutos.",
            date = "2026-07-15",
            type = EventType.FLYOVER,
            iconEmoji = "🌑"
        )
    )
}
