package com.github.karlaeisaque.gs.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.karlaeisaque.gs.ui.theme.*
import com.github.karlaeisaque.gs.viewmodel.SpaceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SpaceViewModel,
    satelliteId: Int
) {
    val satellite by viewModel.selectedSatellite.collectAsState()
    val telemetry by viewModel.telemetry.collectAsState()
    var refreshCount by remember { mutableStateOf(0) }

    // Garante que carregamos o satélite correto ao navegar diretamente
    LaunchedEffect(satelliteId) {
        val sat = com.github.karlaeisaque.gs.model.SatelliteRepository.getSatelliteById(satelliteId)
        sat?.let { viewModel.selectSatellite(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = satellite?.name ?: "Detalhes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1
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

        if (satellite == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = SpaceAccent)
            }
            return@Scaffold
        }

        val sat = satellite!!

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SpaceBlack)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // ── Hero do Satélite ──────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SpaceBlue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = sat.iconEmoji, fontSize = 56.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = sat.name,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = sat.type,
                        fontSize = 13.sp,
                        color = SpaceAccent
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Status badge
                    Surface(
                        color = Color(sat.status.color).copy(alpha = 0.25f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "● ${sat.status.label}",
                            fontSize = 13.sp,
                            color = Color(sat.status.color),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Informações Orbitais ──────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SpaceCard)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionTitle(title = "📡  Dados Orbitais")
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OrbitalInfo(label = "Altitude", value = "${sat.altitude} km")
                        OrbitalInfo(label = "Inclinação", value = "${sat.inclination}°")
                        OrbitalInfo(label = "Lançamento", value = "${sat.launchYear}")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OrbitalInfo(label = "País / Agência", value = sat.country)
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = SpaceBlue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = sat.description,
                        fontSize = 13.sp,
                        color = SpaceGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Telemetria ─────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SpaceCard)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SectionTitle(title = "📊  Telemetria ao Vivo")
                        IconButton(
                            onClick = {
                                viewModel.selectSatellite(sat)
                                refreshCount++
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Atualizar",
                                tint = SpaceAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    if (refreshCount > 0) {
                        Text(
                            text = "✅ Atualizado (simulação #$refreshCount)",
                            fontSize = 11.sp,
                            color = SpaceGreen,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    telemetry?.let { t ->
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "🕐 ${t.timestamp}",
                            fontSize = 11.sp,
                            color = SpaceGray,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        TelemetryItem(
                            icon = Icons.Default.Thermostat,
                            label = "Temperatura",
                            value = "${t.temperature}°C",
                            color = if (t.temperature > 60) SpaceOrange else SpaceGreen
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TelemetryItem(
                            icon = Icons.Default.BatteryFull,
                            label = "Bateria",
                            value = "${t.batteryLevel}%",
                            color = if (t.batteryLevel < 70) SpaceOrange else SpaceGreen
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress bar de órbita
                        Text(
                            text = "Progresso da Órbita: ${t.orbitProgress.toInt()}%",
                            fontSize = 13.sp,
                            color = SpaceGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { (t.orbitProgress / 100).toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = SpaceAccent,
                            trackColor = SpaceBlue
                        )
                    } ?: run {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = SpaceAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Botão Voltar ──────────────────────────────────────────
            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SpaceAccent)
            ) {
                Text("← Voltar para a lista", color = SpaceAccent, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = SpaceAccent
    )
}

@Composable
fun OrbitalInfo(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 11.sp, color = SpaceGray)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TelemetryItem(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 13.sp, color = SpaceGray)
        }
        Text(text = value, fontSize = 14.sp, color = color, fontWeight = FontWeight.Bold)
    }
}
