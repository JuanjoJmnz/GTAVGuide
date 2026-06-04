package com.juanjojmnz.gtavguide.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.juanjojmnz.gtavguide.R
import com.juanjojmnz.gtavguide.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ===== MODELOS =====
data class TrucoInfo(
    val id: String,
    val efecto: String,
    val comandoConsola: String? = null,
    val comandoPS: String? = null,
    val comandoXbox: String? = null,
    val codigoTelefono: String? = null
)

sealed interface TrucoListItem {
    data class TrucoEntry(val info: TrucoInfo) : TrucoListItem
    data class SubHeaderText(val text: String) : TrucoListItem
}

data class TrucosCategory(
    val title: String,
    val generalDescription: String? = null,
    val items: List<TrucoListItem>
)

// ===== DATOS =====
val listaDeCategoriasDeTrucosGTA = listOf(
    TrucosCategory(
        title = "Vehículos",
        generalDescription = "Algunos como el Dodo, el Kraken o el Duke O'Death necesitan haber completado sus respectivos eventos aleatorios.",
        items = listOf(
            TrucoListItem.TrucoEntry(TrucoInfo("bmx", "Aparece una BMX", "BANDIT", "Izquierda, Izquierda, Derecha, Derecha, Izquierda, Derecha, Cuadrado, Círculo, Triángulo, R1, R2", "Izquierda, Izquierda, Derecha, Derecha, Izquierda, Derecha, X, B, Y, RB, RT", "1-999-226-248")),
            TrucoListItem.TrucoEntry(TrucoInfo("buzzard", "Aparece un helicóptero Buzzard", "BUZZOFF", "Círculo, Círculo, L1, Círculo, Círculo, Círculo, L1, L2, R1, Triángulo, Círculo, Triángulo", "B, B, LB, B, B, B, LB, LT, RB, Y, B, Y", "1-999-289-9633")),
            TrucoListItem.TrucoEntry(TrucoInfo("caddy", "Aparece un coche de golf", "HOLEIN1", "Círculo, L1, Izquierda, R1, L2, X, R1, L1, Círculo, X", "B, LB, Izquierda, RB, LT, A, RB, LB, B, A", "1-999-4653-46-1")),
            TrucoListItem.TrucoEntry(TrucoInfo("comet", "Aparece un Comet", "COMET", "R1, Círculo, R2, Derecha, L1, L2, X, X, Cuadrado, R1", "RB, B, RT, Derecha, LB, LT, A, A, X, RB", "1-999-266-38")),
            TrucoListItem.TrucoEntry(TrucoInfo("duster", "Aparece una avioneta fumigadora", "FLYSPRAY", "Derecha, Izquierda, R1, R1, R1, Izquierda, Triángulo, Triángulo, X, Círculo, L1, L1", "Derecha, Izquierda, RB, RB, RB, Izquierda, Y, Y, A, B, LB, LB", "1-999-359-77729")),
            TrucoListItem.TrucoEntry(TrucoInfo("limusina", "Aparece una limusina", "VINEWOOD", "R2, Derecha, L2, Izquierda, Izquierda, R1, L1, Círculo, Derecha", "RT, Derecha, LT, Izquierda, Izquierda, RB, LB, B, Derecha", "1-999-846-39663")),
            TrucoListItem.TrucoEntry(TrucoInfo("parachute", "Consigues un paracaídas", "SKYDIVE", "Izquierda, Derecha, L1, L2, R1, R2, R2, Izquierda, Izquierda, Derecha, L1", "Izquierda, Derecha, LB, LT, RB, RT, RT, Izquierda, Izquierda, Derecha, LB", "1-999-759-3483")),
            TrucoListItem.TrucoEntry(TrucoInfo("pcj", "Aparece una moto PCJ-600", "ROCKET", "R1, Derecha, Izquierda, Derecha, R2, Izquierda, Derecha, Cuadrado, Derecha, L2, L1, L1", "RB, Derecha, Izquierda, Derecha, RT, Izquierda, Derecha, X, Derecha, LT, LB, LB", "1-999-762-538")),
            TrucoListItem.TrucoEntry(TrucoInfo("rapid", "Aparece un Rapid GT", "RAPIDGT", "R2, L1, Círculo, Derecha, L1, R1, Derecha, Izquierda, Círculo, R2", "RT, LB, B, Derecha, LB, RB, Derecha, Izquierda, B, RT", "1-999-727-4348")),
            TrucoListItem.TrucoEntry(TrucoInfo("sanchez", "Aparece una moto Sánchez", "OFFROAD", "Círculo, X, L1, Círculo, Círculo, L1, Círculo, R1, R2, L2, L1, L1", "B, A, LB, B, B, LB, B, RB, RT, LT, LB, LB", "1-999-633-7623")),
            TrucoListItem.TrucoEntry(TrucoInfo("mallard", "Aparece un avión de acrobacias", "BARNSTORM", "Círculo, Derecha, L1, L2, Izquierda, R1, L1, L1, Izquierda, Izquierda, X, Triángulo", "B, Derecha, LB, LT, Izquierda, RB, LB, LB, Izquierda, Izquierda, A, Y", "1-999-2276-78676")),
            TrucoListItem.TrucoEntry(TrucoInfo("trashmaster", "Aparece un Trashmaster", "TRASHED", "Círculo, R1, Círculo, R1, Izquierda, Izquierda, R1, L1, Círculo, Derecha", "B, RB, B, RB, Izquierda, Izquierda, RB, LB, B, Derecha", "1-999-872-7433")),
            TrucoListItem.TrucoEntry(TrucoInfo("dodo", "Aparece un Hidroavión Dodo", "EXTINCT", null, null, "1-999-398-4628")),
            TrucoListItem.TrucoEntry(TrucoInfo("kraken", "Aparece un submarino Kraken", "BUBBLES", null, null, "1-999-282-2537")),
            TrucoListItem.TrucoEntry(TrucoInfo("duke", "Aparece el Duke O'Death (antibalas)", "DEATHCAR", null, null, "1-999-3328-4227"))
        )
    ),
    TrucosCategory(
        title = "Salud y Blindaje",
        items = listOf(
            TrucoListItem.TrucoEntry(TrucoInfo("salud_max", "Salud y blindaje al máximo", "TURTLE", "Círculo, L1, Triángulo, R2, X, Cuadrado, Círculo, Derecha, Cuadrado, L1, L1, L1", "B, LB, Y, RT, A, X, B, Derecha, X, LB, LB, LB", "1-999-887-853")),
            TrucoListItem.TrucoEntry(TrucoInfo("invencibilidad", "Invencibilidad (5 minutos)", "PAINKILLER", "Derecha, X, Derecha, Izquierda, Derecha, R1, Derecha, Izquierda, X, Triángulo", "Derecha, A, Derecha, Izquierda, Derecha, RB, Derecha, Izquierda, A, Y", "1-999-724-654-5537"))
        )
    ),
    TrucosCategory(
        title = "Armas y Munición",
        items = listOf(
            TrucoListItem.TrucoEntry(TrucoInfo("all_guns", "Todas las armas y munición", "TOOLUP", "Triángulo, R2, Izquierda, L1, X, Derecha, Triángulo, Abajo, Cuadrado, L1, L1, L1", "Y, RT, Izquierda, LB, A, Derecha, Y, Abajo, X, LB, LB, LB", "1-999-8665-87")),
            TrucoListItem.TrucoEntry(TrucoInfo("balas_expl", "Balas explosivas", "HIGHEX", "Derecha, Cuadrado, X, Izquierda, R1, R2, Izquierda, Derecha, Derecha, L1, L1, L1", "Derecha, X, A, Izquierda, RB, RT, Izquierda, Derecha, Derecha, LB, LB, LB", "1-999-444-439")),
            TrucoListItem.TrucoEntry(TrucoInfo("balas_fue", "Balas de fuego", "INCENDIARY", "L1, R1, Cuadrado, R1, Izquierda, R2, R1, Izquierda, Cuadrado, Derecha, L1, L1", "LB, RB, X, RB, Izquierda, RT, RB, Izquierda, X, Derecha, LB, LB", "1-999-462-363-4279"))
        )
    ),
    TrucosCategory(
        title = "Habilidades y Poderes Especiales",
        items = listOf(
            TrucoListItem.TrucoEntry(TrucoInfo("puñetazo_expl", "Puñetazos explosivos", "HOTHANDS", "Derecha, Izquierda, X, Triángulo, R1, Círculo, Círculo, Círculo, L2", "Derecha, Izquierda, A, Y, RB, B, B, B, LT", "1-999-4684-2637")),
            TrucoListItem.TrucoEntry(TrucoInfo("hab_esp", "Recargar habilidad especial", "POWERUP", "X, X, Cuadrado, R1, L1, X, Derecha, Izquierda, X", "A, A, X, RB, LB, A, Derecha, Izquierda, A", "1-999-769-3787")),
            TrucoListItem.TrucoEntry(TrucoInfo("super_salto", "Súper salto", "HOPTOIT", "Izquierda, Izquierda, Triángulo, Triángulo, Derecha, Derecha, Izquierda, Derecha, Cuadrado, R1, R2", "Izquierda, Izquierda, Y, Y, Derecha, Derecha, Izquierda, Derecha, X, RB, RT", "1-999-467-86-48")),
            TrucoListItem.TrucoEntry(TrucoInfo("correr_rap", "Correr más rápido", "CATCHME", "Triángulo, Izquierda, Derecha, Derecha, L2, L1, Cuadrado", "Y, Izquierda, Derecha, Derecha, LT, LB, X", "1-999-228-8463")),
            TrucoListItem.TrucoEntry(TrucoInfo("nadar_rap", "Nadar más rápido", "GOTGILLS", "Izquierda, Izquierda, L1, Derecha, Derecha, R2, Izquierda, L2, Derecha", "Izquierda, Izquierda, LB, Derecha, Derecha, RT, Izquierda, LT, Derecha", "1-999-468-44557")),
            TrucoListItem.TrucoEntry(TrucoInfo("tiempo_lento", "Apuntar en cámara lenta (x3, la 4ª desactiva)", "DEADEYE", "Cuadrado, L2, R1, Triángulo, Izquierda, Cuadrado, L2, Derecha, X", "X, LT, RB, Y, Izquierda, X, LT, Derecha, A", "1-999-332-3393")),
            TrucoListItem.TrucoEntry(TrucoInfo("skyfall", "Caída libre desde el cielo", "SKYFALL", "L1, L2, R1, R2, Izquierda, Derecha, Izquierda, Derecha, L1, L2, R1, R2, Izquierda, Derecha, Izquierda, Derecha", "LB, LT, RB, RT, Izquierda, Derecha, Izquierda, Derecha, LB, LT, RB, RT, Izquierda, Derecha, Izquierda, Derecha", "1-999-759-3255"))
        )
    ),
    TrucosCategory(
        title = "Nivel de Búsqueda Policial",
        items = listOf(
            TrucoListItem.TrucoEntry(TrucoInfo("mas_poli", "Aumentar nivel de búsqueda", "FUGITIVE", "R1, R1, Círculo, R2, Izquierda, Derecha, Izquierda, Derecha, Izquierda, Derecha", "RB, RB, B, RT, Izquierda, Derecha, Izquierda, Derecha, Izquierda, Derecha", "1-999-3844-8483")),
            TrucoListItem.TrucoEntry(TrucoInfo("menos_poli", "Disminuir nivel de búsqueda", "LAWYERUP", "R1, R1, Círculo, R2, Derecha, Izquierda, Derecha, Izquierda, Derecha, Izquierda", "RB, RB, B, RT, Derecha, Izquierda, Derecha, Izquierda, Derecha, Izquierda", "1-99-5299-3787"))
        )
    ),
    TrucosCategory(
        title = "Entorno, Clima y Varios",
        items = listOf(
            TrucoListItem.TrucoEntry(TrucoInfo("borracho", "Modo borracho", "LIQUOR", "Triángulo, Derecha, Derecha, Izquierda, Derecha, Cuadrado, Círculo, Izquierda", "Y, Derecha, Derecha, Izquierda, Derecha, X, B, Izquierda", "1-999-547-867")),
            TrucoListItem.TrucoEntry(TrucoInfo("slowmotion", "Cámara lenta (x4, la 5ª desactiva)", "SLOWMO", "Triángulo, Izquierda, Derecha, Derecha, Cuadrado, R2, R1", "Y, Izquierda, Derecha, Derecha, X, RT, RB", "1-999-756-966")),
            TrucoListItem.TrucoEntry(TrucoInfo("explosion", "Explosión + teléfono negro", null, null, null, "1-999-367-3767")),
            TrucoListItem.TrucoEntry(TrucoInfo("clima", "Cambiar clima", "MAKEITRAIN", "R2, X, L1, L1, L2, L2, L2, Cuadrado", "RT, A, LB, LB, LT, LT, LT, X", "1-999-625-384-7246")),
            TrucoListItem.TrucoEntry(TrucoInfo("lunar", "Baja gravedad", "FLOATER", "Izquierda, Izquierda, L1, R1, L1, Derecha, Izquierda, L1, Izquierda", "Izquierda, Izquierda, LB, RB, LB, Derecha, Izquierda, LB, Izquierda", "1-999-356-2837")),
            TrucoListItem.TrucoEntry(TrucoInfo("coches_slip", "Coches resbaladizos", "SNOWDAY", "Triángulo, R1, R1, Izquierda, R1, L1, R2, L1", "Y, RB, RB, Izquierda, RB, LB, RT, LB", "1-999-766-9329"))
        )
    )
)

