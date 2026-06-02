package com.github.karlaeisaque.gs.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.karlaeisaque.gs.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🌌  Sobre o Projeto",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = SpaceGreen
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // ── Banner do Projeto ─────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(SpaceBlue, SpaceDeepBlue)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🛸", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Space Monitor",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Global Solution 2026 — FIAP",
                        fontSize = 13.sp,
                        color = SpaceAccent
                    )
                    Text(
                        text = "Tema: Indústria Espacial",
                        fontSize = 12.sp,
                        color = SpaceGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Contexto do Projeto ───────────────────────────────────
            AboutCard(
                icon = Icons.Default.Info,
                title = "Contexto",
                iconColor = SpaceAccent
            ) {
                AboutText(
                    "O Space Monitor foi desenvolvido para a Global Solution 2026 da FIAP, " +
                    "cujo tema é a Indústria Espacial. A solução aborda o monitoramento de " +
                    "satélites em órbita e eventos espaciais relevantes, conectando a " +
                    "exploração espacial com desafios reais como:"
                )
                Spacer(modifier = Modifier.height(8.dp))
                val topics = listOf(
                    "🌱 Monitoramento ambiental da Amazônia",
                    "⛅ Previsão climática e desastres naturais",
                    "🌊 Monitoramento oceânico e nível dos mares",
                    "📡 Conectividade em regiões remotas",
                    "🚀 Missões de exploração espacial"
                )
                topics.forEach { topic ->
                    Text(
                        text = topic,
                        fontSize = 13.sp,
                        color = SpaceGray,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ── Tecnologias ───────────────────────────────────────────
            AboutCard(
                icon = Icons.Default.Code,
                title = "Tecnologias Utilizadas",
                iconColor = SpaceGreen
            ) {
                val techs = listOf(
                    "Kotlin" to "Linguagem principal Android",
                    "Jetpack Compose" to "UI declarativa moderna",
                    "Navigation Compose" to "Navegação entre telas",
                    "ViewModel + StateFlow" to "Padrão MVVM",
                    "Material 3" to "Design System do Google",
                    "Retrofit" to "Pronto para integração com API"
                )
                techs.forEach { (tech, desc) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "✅ $tech",
                            fontSize = 13.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(0.45f)
                        )
                        Text(
                            text = desc,
                            fontSize = 12.sp,
                            color = SpaceGray,
                            modifier = Modifier.weight(0.55f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ── Equipe ────────────────────────────────────────────────
            AboutCard(
                icon = Icons.Default.Group,
                title = "Equipe",
                iconColor = SpaceGold
            ) {
                AboutText("Projeto desenvolvido para a disciplina Android Kotlin Developer com o Prof. Ewerton Luiz de Lima Carreira — 3SIR — FIAP 2026.1")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "👤 Isaque Santana Paixão - RM558864\n👤 Karla Louise Famula de Melo - RM555733\n",
                    fontSize = 13.sp,
                    color = SpaceGray,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── ODS da ONU ────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SpaceGreen.copy(alpha = 0.1f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🌐  ODS da ONU Abordados",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = SpaceGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val ods = listOf(
                        "ODS 9" to "Indústria, inovação e infraestrutura",
                        "ODS 11" to "Cidades e comunidades sustentáveis",
                        "ODS 13" to "Ação contra a mudança global do clima",
                        "ODS 2" to "Fome zero e agricultura sustentável"
                    )
                    ods.forEach { (num, desc) ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = num,
                                fontSize = 12.sp,
                                color = SpaceGreen,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(60.dp)
                            )
                            Text(text = desc, fontSize = 12.sp, color = SpaceGray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SpaceGreen)
            ) {
                Text("← Voltar ao Início", color = SpaceGreen)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AboutCard(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SpaceCard)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = iconColor)
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun AboutText(text: String) {
    Text(text = text, fontSize = 13.sp, color = SpaceGray, lineHeight = 20.sp)
}
