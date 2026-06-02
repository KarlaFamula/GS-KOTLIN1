package com.github.karlaeisaque.gs.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.karlaeisaque.gs.ui.theme.*
import com.github.karlaeisaque.gs.viewmodel.SpaceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SpaceViewModel
) {
    val activeCount by viewModel.getActiveCount().let { count ->
        val totalCount = viewModel.getTotalCount()
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(Pair(count, totalCount)) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🛸", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Space Monitor",
                            color = SpaceAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
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
                .verticalScroll(rememberScrollState())
                .background(SpaceBlack),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Hero Section ─────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(SpaceDeepBlue, SpaceBlue, SpaceBlack)
                        )
                    )
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🌌", fontSize = 72.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Space Monitor",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Monitoramento de Satélites\ne Eventos Espaciais",
                        fontSize = 16.sp,
                        color = SpaceGray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Global Solution 2026.1 — FIAP",
                        fontSize = 12.sp,
                        color = SpaceAccent,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Cards de Estatísticas ─────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "✅",
                    label = "Ativos",
                    value = "${viewModel.getActiveCount()}",
                    accentColor = SpaceGreen
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🛰️",
                    label = "Total",
                    value = "${viewModel.getTotalCount()}",
                    accentColor = SpaceAccent
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "📅",
                    label = "Eventos",
                    value = "6",
                    accentColor = SpaceGold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Seção "Sobre o App" ───────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SpaceCard)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "🚀  Sobre o App",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = SpaceAccent
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "O Space Monitor conecta a exploração espacial aos desafios reais da humanidade. " +
                                "Acompanhe em tempo real satélites que monitoram o clima, " +
                                "a Amazônia, a oceanografia e a conectividade global.",
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Desenvolvido para a Global Solution 2026 — Indústria Espacial",
                        fontSize = 12.sp,
                        color = SpaceGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Botões de Navegação ───────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Explorar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = SpaceGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                NavButton(
                    icon = Icons.Default.Satellite,
                    label = "🛰️  Satélites em Órbita",
                    subtitle = "Lista e filtros de satélites monitorados",
                    accentColor = SpaceAccent,
                    onClick = { navController.navigate("satellites") }
                )

                NavButton(
                    icon = Icons.Default.Event,
                    label = "📅  Eventos Espaciais",
                    subtitle = "Próximos lançamentos e passagens",
                    accentColor = SpaceGold,
                    onClick = { navController.navigate("events") }
                )

                NavButton(
                    icon = Icons.Default.Star,
                    label = "🌌  Sobre o Projeto",
                    subtitle = "Equipe, contexto e tecnologias usadas",
                    accentColor = SpaceGreen,
                    onClick = { navController.navigate("about") }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    emoji: String,
    label: String,
    value: String,
    accentColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SpaceCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = accentColor
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = SpaceGray
            )
        }
    }
}

@Composable
fun NavButton(
    icon: ImageVector,
    label: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SpaceCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = label,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = SpaceGray
                )
            }
        }
    }
}
