package com.github.jfyoteau.draw.app.ui.drawer.action

import androidx.compose.ui.geometry.Offset
import com.github.jfyoteau.draw.app.ui.drawer.DrawerState

interface DrawerAction {
    fun onStart(drawerState: DrawerState, position: Offset)
    fun onMove(drawerState: DrawerState, distance: Offset)
    fun onEnd(drawerState: DrawerState)
}
