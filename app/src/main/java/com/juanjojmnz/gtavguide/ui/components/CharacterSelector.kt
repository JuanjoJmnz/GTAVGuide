package com.juanjojmnz.gtavguide.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.juanjojmnz.gtavguide.R
import com.juanjojmnz.gtavguide.viewmodel.CharacterFilter
import kotlin.math.atan2

@Composable
fun CharacterWheelSelector(
    selected: CharacterFilter,
    onSelected: (CharacterFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    val size = 130.dp

    val michaelAlpha by animateFloatAsState(
        targetValue = if (selected == CharacterFilter.MICHAEL) 1f else 0.45f,
        animationSpec = tween(200), label = "michael"
    )
    val franklinAlpha by animateFloatAsState(
        targetValue = if (selected == CharacterFilter.FRANKLIN) 1f else 0.45f,
        animationSpec = tween(200), label = "franklin"
    )
    val trevorAlpha by animateFloatAsState(
        targetValue = if (selected == CharacterFilter.TREVOR) 1f else 0.45f,
        animationSpec = tween(200), label = "trevor"
    )
    val allAlpha by animateFloatAsState(
        targetValue = if (selected == CharacterFilter.ALL) 1f else 0.45f,
        animationSpec = tween(200), label = "all"
    )

    Box(
        modifier = modifier
            .size(size)
            .shadow(8.dp, CircleShape)
            .clip(CircleShape)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val center = Offset(size.toPx() / 2f, size.toPx() / 2f)
                    val dx = offset.x - center.x
                    val dy = offset.y - center.y
                    val angle = (Math.toDegrees(
                        atan2(dy.toDouble(), dx.toDouble())
                    ) + 360) % 360

                    val filter = when {
                        angle >= 135 && angle < 225 -> CharacterFilter.MICHAEL
                        angle >= 225 && angle < 315 -> CharacterFilter.FRANKLIN
                        angle >= 315 || angle < 45  -> CharacterFilter.TREVOR
                        else                        -> CharacterFilter.ALL
                    }
                    onSelected(filter)
                }
            }
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.icon_all_players),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 1f },
            contentScale = ContentScale.Crop
        )


        SectorOverlay(
            selected = selected == CharacterFilter.MICHAEL,
            alpha = 1f - michaelAlpha,
            startAngle = 135f,
            sweepAngle = 90f
        )
        SectorOverlay(
            selected = selected == CharacterFilter.FRANKLIN,
            alpha = 1f - franklinAlpha,
            startAngle = 225f,
            sweepAngle = 90f
        )
        SectorOverlay(
            selected = selected == CharacterFilter.TREVOR,
            alpha = 1f - trevorAlpha,
            startAngle = 315f,
            sweepAngle = 90f
        )
        SectorOverlay(
            selected = selected == CharacterFilter.ALL,
            alpha = 1f - allAlpha,
            startAngle = 45f,
            sweepAngle = 90f
        )
    }
}

@Composable
private fun SectorOverlay(
    selected: Boolean,
    alpha: Float,
    startAngle: Float,
    sweepAngle: Float
) {
    androidx.compose.foundation.Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!selected) {
            drawArc(
                color = androidx.compose.ui.graphics.Color.Black.copy(alpha = alpha * 0.6f),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = size
            )
        }
    }
}