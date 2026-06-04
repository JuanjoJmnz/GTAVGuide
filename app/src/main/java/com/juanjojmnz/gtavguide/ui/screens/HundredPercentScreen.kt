package com.juanjojmnz.gtavguide.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.juanjojmnz.gtavguide.ui.theme.*

// ===== MODELOS =====
data class HundredPercentItem(
    val text: String,
    val isSubItem: Boolean = false
)

data class HundredPercentCategory(
    val title: String,
    val percentage: Int,
    val accentColor: androidx.compose.ui.graphics.Color = GTAGreen,
    val items: List<HundredPercentItem>
)

// ===== DATOS =====
val hundredPercentData = listOf(
    HundredPercentCategory(
        title = "Misiones de la Historia",
        percentage = 50,
        accentColor = MichaelBlue,
        items = listOf(
            HundredPercentItem("Completa todas las misiones del modo historia.")
        )
    ),
    HundredPercentCategory(
        title = "Aficiones y Pasatiempos",
        percentage = 10,
        accentColor = FranklinGreen,
        items = listOf(
            HundredPercentItem("Completa las 4 carreras marítimas (3er lugar o mejor)."),
            HundredPercentItem("Completa las 6 carreras todoterreno (3er lugar o mejor)."),
            HundredPercentItem("Completa las 5 carreras urbanas (3er lugar o mejor)."),
            HundredPercentItem("Desafíos de la galería de tiro:"),
            HundredPercentItem("3 desafíos de armas de mano (bronce o mejor).", isSubItem = true),
            HundredPercentItem("3 desafíos de subfusiles (bronce o mejor).", isSubItem = true),
            HundredPercentItem("3 desafíos de fusiles de asalto (bronce o mejor).", isSubItem = true),
            HundredPercentItem("3 desafíos de escopetas (bronce o mejor).", isSubItem = true),
            HundredPercentItem("3 desafíos de ametralladoras (bronce o mejor).", isSubItem = true),
            HundredPercentItem("3 desafíos de armamento pesado (bronce o mejor).", isSubItem = true),
            HundredPercentItem("Completa las 12 lecciones de la escuela de vuelo (bronce o mejor)."),
            HundredPercentItem("Gana una partida de dardos."),
            HundredPercentItem("Gana un juego de tenis."),
            HundredPercentItem("Juega 9 hoyos de golf y acaba en par o bajo par."),
            HundredPercentItem("Completa los 3 triatlones (3er lugar o mejor)."),
            HundredPercentItem("Completa los 13 saltos en paracaídas."),
            HundredPercentItem("Disfruta de un baile privado en el club de striptease.")
        )
    ),
    HundredPercentCategory(
        title = "Extraños y Locos",
        percentage = 10,
        accentColor = TrevorOrange,
        items = listOf(
            HundredPercentItem("Completa las misiones de Extraños y Locos de Franklin."),
            HundredPercentItem("Barry: Movimiento verde (4 misiones).", isSubItem = true),
            HundredPercentItem("Beverly Felton: Paparazzi (6 misiones).", isSubItem = true),
            HundredPercentItem("Dom Beasley: Gestión de riesgos (4 misiones).", isSubItem = true),
            HundredPercentItem("Hao: Cambios.", isSubItem = true),
            HundredPercentItem("MaryAnn Quinn: Ejercitando demonios.", isSubItem = true),
            HundredPercentItem("Omega: Pasado de rosca + La última frontera (50 piezas de nave).", isSubItem = true),
            HundredPercentItem("Peter Dreyfuss: Una joven estrella (50 fragmentos de carta).", isSubItem = true),
            HundredPercentItem("Tonya Wiggins: Pedir favores (5 misiones).", isSubItem = true)
        )
    ),
    HundredPercentCategory(
        title = "Eventos Aleatorios",
        percentage = 15,
        accentColor = GTAYellow,
        items = listOf(
            HundredPercentItem("Completa al menos 14 de los 57 eventos aleatorios disponibles.")
        )
    ),
    HundredPercentCategory(
        title = "Varios",
        percentage = 15,
        accentColor = GTAGreen,
        items = listOf(
            HundredPercentItem("Compra 5 propiedades."),
            HundredPercentItem("Compra un vehículo desde un sitio web."),
            HundredPercentItem("Recoge las 50 piezas de la nave espacial."),
            HundredPercentItem("Recoge los 50 fragmentos de la carta."),
            HundredPercentItem("Pasea y juega con Chop."),
            HundredPercentItem("Consigue una cita sexual."),
            HundredPercentItem("Recibe un servicio de una prostituta."),
            HundredPercentItem("Asalta una tienda."),
            HundredPercentItem("Completa 25 vuelos bajo el puente."),
            HundredPercentItem("Completa 8 vuelos a cuchillo."),
            HundredPercentItem("Completa 25 saltos acrobáticos."),
            HundredPercentItem("Visita un cine."),
            HundredPercentItem("Actividades con amigos:"),
            HundredPercentItem("Ve a un bar.", isSubItem = true),
            HundredPercentItem("Ve al club de striptease.", isSubItem = true),
            HundredPercentItem("Ve al cine.", isSubItem = true),
            HundredPercentItem("Juega a los dardos.", isSubItem = true)
        )
    )
)

