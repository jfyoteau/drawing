package com.github.jfyoteau.draw.app.ui.drawer.shape

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

class Canvas {
    val shapes = mutableStateListOf<Shape>()
    var currentShape by mutableStateOf<Shape?>(null)
    var color = Color.Red

    fun addShape(shape: Shape) {
        shapes.add(shape)
    }

    fun removeShape(shape: Shape) {
        shapes.remove(shape)
    }

    fun drawLineFrom(point: Offset) {
        val shape = LineShape(
            startPoint = point,
            endPoint = point,
            color = color,
        )
        currentShape = shape
    }

    fun drawLineToDistance(distance: Offset) {
        val shape = currentShape as? LineShape ?: return
        currentShape = shape.copy(
            endPoint = shape.endPoint + distance
        )
    }

    fun drawCircle(firstPoint: Offset, secondPoint: Offset) {
        val shape = CircleShape(
            firstPoint = firstPoint,
            secondPoint = secondPoint,
            color = color,
        )
        currentShape = shape
    }
}
