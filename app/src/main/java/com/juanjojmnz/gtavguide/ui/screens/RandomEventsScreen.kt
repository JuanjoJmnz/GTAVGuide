package com.juanjojmnz.gtavguide.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.juanjojmnz.gtavguide.auxiliar.filterColor
import com.juanjojmnz.gtavguide.model.RandomEvent
import com.juanjojmnz.gtavguide.ui.components.CharacterWheelSelector
import com.juanjojmnz.gtavguide.ui.theme.*
import com.juanjojmnz.gtavguide.viewmodel.CharacterFilter
import com.juanjojmnz.gtavguide.viewmodel.RandomEventViewModel

@Composable
fun RandomEventCard(event: RandomEvent, onClick: () -> Unit) {
    val accentColor = when {
        event.characters.size >= 3                -> GTAGreen
        event.characters.contains("MICHAEL")  -> MichaelBlue
        event.characters.contains("FRANKLIN") -> FranklinGreen
        event.characters.contains("TREVOR")   -> TrevorOrange
        else -> GTAGreen
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = accentColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = GTACard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Número del evento
                    Surface(
                        shape = RoundedCornerShape(2.dp),
                        color = accentColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "#${event.number}",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = accentColor,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GTATextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GTATextSecondary
                    )
                )
                Spacer(Modifier.height(6.dp))
                // Recompensa
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "💰 ",
                        fontSize = 11.sp
                    )
                    Text(
                        text = event.reward,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GTAYellow
                        )
                    )
                }
                Spacer(Modifier.height(6.dp))
                // Chips de personajes
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    event.characters.forEach { char ->
                        val chipColor = when (char) {
                            "MICHAEL"  -> MichaelBlue
                            "FRANKLIN" -> FranklinGreen
                            "TREVOR"   -> TrevorOrange
                            else       -> GTAGreen
                        }
                        val charName = when (char) {
                            "MICHAEL"  -> "M"
                            "FRANKLIN" -> "F"
                            "TREVOR"   -> "T"
                            else       -> char.first().toString()
                        }
                        Surface(
                            shape = RoundedCornerShape(2.dp),
                            color = chipColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = charName,
                                modifier = Modifier.padding(
                                    horizontal = 6.dp, vertical = 2.dp
                                ),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = chipColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RandomEventDetailSheet(event: RandomEvent, onClose: () -> Unit) {
    val accentColor = when {
        event.characters.size >= 3                -> GTAGreen
        event.characters.contains("MICHAEL")  -> MichaelBlue
        event.characters.contains("FRANKLIN") -> FranklinGreen
        event.characters.contains("TREVOR")   -> TrevorOrange
        else -> GTAGreen
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GTABackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GTASurface)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Cerrar",
                    tint = accentColor
                )
            }
            Text(
                text = "EVENTO #${event.number}",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = accentColor,
                    letterSpacing = 2.sp
                )
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = GTATextPrimary
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GTATextSecondary
                    )
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    event.characters.forEach { char ->
                        val chipColor = when (char) {
                            "MICHAEL"  -> MichaelBlue
                            "FRANKLIN" -> FranklinGreen
                            "TREVOR"   -> TrevorOrange
                            else       -> GTAGreen
                        }
                        val charName = when (char) {
                            "MICHAEL"  -> "Michael"
                            "FRANKLIN" -> "Franklin"
                            "TREVOR"   -> "Trevor"
                            else       -> char
                        }
                        Surface(
                            shape = RoundedCornerShape(2.dp),
                            color = chipColor.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp, chipColor.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = charName.uppercase(),
                                modifier = Modifier.padding(
                                    horizontal = 10.dp, vertical = 4.dp
                                ),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = chipColor,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                }
            }

            item {
                DetailSection(title = "Descripción", accentColor = accentColor) {
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GTATextPrimary
                        )
                    )
                }
            }

            item {
                DetailSection(title = "Recompensa", accentColor = GTAYellow) {
                    Text(
                        text = event.reward,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GTATextPrimary
                        )
                    )
                }
            }

            if (event.unlocks.isNotEmpty()) {
                item {
                    DetailSection(title = "Desbloquea", accentColor = GTAGreen) {
                        Text(
                            text = event.unlocks,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GTATextPrimary
                            )
                        )
                    }
                }
            }

            if (event.notes.isNotEmpty()) {
                item {
                    DetailSection(title = "Notas", accentColor = GTAOrange) {
                        Text(
                            text = event.notes,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GTATextSecondary
                            )
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomEventsScreen(
    navController: NavController,
    viewModel: RandomEventViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedEvent by viewModel.selectedEvent.collectAsState()

    val accentColor by remember(uiState.selectedFilter) {
        derivedStateOf { filterColor(uiState.selectedFilter) }
    }

    if (selectedEvent != null) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it }
        ) {
            RandomEventDetailSheet(
                event = selectedEvent!!,
                onClose = { viewModel.clearSelectedEvent() }
            )
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "EVENTOS ALEATORIOS",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = accentColor,
                                letterSpacing = 3.sp
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = accentColor
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
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = GTAYellow)
                    }
                }
                uiState.events.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay eventos disponibles.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GTATextSecondary
                            )
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 160.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = "${uiState.events.size} eventos · mínimo 14 para el 100%",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = GTATextSecondary,
                                    letterSpacing = 1.sp
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        items(
                            items = uiState.events,
                            key = { it.id }
                        ) { event ->
                            RandomEventCard(
                                event = event,
                                onClick = { viewModel.selectEvent(event.id) }
                            )
                        }
                    }
                }
            }
        }

        CharacterWheelSelector(
            selected = uiState.selectedFilter,
            onSelected = { viewModel.onFilterSelected(it) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 24.dp)
        )
    }
}