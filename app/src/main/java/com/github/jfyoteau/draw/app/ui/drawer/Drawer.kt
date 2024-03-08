package com.github.jfyoteau.draw.app.ui.drawer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastForEach
import com.github.jfyoteau.draw.app.ui.drawer.action.AddLineDrawerAction
import com.github.jfyoteau.draw.app.ui.drawer.action.NoneDrawerAction
import com.github.jfyoteau.draw.core.ui.theme.DrawTheme
import kotlin.math.abs

@Composable
fun Drawer(
    state: DrawerState,
    modifier: Modifier = Modifier,
) {
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier
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
                        state.action = NoneDrawerAction()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTransformGestures { pan, zoom ->
                    val newScale = scale * zoom
                    scale = if (newScale < 1f) {
                        1f
                    } else if (newScale > 2f) {
                        2f
                    } else {
                        newScale
                    }
                    offset += pan
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .background(color = Color.LightGray)
        ) {
            state.canvas.shapes.forEach { shape ->
                shape.draw(scope = this)
            }

            state.canvas.currentShape?.draw(scope = this)
        }
    }
}

suspend fun PointerInputScope.detectTransformGestures(
    onGesture: (pan: Offset, zoom: Float) -> Unit
) {
    awaitEachGesture {
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop

        awaitFirstDown(requireUnconsumed = false)
        do {
            val event = awaitPointerEvent()
            val canceled = event.changes.fastAny { it.isConsumed }
            if (!canceled) {
                val zoomChange = event.calculateZoom()
                val panChange = if (event.changes.count() > 1) event.calculatePan() else Offset.Zero

                if (!pastTouchSlop) {
                    zoom *= zoomChange
                    pan += panChange

                    val centroidSize = event.calculateCentroidSize(useCurrent = false)
                    val zoomMotion = abs(1 - zoom) * centroidSize
                    val panMotion = pan.getDistance()

                    if (zoomMotion > touchSlop ||
                        panMotion > touchSlop
                    ) {
                        pastTouchSlop = true
                    }
                }

                if (pastTouchSlop) {
                    if (
                        zoomChange != 1f ||
                        panChange != Offset.Zero
                    ) {
                        onGesture(panChange, zoomChange)
                    }
                    event.changes.fastForEach {
                        if (it.positionChanged()) {
                            it.consume()
                        }
                    }
                }
            }
        } while (!canceled && event.changes.fastAny { it.pressed })
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
