package com.github.jfyoteau.draw.app.ui.drawer.action

import androidx.compose.ui.geometry.Offset
import com.github.jfyoteau.draw.app.ui.drawer.DrawerState
import com.github.jfyoteau.draw.app.ui.drawer.command.DrawLine
import com.github.jfyoteau.draw.app.ui.drawer.shape.LineShape

class AddLineDrawerAction : DrawerAction {
    override fun onStart(drawerState: DrawerState, position: Offset) {
        drawerState.canvas.drawLineFrom(point = position)
    }

    override fun onMove(drawerState: DrawerState, distance: Offset) {
        drawerState.canvas.drawLineToDistance(distance = distance)
    }

    override fun onEnd(drawerState: DrawerState) {
        val canvas = drawerState.canvas
        val shape = canvas.currentShape as? LineShape ?: return
        drawerState.processCommand(
            DrawLine(
                shape = shape.copy(isEditMode = false),
                canvas = canvas
            )
        )
        canvas.currentShape = null
    }
}
