package com.github.jfyoteau.draw.app.ui.drawer.command

import com.github.jfyoteau.draw.app.ui.drawer.shape.Canvas
import com.github.jfyoteau.draw.app.ui.drawer.shape.LineShape

class DrawLine(
    private var shape: LineShape,
    private val canvas: Canvas,
) : DrawCommand {
    override fun execute() {
        canvas.addShape(shape)
    }

    override fun undo() {
        canvas.removeShape(shape)
    }
}
