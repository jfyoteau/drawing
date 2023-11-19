package com.github.jfyoteau.draw.app.ui.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.github.jfyoteau.draw.app.ui.drawer.action.DrawerAction
import com.github.jfyoteau.draw.app.ui.drawer.action.NoneDrawerAction
import com.github.jfyoteau.draw.app.ui.drawer.command.DrawCommand
import com.github.jfyoteau.draw.app.ui.drawer.shape.Canvas

interface DrawerState {
    val canvas: Canvas
    var color: Color
    var action: DrawerAction

    fun processCommand(command: DrawCommand)
    fun undo()
}

@Immutable
class DrawerStateImpl : DrawerState {
    override val canvas = Canvas()
    override var color = Color.Red
    override var action: DrawerAction = NoneDrawerAction()
    private val commandHistory = mutableListOf<DrawCommand>()

    override fun processCommand(command: DrawCommand) {
        command.execute()
        commandHistory.add(command)
    }

    override fun undo() {
        commandHistory.removeLastOrNull()?.undo()
    }
}

@Composable
fun rememberDrawerState(): DrawerState =
    remember {
        DrawerStateImpl()
    }
