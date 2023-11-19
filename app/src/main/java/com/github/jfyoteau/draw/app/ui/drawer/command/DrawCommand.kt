package com.github.jfyoteau.draw.app.ui.drawer.command

interface DrawCommand {
    fun execute()
    fun undo()
}
