package com.github.karlaeisaque.gs.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.karlaeisaque.gs.model.Satellite
import com.github.karlaeisaque.gs.model.SatelliteStatus
import com.github.karlaeisaque.gs.ui.theme.*
import com.github.karlaeisaque.gs.viewmodel.SatelliteUiState
import com.github.karlaeisaque.gs.viewmodel.SpaceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SatellitesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SpaceViewModel
) {
    val uiState by viewModel.satelliteState.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🛰️  Satélites em Órbita",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = SpaceAccent
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpaceDeepBlue
                )
            )
        },
        containerColor = SpaceBlack
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SpaceBlack)
        ) {

            // ── Filtros por Tipo ──────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpaceDeepBlue)
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = SpaceGray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Filtrar por tipo:",
                        fontSize = 12.sp,
                        color = SpaceGray
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.filterOptions) { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { viewModel.applyFilter(filter) },
                            label = {
                                Text(
                                    text = filter,
                                    fontSize = 12.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SpaceAccent,
                                selectedLabelColor = SpaceBlack,
                                containerColor = SpaceCard,
                                labelColor = SpaceGray
                            )
                        )
                    }
                }
            }

            // ── Lista ou Estado ───────────────────────────────────────
            when (val state = uiState) {
                is SatelliteUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = SpaceAccent)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Carregando satélites...", color = SpaceGray)
                        }
                    }
                }

                is SatelliteUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("❌", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(state.message, color = SpaceOrange, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadSatellites() },
                                colors = ButtonDefaults.buttonColors(containerColor = SpaceAccent)
                            ) {
                                Text("Tentar novamente", color = SpaceBlack)
                            }
                        }
                    }
                }

                is SatelliteUiState.Success -> {
                    if (state.satellites.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum satélite encontrado para este filtro.", color = SpaceGray)
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Text(
                                    text = "${state.satellites.size} satélite(s) encontrado(s)",
                                    fontSize = 12.sp,
                                    color = SpaceGray,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            items(state.satellites, key = { it.id }) { satellite ->
                                SatelliteCard(
                                    satellite = satellite,
                                    onClick = {
                                        viewModel.selectSatellite(satellite)
                                        navController.navigate("detail/${satellite.id}")
                                    }
                                )
                            }
                        }
                    }
                }

                is SatelliteUiState.Initial -> {

                }
            }
        }
    }
}


@Composable
fun SatelliteCard(
    satellite: Satellite,
    onClick: () -> Unit
) {
    val statusColor = Color(satellite.status.color)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SpaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji / ícone do satélite
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(SpaceBlue, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = satellite.iconEmoji, fontSize = 26.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = satellite.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = satellite.type,
                    fontSize = 12.sp,
                    color = SpaceAccent
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Badge de status
                    Surface(
                        color = statusColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = satellite.status.label,
                            fontSize = 11.sp,
                            color = statusColor,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "${satellite.altitude} km",
                        fontSize = 11.sp,
                        color = SpaceGray
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = null,
                tint = statusColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
