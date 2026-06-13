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
import com.juanjojmnz.gtavguide.model.Property
import com.juanjojmnz.gtavguide.ui.components.CharacterWheelSelector
import com.juanjojmnz.gtavguide.ui.theme.*
import com.juanjojmnz.gtavguide.viewmodel.CharacterFilter
import com.juanjojmnz.gtavguide.viewmodel.PropertyViewModel
import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Long): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return "$${formatter.format(price)}"
}

@Composable
fun PropertyCard(property: Property, onClick: () -> Unit) {
    val accentColor = when {
        property.availableTo.size >= 3   -> GTAGreen
        property.availableTo.contains("MICHAEL")  -> MichaelBlue
        property.availableTo.contains("FRANKLIN") -> FranklinGreen
        property.availableTo.contains("TREVOR")   -> TrevorOrange
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
        Column(
            modifier = Modifier.padding(
                start = 14.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = property.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GTATextPrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatPrice(property.price),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GTAYellow,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = property.location,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GTATextSecondary
                    )
                )
                if (property.weeklyIncome > 0) {
                    Text(
                        text = "+${formatPrice(property.weeklyIncome)}/sem",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GTAGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Tipo de propiedad + chips de personajes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(2.dp),
                    color = accentColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = property.propertyType,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = accentColor,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    property.availableTo.forEach { char ->
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
fun PropertyDetailSheet(property: Property, onClose: () -> Unit) {
    val accentColor = when {
        property.availableTo.size >= 3   -> GTAGreen
        property.availableTo.contains("MICHAEL")  -> MichaelBlue
        property.availableTo.contains("FRANKLIN") -> FranklinGreen
        property.availableTo.contains("TREVOR")   -> TrevorOrange
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
                text = "PROPIEDAD",
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
                    text = property.title,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = GTATextPrimary
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = property.location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GTATextSecondary
                    )
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    property.availableTo.forEach { char ->
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

            // Precio e ingresos
            item {
                DetailSection(title = "Economía", accentColor = GTAYellow) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "PRECIO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GTATextSecondary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Text(
                                text = formatPrice(property.price),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = GTAYellow,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        if (property.weeklyIncome > 0) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "INGRESO SEMANAL",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = GTATextSecondary,
                                        letterSpacing = 1.sp
                                    )
                                )
                                Text(
                                    text = "+${formatPrice(property.weeklyIncome)}",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = GTAGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Descripción
            item {
                DetailSection(title = "Descripción", accentColor = accentColor) {
                    Text(
                        text = property.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GTATextPrimary
                        )
                    )
                }
            }

            // Desbloquea
            if (property.unlocks.isNotEmpty()) {
                item {
                    DetailSection(title = "Desbloquea", accentColor = GTAGreen) {
                        property.unlocks.forEach { unlock ->
                            Row(
                                modifier = Modifier.padding(bottom = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "▸",
                                    color = GTAGreen,
                                    modifier = Modifier.padding(end = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = unlock,
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

            // Notas
            if (property.notes.isNotEmpty()) {
                item {
                    DetailSection(title = "Notas", accentColor = GTAOrange) {
                        Text(
                            text = property.notes,
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
fun PropertiesScreen(
    navController: NavController,
    viewModel: PropertyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedProperty by viewModel.selectedProperty.collectAsState()

    val accentColor by remember(uiState.selectedFilter) {
        derivedStateOf { filterColor(uiState.selectedFilter) }
    }

    if (selectedProperty != null) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it }
        ) {
            PropertyDetailSheet(
                property = selectedProperty!!,
                onClose = { viewModel.clearSelectedProperty() }
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
                            text = "PROPIEDADES",
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
                        CircularProgressIndicator(color = GTAGreen)
                    }
                }
                uiState.properties.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay propiedades disponibles.",
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
                                text = "${uiState.properties.size} propiedades",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = GTATextSecondary,
                                    letterSpacing = 1.sp
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        items(
                            items = uiState.properties,
                            key = { it.id }
                        ) { property ->
                            PropertyCard(
                                property = property,
                                onClick = { viewModel.selectProperty(property.id) }
                            )
                        }
                    }
                }
            }
        }

        // Selector flotante
        CharacterWheelSelector(
            selected = uiState.selectedFilter,
            onSelected = { viewModel.onFilterSelected(it) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 24.dp)
        )
    }
}