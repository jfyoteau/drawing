package com.github.jfyoteau.draw.app.ui.drawer.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

interface Shape {
    fun draw(scope: DrawScope)
}

fun DrawScope.shapeAnchor(
    point: Offset,
    color: Color,
) {
    drawCircle(
        color = color,
        radius = 6.dp.toPx(),
        center = point,
    )
}
