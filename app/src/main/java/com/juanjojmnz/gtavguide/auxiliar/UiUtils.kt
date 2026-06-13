package com.juanjojmnz.gtavguide.auxiliar

import androidx.compose.ui.graphics.Color
import com.juanjojmnz.gtavguide.ui.theme.*
import com.juanjojmnz.gtavguide.viewmodel.CharacterFilter

fun filterColor(filter: CharacterFilter): Color = when (filter) {
    CharacterFilter.ALL      -> GTAGreen
    CharacterFilter.MICHAEL  -> MichaelBlue
    CharacterFilter.FRANKLIN -> FranklinGreen
    CharacterFilter.TREVOR   -> TrevorOrange
}