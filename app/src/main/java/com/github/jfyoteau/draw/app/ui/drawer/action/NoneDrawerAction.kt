package com.github.jfyoteau.draw.app.ui.drawer.action

import androidx.compose.ui.geometry.Offset
import com.github.jfyoteau.draw.app.ui.drawer.DrawerState

class NoneDrawerAction : DrawerAction {
    override fun onStart(drawerState: DrawerState, position: Offset) = Unit

    override fun onMove(drawerState: DrawerState, distance: Offset) = Unit

    override fun onEnd(drawerState: DrawerState) = Unit
}
