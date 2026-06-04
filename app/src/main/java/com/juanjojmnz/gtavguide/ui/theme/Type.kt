package com.juanjojmnz.gtavguide.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.juanjojmnz.gtavguide.R

// Familias de fuentes
val PricedownFamily = FontFamily(
    Font(R.font.pricedown_bl)
)

val ChaletCompressedFamily = FontFamily(
    Font(R.font.chalet_comprime_cologne_sixty)
)

val ChaletLondonFamily = FontFamily(
    Font(R.font.chalet_london_nineteen_sixty_regular)
)

val LegalFamily = FontFamily(
    Font(R.font.legalv2),
    Font(R.font.legalv2i, style = FontStyle.Italic),
    Font(R.font.legalv2c),
    Font(R.font.legalv2ci, style = FontStyle.Italic)
)

// Tipografía de la app
val GTATypography = Typography(
    // Títulos grandes: Pricedown (el icónico de GTA)
    displayLarge = TextStyle(
        fontFamily = PricedownFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 52.sp,
        lineHeight = 56.sp,
        letterSpacing = 4.sp
    ),
    displayMedium = TextStyle(
        fontFamily = PricedownFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        lineHeight = 42.sp,
        letterSpacing = 2.sp
    ),
    displaySmall = TextStyle(
        fontFamily = PricedownFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    ),
    // Cabeceras: Chalet Comprimé
    headlineLarge = TextStyle(
        fontFamily = ChaletCompressedFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        letterSpacing = 1.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ChaletCompressedFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = ChaletCompressedFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),
    // Títulos de cards: Chalet London
    titleLarge = TextStyle(
        fontFamily = ChaletLondonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.3.sp
    ),
    titleMedium = TextStyle(
        fontFamily = ChaletLondonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 19.sp
    ),
    titleSmall = TextStyle(
        fontFamily = ChaletLondonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 17.sp
    ),
    // Cuerpo: Chalet London
    bodyLarge = TextStyle(
        fontFamily = ChaletLondonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ChaletLondonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = ChaletLondonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    // Labels
    labelLarge = TextStyle(
        fontFamily = ChaletCompressedFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = ChaletCompressedFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ChaletCompressedFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 13.sp
    )
)