// ===== VIEWMODEL =====
class TrucosViewModel : ViewModel() {
    private val _categorias = MutableStateFlow(listaDeCategoriasDeTrucosGTA)
    val categorias: StateFlow<List<TrucosCategory>> = _categorias
}

// ===== HELPERS ICONOS =====
fun getPSButtonIcon(buttonName: String): Int? = when (buttonName.trim().uppercase()) {
    "IZQUIERDA" -> R.drawable.playstation_left_button
    "DERECHA"   -> R.drawable.playstation_right_button
    "ARRIBA"    -> R.drawable.playstation_up_button
    "ABAJO"     -> R.drawable.playstation_down_button
    "X"         -> R.drawable.playstation_button_x
    "CUADRADO"  -> R.drawable.playstation_button_s
    "CÍRCULO", "CIRCULO" -> R.drawable.playstation_button_c
    "TRIÁNGULO", "TRIANGULO" -> R.drawable.playstation_button_t
    "L1" -> R.drawable.playstation_button_l1
    "R1" -> R.drawable.playstation_button_r1
    "L2" -> R.drawable.playstation_button_l2
    "R2" -> R.drawable.playstation_button_r2
    else -> null
}

fun getXboxButtonIcon(buttonName: String): Int? = when (buttonName.trim().uppercase()) {
    "IZQUIERDA" -> R.drawable.xbox_d_pad_left
    "DERECHA"   -> R.drawable.xbox_d_pad_right
    "ARRIBA"    -> R.drawable.xbox_d_pad_up
    "ABAJO"     -> R.drawable.xbox_d_pad_down
    "A"  -> R.drawable.xbox_button_a
    "B"  -> R.drawable.xbox_button_b
    "X"  -> R.drawable.xbox_button_x
    "Y"  -> R.drawable.xbox_button_y
    "LB" -> R.drawable.xbox_left_bumper
    "RB" -> R.drawable.xbox_right_bumper
    "LT" -> R.drawable.xbox_left_trigger
    "RT" -> R.drawable.xbox_right_trigger
    else -> null
}

