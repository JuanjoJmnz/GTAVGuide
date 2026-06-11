package com.juanjojmnz.gtavguide.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
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
import com.juanjojmnz.gtavguide.model.Mission
import com.juanjojmnz.gtavguide.ui.components.CharacterWheelSelector
import com.juanjojmnz.gtavguide.ui.theme.*
import com.juanjojmnz.gtavguide.viewmodel.MissionViewModel

data class MissionGroup(
    val approachGroup: String?,
    val approach1Name: String?,
    val approach2Name: String?,
    val approach1Missions: List<Mission>,
    val approach2Missions: List<Mission>,
    val commonMissions: List<Mission>
)

fun groupMissions(missions: List<Mission>): List<MissionGroup> {
    val result = mutableListOf<MissionGroup>()
    val processed = mutableSetOf<Int>()
    val sorted = missions.sortedBy { it.orderIndex }

    sorted.forEach { mission ->
        if (mission.id in processed) return@forEach

        if (mission.approachGroup != null) {
            if (result.any { g -> g.approachGroup == mission.approachGroup }) {
                processed.add(mission.id)
                return@forEach
            }
            val groupMissions = sorted.filter { it.approachGroup == mission.approachGroup }
            val approach1 = groupMissions.filter { it.approach == "1" }
            val approach2 = groupMissions.filter { it.approach == "2" }
            val common = groupMissions.filter { it.approach == null }
            result.add(
                MissionGroup(
                    approachGroup = mission.approachGroup,
                    approach1Name = approach1.firstOrNull()?.approachName,
                    approach2Name = approach2.firstOrNull()?.approachName,
                    approach1Missions = approach1,
                    approach2Missions = approach2,
                    commonMissions = common
                )
            )
            groupMissions.forEach { processed.add(it.id) }
        } else {
            result.add(
                MissionGroup(
                    approachGroup = null,
                    approach1Name = null,
                    approach2Name = null,
                    approach1Missions = emptyList(),
                    approach2Missions = emptyList(),
                    commonMissions = listOf(mission)
                )
            )
            processed.add(mission.id)
        }
    }
    return result
}

@Composable
fun ApproachGroupCard(
    group: MissionGroup,
    onMissionClick: (Mission) -> Unit
) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(
                    text = "⚡",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = group.approachGroup!!.replace("_", " "),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = GTAYellow,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            HorizontalDivider(thickness = 0.5.dp, color = GTADivider)
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(2.dp),
                        color = MichaelBlue.copy(alpha = 0.15f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = (group.approach1Name ?: "Opción 1").uppercase(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MichaelBlue,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    group.approach1Missions.forEach { mission ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(2.dp))
                                .clickable { onMissionClick(mission) }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "▸",
                                color = MichaelBlue,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = mission.title,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = GTATextPrimary
                                )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .width(0.5.dp)
                        .fillMaxHeight()
                        .background(GTADivider)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(2.dp),
                        color = TrevorOrange.copy(alpha = 0.15f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = (group.approach2Name ?: "Opción 2").uppercase(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TrevorOrange,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    group.approach2Missions.forEach { mission ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(2.dp))
                                .clickable { onMissionClick(mission) }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "▸",
                                color = TrevorOrange,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = mission.title,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = GTATextPrimary
                                )
                            )
                        }
                    }
                }
            }

            if (group.commonMissions.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(thickness = 0.5.dp, color = GTADivider)
                Spacer(Modifier.height(8.dp))
                group.commonMissions.forEach { mission ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(2.dp))
                            .clickable { onMissionClick(mission) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "★",
                            color = GTAYellow,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Text(
                            text = mission.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GTATextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MissionCard(mission: Mission, onClick: () -> Unit) {
    val accentColor = when {
        mission.characters.contains("MICHAEL") && mission.characters.contains("FRANKLIN")
                && mission.characters.contains("TREVOR") -> GTAGreen
        mission.characters.contains("MICHAEL")  -> MichaelBlue
        mission.characters.contains("FRANKLIN") -> FranklinGreen
        mission.characters.contains("TREVOR")   -> TrevorOrange
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
                Text(
                    text = "#${mission.orderIndex} ${mission.title}",
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

@Composable
fun MissionDetailSheet(mission: Mission, onClose: () -> Unit) {
    val accentColor = when {
        mission.characters.contains("MICHAEL") && mission.characters.contains("FRANKLIN")
                && mission.characters.contains("TREVOR") -> GTAGreen
        mission.characters.contains("MICHAEL")  -> MichaelBlue
        mission.characters.contains("FRANKLIN") -> FranklinGreen
        mission.characters.contains("TREVOR")   -> TrevorOrange
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
                text = "MISIÓN #${mission.orderIndex}",
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
                    text = mission.title,
                    style = MaterialTheme.typography.displaySmall.copy(color = GTATextPrimary)
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
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
                            color = chipColor.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp, chipColor.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = charName.uppercase(),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = chipColor,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                }
                if (mission.approachName != null) {
                    Spacer(Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(2.dp),
                        color = GTAYellow.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, GTAYellow.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(
                            text = "Enfoque: ${mission.approachName}".uppercase(),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = GTAYellow,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }
            }

            item {
                DetailSection(title = "Descripción", accentColor = accentColor) {
                    Text(
                        text = mission.description,
                        style = MaterialTheme.typography.bodyMedium.copy(color = GTATextPrimary)
                    )
                }
            }

            if (mission.unlockCondition.isNotEmpty()) {
                item {
                    DetailSection(title = "Se desbloquea", accentColor = GTATextSecondary) {
                        Text(
                            text = mission.unlockCondition,
                            style = MaterialTheme.typography.bodyMedium.copy(color = GTATextSecondary)
                        )
                    }
                }
            }

            if (mission.goldRequirements.isNotEmpty()) {
                item {
                    DetailSection(title = "Requisitos Medalla de Oro", accentColor = GTAYellow) {
                        mission.goldRequirements.forEach { req ->
                            Row(
                                modifier = Modifier.padding(bottom = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "★",
                                    color = GTAYellow,
                                    modifier = Modifier.padding(end = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = req,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = GTATextPrimary),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            if (mission.rewards.isNotEmpty()) {
                item {
                    DetailSection(title = "Recompensas", accentColor = GTAGreen) {
                        mission.rewards.forEach { reward ->
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
                                    text = reward,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = GTATextPrimary),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            if (mission.notes.isNotEmpty()) {
                item {
                    DetailSection(title = "Notas", accentColor = GTAOrange) {
                        Text(
                            text = mission.notes,
                            style = MaterialTheme.typography.bodyMedium.copy(color = GTATextSecondary)
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun DetailSection(
    title: String,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = accentColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            }
            .background(GTACard, RoundedCornerShape(4.dp))
            .padding(start = 14.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                color = accentColor,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(thickness = 0.5.dp, color = GTADivider)
        Spacer(Modifier.height(8.dp))
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionsScreen(
    navController: NavController,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedMission by viewModel.selectedMission.collectAsState()

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
                            text = "MISIONES",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MichaelBlue,
                                letterSpacing = 3.sp
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = MichaelBlue
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
                                    MissionCard(
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