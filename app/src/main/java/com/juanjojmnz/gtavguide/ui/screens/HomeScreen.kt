package com.juanjojmnz.gtavguide.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.juanjojmnz.gtavguide.R
import com.juanjojmnz.gtavguide.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

data class MenuButton(
    val title: String,
    val subtitle: String,
    val iconRes: Int,
    val route: String,
    val accentColor: Color = GTAGreen
)

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    val bgIndex   = remember { Random.nextInt(1, 6) }
    val charIndex = remember { Random.nextInt(1, 36) }

    val bgResId = remember(bgIndex) {
        context.resources.getIdentifier(
            "gta_bg_$bgIndex", "drawable", context.packageName
        )
    }
    val charResId = remember(charIndex) {
        context.resources.getIdentifier(
            "gta_char_${charIndex}_fg", "drawable", context.packageName
        )
    }

    var logoVisible by remember { mutableStateOf(false) }
    var menuVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(200); logoVisible = true
        delay(500); menuVisible = true
    }

    Box(modifier = Modifier.fillMaxSize().background(GTABackground)) {

        if (bgResId != 0) {
            Image(
                painter = painterResource(id = bgResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.35f
            )
        }

        if (charResId != 0) {
            Image(
                painter = painterResource(id = charResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.62f)
                    .align(Alignment.TopEnd)
                    .offset(x = 16.dp),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    0.0f to Color.Transparent,
                    0.45f to GTABackground.copy(alpha = 0.4f),
                    0.62f to GTABackground.copy(alpha = 0.9f),
                    1.0f to GTABackground
                )
            )
        )
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    0.0f to GTABackground.copy(alpha = 0.75f),
                    0.55f to Color.Transparent
                )
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            AnimatedVisibility(
                visible = logoVisible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { -40 }
            ) {
                GTAHeader(
                    modifier = Modifier.padding(
                        top = 64.dp, start = 24.dp, end = 24.dp
                    )
                )
            }

            Spacer(Modifier.height(36.dp))

            AnimatedVisibility(
                visible = menuVisible,
                enter = fadeIn(tween(400)) + slideInVertically(tween(500)) { 60 }
            ) {
                MenuGrid(navController = navController)
            }
        }
    }
}

@Composable
private fun GTAHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(3.dp)
                .background(GTAOrange2)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "GTA V",
            style = MaterialTheme.typography.displayLarge.copy(
                color = GTAOrange2,
                shadow = Shadow(
                    color = GTAOrange.copy(alpha = 0.5f),
                    offset = Offset(0f, 4f),
                    blurRadius = 16f
                )
            )
        )
        Text(
            text = "GUÍA COMPLETA",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = GTATextPrimary,
                letterSpacing = 6.sp
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Todo lo que necesitas para conseguir el 100%",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = GTATextSecondary,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = GTAOrange.copy(alpha = 1f),
                    offset = Offset(0f, 4f),
                    blurRadius = 16f
                )
            )
        )
    }
}

@Composable
private fun MenuGrid(navController: NavController) {
    val menuItems = listOf(
        MenuButton("100%",          "Requisitos completos",     R.drawable.icono_cien_por_cien,        "hundred_percent", GTAYellow),
        MenuButton("Misiones",      "Historia principal",        R.drawable.icono_misiones_principales, "missions",        MichaelBlue),
        MenuButton("Extraños",      "Extraños y Locos",          R.drawable.icono_extranos_locos,       "strangers",       TrevorOrange),
        MenuButton("Golpes",        "Heists y crew",             R.drawable.icono_golpes,               "heists",          GTAGreen),
        MenuButton("Lester",        "Asesinatos e inversiones",  R.drawable.icono_asesinatos_lester,    "lester",          FranklinGreen),
        MenuButton("Coleccionables","Piezas, cartas...",         R.drawable.icono_coleccionables,       "collectibles",    GTABlue),
        MenuButton("Actividades",   "Golf, yoga, tenis...",      R.drawable.icono_actividades,          "activities",      GTAGreen),
        MenuButton("Secundarias",   "Caza, tiendas, relojes...", R.drawable.icono_otras_actividades,    "secondary",       GTAOrange),
        MenuButton("Carreras",      "Coches, motos, barcos",     R.drawable.icono_carreras,             "races",           GTARed),
        MenuButton("Eventos",       "50 eventos aleatorios",     R.drawable.icono_eventos_aleatorios,   "events",          GTAYellow),
        MenuButton("Propiedades",   "Compra y negocios",         R.drawable.icono_propiedades,          "properties",      GTAGreen),
        MenuButton("Mapa",          "Mapa interactivo",          R.drawable.icono_mapa,                 "map",             GTABlue),
        MenuButton("Curiosidades",  "Easter eggs y secretos",    R.drawable.icono_curiosidades,         "curiosities",     TrevorOrange),
        MenuButton("Trucos",        "Todos los cheats",          R.drawable.icono_trucos,               "cheats",          GTAGreen),
        MenuButton("Online",        "GTA Online",                R.drawable.icono_misiones_principales, "online",          GTABlue),
        MenuButton("Información",   "Sobre la app",              R.drawable.icono_informacion,          "about",           GTATextSecondary)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 2000.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = false
    ) {
        itemsIndexed(menuItems) { index, item ->
            MenuCard(
                button = item,
                animDelay = index * 40,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

@Composable
private fun MenuCard(
    button: MenuButton,
    animDelay: Int,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(animDelay.toLong())
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + scaleIn(tween(300), initialScale = 0.92f)
    ) {
        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().height(88.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = GTACard),
            border = BorderStroke(0.5.dp, button.accentColor.copy(alpha = 0.3f))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.horizontalGradient(
                            0f to button.accentColor.copy(alpha = 0.07f),
                            1f to Color.Transparent
                        )
                    )
                )
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .background(button.accentColor)
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 7.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = button.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(15.dp))
                    )
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(
                            text = button.title.uppercase(),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = GTATextPrimary,
                                letterSpacing = 1.sp,
                                fontSize = 11.sp
                            ),
                            maxLines = 1
                        )
                        Text(
                            text = button.subtitle,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GTATextSecondary,
                                fontSize = 9.sp
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}