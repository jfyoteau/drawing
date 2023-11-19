package com.github.jfyoteau.draw.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.jfyoteau.draw.app.ui.drawer.Drawer
import com.github.jfyoteau.draw.app.ui.drawer.action.AddCircleDrawerAction
import com.github.jfyoteau.draw.app.ui.drawer.action.AddLineDrawerAction
import com.github.jfyoteau.draw.app.ui.drawer.rememberDrawerState
import com.github.jfyoteau.draw.app.ui.theme.DrawTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState()

                    Scaffold(
                        topBar = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 10.dp,
                                    alignment = Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Button(
                                    onClick = {
                                        drawerState.action = AddLineDrawerAction()
                                    }
                                ) {
                                    Text(text = "Line")
                                }
                                Button(
                                    onClick = {
                                        drawerState.action = AddCircleDrawerAction()
                                    }
                                ) {
                                    Text(text = "Circle")
                                }
                                Button(
                                    onClick = {
                                        drawerState.undo()
                                    }
                                ) {
                                    Text(text = "Undo")
                                }
                            }
                        }
                    ) { paddingValues ->
                        Drawer(
                            state = drawerState,
                            modifier = Modifier
                                .padding(paddingValues = paddingValues)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