val hundredPercentRewards = listOf(
    "Logro / Trofeo: Criminal Profesional.",
    "Franklin desbloquea una camiseta roja con un 100% dorado.",
    "Se pueden avistar OVNIs en Monte Chiliad, Fort Zancudo y Sandy Shores.",
    "Se desbloquea la misión de Extraños y Locos 'La Última' para Franklin."
)

// ===== COMPOSABLES =====
@Composable
fun PercentageBadge(percentage: Int, color: androidx.compose.ui.graphics.Color) {
    Surface(
        shape = RoundedCornerShape(2.dp),
        color = color.copy(alpha = 0.15f),
        modifier = Modifier
    ) {
        Text(
            text = "$percentage%",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                color = color,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        )
    }
}

@Composable
fun HundredPercentCategoryCard(category: HundredPercentCategory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = category.accentColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = GTACard)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 14.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
            )
        ) {
            // Cabecera con título y badge de porcentaje
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.title.uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GTATextPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                PercentageBadge(
                    percentage = category.percentage,
                    color = category.accentColor
                )
            }

            Spacer(Modifier.height(10.dp))
            HorizontalDivider(thickness = 0.5.dp, color = GTADivider)
            Spacer(Modifier.height(10.dp))

            // Items
            category.items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = if (item.isSubItem) 16.dp else 0.dp,
                            bottom = 6.dp
                        ),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = if (item.isSubItem) "·" else "▸",
                        color = if (item.isSubItem) GTATextSecondary else category.accentColor,
                        modifier = Modifier.padding(end = 8.dp, top = 1.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (item.isSubItem) GTATextSecondary else GTATextPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun RewardsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = GTAYellow,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = GTACard)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 14.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
            )
        ) {
            Text(
                text = "RECOMPENSAS",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = GTAYellow,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(thickness = 0.5.dp, color = GTADivider)
            Spacer(Modifier.height(10.dp))
            hundredPercentRewards.forEach { reward ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "★",
                        color = GTAYellow,
                        modifier = Modifier.padding(end = 8.dp, top = 1.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = reward,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GTATextPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HundredPercentScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "100%",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = GTAYellow,
                            letterSpacing = 3.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = GTAYellow
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GTASurface
                )
            )
        },
        containerColor = GTABackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                // Barra de progreso total visual
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = GTACard)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "PROGRESO TOTAL",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = GTATextSecondary,
                                    letterSpacing = 2.sp
                                )
                            )
                            Text(
                                text = "100%",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = GTAYellow,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        // Barra dividida por categorías
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(GTABorder, RoundedCornerShape(4.dp))
                        ) {
                            Box(Modifier.weight(0.50f).fillMaxHeight().background(MichaelBlue, RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)))
                            Box(Modifier.weight(0.10f).fillMaxHeight().background(FranklinGreen))
                            Box(Modifier.weight(0.10f).fillMaxHeight().background(TrevorOrange))
                            Box(Modifier.weight(0.15f).fillMaxHeight().background(GTAYellow))
                            Box(Modifier.weight(0.15f).fillMaxHeight().background(GTAGreen, RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)))
                        }
                        Spacer(Modifier.height(6.dp))
                        // Leyenda
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                "Historia 50%" to MichaelBlue,
                                "Aficiones 10%" to FranklinGreen,
                                "Extraños 10%" to TrevorOrange,
                                "Eventos 15%" to GTAYellow,
                                "Varios 15%" to GTAGreen
                            ).forEach { (label, color) ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(color, RoundedCornerShape(2.dp))
                                    )
                                    Spacer(Modifier.width(3.dp))
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = GTATextSecondary,
                                            fontSize = 8.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            items(hundredPercentData) { category ->
                HundredPercentCategoryCard(category = category)
            }

            item {
                RewardsCard()
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}