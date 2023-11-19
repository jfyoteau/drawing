package com.github.jfyoteau.draw.app.ui.drawer.command

import com.github.jfyoteau.draw.app.ui.drawer.shape.Canvas
import com.github.jfyoteau.draw.app.ui.drawer.shape.CircleShape

class DrawCircle(
    private var shape: CircleShape,
    private val canvas: Canvas,
) : DrawCommand {
    override fun execute() {
        canvas.addShape(shape)
    }

    override fun undo() {
        canvas.removeShape(shape)
    }
}
