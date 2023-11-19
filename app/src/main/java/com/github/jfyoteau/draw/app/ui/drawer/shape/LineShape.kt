package com.github.jfyoteau.draw.app.ui.drawer.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

data class LineShape(
    val startPoint: Offset = Offset.Zero,
    val endPoint: Offset = startPoint,
    val color: Color = Color.Blue,
    val isEditMode: Boolean = true,
): Shape {
    override fun draw(scope: DrawScope) {
        scope.run {
            drawLine(
                color = color,
                start = startPoint,
                end = endPoint,
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
            if (isEditMode) {
                shapeAnchor(point = startPoint, color = color)
                shapeAnchor(point = endPoint, color = color)
            }
        }
    }
}
