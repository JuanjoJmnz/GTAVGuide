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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.juanjojmnz.gtavguide.R
import com.juanjojmnz.gtavguide.auxiliar.filterColor
import com.juanjojmnz.gtavguide.model.Mission
import com.juanjojmnz.gtavguide.ui.components.CharacterWheelSelector
import com.juanjojmnz.gtavguide.ui.theme.*
import com.juanjojmnz.gtavguide.viewmodel.StrangersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrangersScreen(
    navController: NavController,
    viewModel: StrangersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedMission by viewModel.selectedMission.collectAsState()

    val accentColor by remember(uiState.selectedFilter) {
        derivedStateOf { filterColor(uiState.selectedFilter) }
    }

    if (selectedMission != null) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it }
        ) {
            MissionDetailSheet(
                mission = selectedMission!!,
                onClose = { viewModel.clearSelectedMission() }
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
                            text = "EXTRAÑOS Y LOCOS",
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
                        CircularProgressIndicator(color = TrevorOrange)
                    }
                }
                uiState.missions.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay misiones disponibles.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GTATextSecondary
                            )
                        )
                    }
                }
                else -> {
                    val groups = remember(uiState.missions) {
                        groupMissions(uiState.missions)
                    }
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
                                text = "${uiState.missions.size} misiones",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = GTATextSecondary,
                                    letterSpacing = 1.sp
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        items(
                            items = groups,
                            key = {
                                it.approachGroup
                                    ?: it.commonMissions.firstOrNull()?.id.toString()
                            }
                        ) { group ->
                            if (group.approachGroup != null) {
                                ApproachGroupCard(
                                    group = group,
                                    onMissionClick = { viewModel.selectMission(it.id) }
                                )
                            } else {
                                group.commonMissions.forEach { mission ->
                                    StrangerMissionCard(
                                        mission = mission,
                                        onClick = { viewModel.selectMission(mission.id) }
                                    )
                                }
                            }
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

@Composable
fun StrangerMissionCard(mission: Mission, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = TrevorOrange,
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
                Text(
                    text = mission.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GTATextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
                if (mission.unlockCondition.isNotEmpty()) {
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = mission.unlockCondition,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GTATextSecondary
                        )
                    )
                }
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    mission.characters.forEach { char ->
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
                            color = chipColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = charName.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = chipColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 8.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}