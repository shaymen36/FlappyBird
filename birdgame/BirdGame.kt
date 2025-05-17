package com.st10312252.flappybird.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BirdGame()
            }
        }
    }
}

@Composable
fun BirdGame() {
    var birdY by remember { mutableStateOf(500f) }
    var velocity by remember { mutableStateOf(0f) }
    var score by remember { mutableStateOf(0) }
    var towerX by remember { mutableStateOf(1000f) }
    val gravity = 0.6f
    val jumpVelocity = -15f
    val birdRadius = 30f
    val towerWidth = 150f
    val towerGap = 400f

    LaunchedEffect(Unit) {
        while (true) {
            delay(16L)  // Roughly 60 FPS
            birdY += velocity
            velocity += gravity
            towerX -= 8f

            if (towerX < -towerWidth) {
                towerX = 1000f
                score++
            }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .pointerInput(Unit) {
                detectTapGestures {
                    velocity = jumpVelocity
                }
            }
    ) {
        // Draw the bird
        drawCircle(
            color = Color.Yellow,
            radius = birdRadius,
            center = Offset(200f, birdY)
        )

        // Draw the towers
        drawRect(
            color = Color.Green,
            topLeft = Offset(towerX, 0f),
            size = androidx.compose.ui.geometry.Size(towerWidth, 500f)
        )

        drawRect(
            color = Color.Green,
            topLeft = Offset(towerX, 500f + towerGap),
            size = androidx.compose.ui.geometry.Size(towerWidth, size.height)
        )
    }
}
