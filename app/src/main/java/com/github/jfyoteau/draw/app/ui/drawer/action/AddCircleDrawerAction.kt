package com.github.jfyoteau.draw.app.ui.drawer.action

import androidx.compose.ui.geometry.Offset
import com.github.jfyoteau.draw.app.ui.drawer.DrawerState
import com.github.jfyoteau.draw.app.ui.drawer.command.DrawCircle
import com.github.jfyoteau.draw.app.ui.drawer.shape.CircleShape

class AddCircleDrawerAction : DrawerAction {
    private var firstPoint = Offset.Zero
    private var secondPoint = Offset.Zero

    override fun onStart(drawerState: DrawerState, position: Offset) {
        firstPoint = position
        secondPoint = position
        drawerState.canvas.drawCircle(firstPoint = firstPoint, secondPoint = secondPoint)
    }

    override fun onMove(drawerState: DrawerState, distance: Offset) {
        secondPoint += distance
        drawerState.canvas.drawCircle(firstPoint = firstPoint, secondPoint = secondPoint)
    }

    override fun onEnd(drawerState: DrawerState) {
        val canvas = drawerState.canvas
        val shape = canvas.currentShape as? CircleShape ?: return
        drawerState.processCommand(
            DrawCircle(
                shape = shape.copy(isEditMode = false),
                canvas = canvas
            )
        )
        canvas.currentShape = null
    }
}