// ===== COMPOSABLES =====
@Composable
fun ButtonIcon(iconRes: Int?, desc: String, size: Dp = 22.dp) {
    if (iconRes != null) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = desc,
            modifier = Modifier.size(size),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ComandoRow(comando: String?, platform: String) {
    if (comando.isNullOrBlank()) return
    val buttons = comando.split(",").map { it.trim() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        buttons.forEach { btn ->
            val icon = if (platform == "PS") getPSButtonIcon(btn) else getXboxButtonIcon(btn)
            if (icon != null) {
                ButtonIcon(iconRes = icon, desc = btn)
            } else {
                // Si no hay icono (Abajo, etc.) muestra texto pequeño
                Text(
                    text = btn,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GTATextSecondary,
                        fontSize = 9.sp
                    )
                )
            }
        }
    }
}

@Composable
fun TrucoCard(truco: TrucoInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = GTAGreen,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = GTACard),
    ) {
        Column(
            modifier = Modifier.padding(
                start = 14.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
            )
        ) {
            // Efecto
            Text(
                text = truco.efecto,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = GTATextPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(10.dp))

            // PC
            truco.comandoConsola?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "PC",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GTATextSecondary,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.width(60.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GTAGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                }
            }

            // PlayStation
            if (!truco.comandoPS.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "PS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GTATextSecondary,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.width(60.dp)
                    )
                    ComandoRow(comando = truco.comandoPS, platform = "PS")
                }
            }

            // Xbox
            if (!truco.comandoXbox.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "Xbox",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GTATextSecondary,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.width(60.dp)
                    )
                    ComandoRow(comando = truco.comandoXbox, platform = "XBOX")
                }
            }

            // Teléfono
            truco.codigoTelefono?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Móvil",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GTATextSecondary,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.width(60.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GTAYellow
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(20.dp)
                .background(GTAGreen)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.headlineSmall.copy(
                color = GTAGreen,
                letterSpacing = 2.sp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheatsScreen(
    navController: NavController,
    viewModel: TrucosViewModel = viewModel()
) {
    val categorias by viewModel.categorias.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TRUCOS",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = GTAGreen,
                            letterSpacing = 3.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = GTAGreen
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "⚠ Usar trucos desactiva los logros y trofeos de la sesión actual.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GTAOrange
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            categorias.forEach { categoria ->
                item {
                    CategoryHeader(title = categoria.title)
                    categoria.generalDescription?.let { desc ->
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GTATextSecondary
                            ),
                            modifier = Modifier.padding(
                                start = 11.dp, bottom = 8.dp
                            )
                        )
                    }
                }
                items(
                    items = categoria.items.filterIsInstance<TrucoListItem.TrucoEntry>(),
                    key = { it.info.id }
                ) { entry ->
                    TrucoCard(truco = entry.info)
                }
            }
        }
    }
}