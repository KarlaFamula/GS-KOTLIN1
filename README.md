 # 🛸 Space Monitor — Global Solution 2026 FIAP

Aplicativo Android desenvolvido em **Kotlin + Jetpack Compose** para a Global Solution 2026 da FIAP, tema **Indústria Espacial**.

---

## 🎯 Objetivo do Projeto

O **Space Monitor** conecta a exploração espacial aos desafios reais da humanidade, permitindo o monitoramento de satélites em órbita e eventos espaciais. Aborda diretamente temas como:

- 🌱 Monitoramento ambiental da Amazônia (AMAZONIA-1)
- ⛅ Previsão climática e prevenção de desastres (GOES-18)
- 🌊 Monitoramento oceânico (Sentinel-6)
- 📡 Conectividade em regiões remotas (Starlink)
- 🔭 Descobertas científicas (James Webb)

---

## 📱 Telas e Fluxo do Aplicativo

```
HomeScreen (Tela Inicial)
    ├── SatellitesScreen (Lista de Satélites)
    │       └── DetailScreen (Detalhes + Telemetria)
    ├── EventsScreen (Eventos Espaciais)
    └── AboutScreen (Sobre o Projeto)
```

### Tela 1 — Home
- Nome e logo do app
- Descrição do objetivo
- Cards de estatísticas (ativos, total, eventos)
- Botões de navegação para as demais telas
- Identidade visual espacial (gradiente escuro + ciano)

### Tela 2 — Satélites
- Lista com **LazyColumn** de 10 satélites reais
- **Filtros por tipo** com FilterChips (Todos, Telescópio, Meteorológico, etc.)
- Cards com nome, tipo, altitude e status (Ativo/Inativo/Manutenção)
- Navega para tela de detalhes ao clicar

### Tela 3 — Detalhes do Satélite
- Dados completos do satélite (altitude, inclinação, país, ano de lançamento)
- **Telemetria simulada**: temperatura, bateria, sinal, progresso orbital
- Botão de **Atualizar Telemetria** (interação funcional)
- LinearProgressIndicator de progresso orbital

### Tela 4 — Eventos Espaciais
- Lista com **LazyColumn** de eventos mockados
- Cards com tipo de evento, data e descrição
- Tipos: Lançamento, Sobrevoo, Missão, Descoberta

### Tela 5 — Sobre o Projeto
- Contexto da GS 2026
- Tecnologias utilizadas
- ODS da ONU abordados
- Informações da equipe


## 🏗️ Arquitetura

```
app/
├── model/
│   └── SatelliteData.kt     # Data classes + Repositórios mockados
├── viewmodel/
│   └── SpaceViewModel.kt    # MVVM ViewModel com StateFlow
├── screens/
│   ├── HomeScreen.kt        # Tela inicial
│   ├── SatellitesScreen.kt  # Lista + Filtros
│   ├── DetailScreen.kt      # Detalhes + Telemetria
│   ├── EventsScreen.kt      # Eventos Espaciais
│   └── AboutScreen.kt       # Sobre o Projeto
├── ui/theme/
│   ├── Color.kt             # Paleta espacial customizada
│   ├── Theme.kt             # Dark theme espacial
│   └── Type.kt              # Tipografia
└── MainActivity.kt          # NavHost com 5 rotas
```

### Padrão MVVM
- **Model**: `SatelliteData.kt` com data classes e repositórios
- **ViewModel**: `SpaceViewModel.kt` com `StateFlow` e `sealed class` de estados
- **View**: Composables que observam o estado via `collectAsState()`

### Padrão de Navegação (aula)
- `NavHost` + `NavController` no `MainActivity`
- Passagem de argumento `satelliteId` na rota `detail/{satelliteId}`
- `navigateUp()` nos botões de voltar

---

## 🛠️ Como abrir no Android Studio

1. Extraia o `.zip` em uma pasta
2. Abra o **Android Studio Meerkat Feature Drop 2024.3.2**
3. `File → Open` → selecione a pasta `SpaceMonitor`
4. Aguarde o Gradle sync (pode demorar na primeira vez)
5. Conecte um device ou emulador com API 24+
6. Clique em ▶️ **Run**

### Requisitos
- Android Studio Meerkat Feature Drop 2024.3.2+
- JDK 11 (configurado automaticamente)
- Android SDK API 24+ (minSdk)
- Conexão com internet (para download de dependências no primeiro build)

---

## 📦 Dependências Principais

```toml
# Navigation Compose (aula de navegação)
androidx-navigation-compose = "2.8.5"

# ViewModel + StateFlow (padrão crypto-monitor-declarative)
androidx-lifecycle-viewmodel-compose = "2.8.7"

# Material 3 + Icons Extended
androidx-compose-material3
androidx-compose-material-icons-extended

# Retrofit (pronto para integração futura com API real)
squareup-retrofit = "2.9.0"
squareup-retrofit-gson = "2.9.0"
```

---

## 🔭 Satélites Monitorados

| Satélite | Tipo | Altitude | Status |
|---|---|---|---|
| ISS | Estação Espacial | 408 km | Ativo |
| Hubble | Telescópio | 547 km | Ativo |
| Starlink-1234 | Comunicações | 550 km | Ativo |
| AMAZONIA-1 | Observação da Terra | 614 km | Ativo |
| GPS IIF-12 | Navegação | 20.200 km | Ativo |
| James Webb | Telescópio | 1.500.000 km | Ativo |
| GOES-18 | Meteorológico | 35.786 km | Ativo |
| Landsat 9 | Observação da Terra | 705 km | Ativo |
| Sentinel-6 | Oceanografia | 1.336 km | Ativo |
| DMSP F-19 | Meteorológico | 830 km | Manutenção |

---

## 👥 Equipe

- **Isaque Santana Paixão** — RM 558864
- **Karla Louise Famula de Melo** — RM 555733

**Disciplina**: Android Kotlin Developer  
**Professor**: Ewerton Luiz de Lima Carreira  
**Turma**: 3SIR — FIAP 2026.1
