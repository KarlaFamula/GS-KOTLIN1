package com.github.karlaeisaque.gs.viewmodel

import androidx.lifecycle.ViewModel
import com.github.karlaeisaque.gs.model.Satellite
import com.github.karlaeisaque.gs.model.SatelliteRepository
import com.github.karlaeisaque.gs.model.SatelliteStatus
import com.github.karlaeisaque.gs.model.SpaceEvent
import com.github.karlaeisaque.gs.model.SpaceEventRepository
import com.github.karlaeisaque.gs.model.TelemetryData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Estados possíveis da tela de lista de satélites.
 * Padrão sealed class aprendido em aula (crypto-monitor-declarative).
 */
sealed class SatelliteUiState {
    object Loading : SatelliteUiState()
    data class Success(val satellites: List<Satellite>) : SatelliteUiState()
    data class Error(val message: String) : SatelliteUiState()
    object Initial : SatelliteUiState()
}

/**
 * ViewModel principal do Space Monitor.
 *
 * Segue o padrão MVVM estudado em aula:
 * - Estado exposto como StateFlow (somente leitura para a UI)
 * - Lógica de negócio encapsulada aqui, não nas telas
 * - Sobrevive a rotações de tela
 */
class SpaceViewModel : ViewModel() {

    // --- Estado da lista de satélites ---
    private val _satelliteState = MutableStateFlow<SatelliteUiState>(SatelliteUiState.Initial)
    val satelliteState: StateFlow<SatelliteUiState> = _satelliteState.asStateFlow()

    // --- Filtro de tipo selecionado ---
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    // --- Lista de filtros disponíveis ---
    val filterOptions: List<String> = SatelliteRepository.getSatelliteTypes()

    // --- Satélite selecionado para detalhes ---
    private val _selectedSatellite = MutableStateFlow<Satellite?>(null)
    val selectedSatellite: StateFlow<Satellite?> = _selectedSatellite.asStateFlow()

    // --- Telemetria do satélite selecionado ---
    private val _telemetry = MutableStateFlow<TelemetryData?>(null)
    val telemetry: StateFlow<TelemetryData?> = _telemetry.asStateFlow()

    // --- Eventos espaciais ---
    private val _events = MutableStateFlow<List<SpaceEvent>>(emptyList())
    val events: StateFlow<List<SpaceEvent>> = _events.asStateFlow()

    init {
        loadSatellites()
        loadEvents()
    }

    /**
     * Carrega a lista de satélites (dados mockados, simula chamada async).
     */
    fun loadSatellites() {
        _satelliteState.value = SatelliteUiState.Loading
        try {
            val satellites = SatelliteRepository.getByType(_selectedFilter.value)
            _satelliteState.value = SatelliteUiState.Success(satellites)
        } catch (e: Exception) {
            _satelliteState.value = SatelliteUiState.Error("Erro ao carregar satélites: ${e.message}")
        }
    }

    /**
     * Aplica um filtro por tipo de satélite.
     * Chamado quando o usuário seleciona um chip de filtro.
     */
    fun applyFilter(type: String) {
        _selectedFilter.value = type
        loadSatellites()
    }

    /**
     * Seleciona um satélite para exibir na tela de detalhes.
     * Também carrega os dados de telemetria simulados.
     */
    fun selectSatellite(satellite: Satellite) {
        _selectedSatellite.value = satellite
        _telemetry.value = SatelliteRepository.getTelemetry(satellite.id)
    }

    /**
     * Carrega os eventos espaciais mockados.
     */
    private fun loadEvents() {
        _events.value = SpaceEventRepository.getAllEvents()
    }

    /**
     * Conta os satélites ativos.
     */
    fun getActiveCount(): Int =
        SatelliteRepository.getAllSatellites().count { it.status == SatelliteStatus.ACTIVE }

    /**
     * Retorna o total de satélites no sistema.
     */
    fun getTotalCount(): Int = SatelliteRepository.getAllSatellites().size
}
