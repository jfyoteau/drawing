package com.github.jfyoteau.draw.app.ui.drawer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.github.jfyoteau.draw.app.ui.drawer.action.AddLineDrawerAction
import com.github.jfyoteau.draw.core.ui.theme.DrawTheme

@Composable
fun Drawer(
    state: DrawerState,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .background(color = Color.LightGray)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { position ->
                        state.action.onStart(state, position)
                    },
                    onDrag = { change, distance ->
                        change.consume()
                        state.action.onMove(state, distance)
                    },
                    onDragEnd = {
                        state.action.onEnd(state)
                    }
                )
            },
    ) {
        state.canvas.shapes.forEach { shape ->
            shape.draw(scope = this)
        }

        state.canvas.currentShape?.draw(scope = this)
    }
}

@Preview
@Composable
private fun DrawerPreview() {
    DrawTheme {
        val state = rememberDrawerState()
        state.action = AddLineDrawerAction()

        Drawer(
            state = state,
            modifier = Modifier.fillMaxSize()
        )
    }
}
