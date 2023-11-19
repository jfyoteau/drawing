package com.github.jfyoteau.draw.app.ui.drawer.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

data class CircleShape(
    val firstPoint: Offset,
    val secondPoint: Offset,
    val color: Color = Color.Blue,
    val isEditMode: Boolean = true,
) : Shape {
    private val centerPoint: Offset = Offset(
        x = (firstPoint.x + secondPoint.x) / 2f,
        y = (firstPoint.y + secondPoint.y) / 2f,
    )
    private val radius: Float = sqrt(
        (centerPoint.x - firstPoint.x).pow(2) + (centerPoint.y - firstPoint.y).pow(2)
    )

    override fun draw(scope: DrawScope) {
        scope.run {
            drawCircle(
                color = color,
                radius = radius,
                center = centerPoint,
                style = Stroke(
                    width = 2.dp.toPx()
                )
            )
            if (isEditMode) {
                shapeAnchor(point = firstPoint, color = color)
                shapeAnchor(point = secondPoint, color = color)
            }
        }
    }
}
