package com.juanjojmnz.gtavguide.auxiliar

import androidx.compose.ui.graphics.Color
import com.juanjojmnz.gtavguide.model.Mission
import com.juanjojmnz.gtavguide.ui.theme.FranklinGreen
import com.juanjojmnz.gtavguide.ui.theme.GTAGreen
import com.juanjojmnz.gtavguide.ui.theme.MichaelBlue
import com.juanjojmnz.gtavguide.ui.theme.TrevorOrange

fun getMissionAccentColor(mission: Mission): Color {
    return when {
        mission.characters.contains("MICHAEL") &&
                mission.characters.contains("FRANKLIN") &&
                mission.characters.contains("TREVOR") -> GTAGreen

        mission.characters.contains("MICHAEL") -> MichaelBlue

        mission.characters.contains("FRANKLIN") -> FranklinGreen

        mission.characters.contains("TREVOR") -> TrevorOrange

        else -> GTAGreen
    }
}