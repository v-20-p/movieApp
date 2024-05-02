
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text


import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



import androidx.compose.runtime.*
import kotlinx.coroutines.launch


@Composable
fun Wave(
    modifier: Modifier = Modifier.fillMaxSize(),
    color: Color,
    amplitude: Float,
    length: Float,
    yOffset: Float
) {
    Canvas(modifier = modifier) {

            val wavePath = Path()

            val width = size.width
            val height = size.height

            val waveIncrement = width / length

            wavePath.moveTo(0f, height)
            wavePath.lineTo(0f, yOffset)


            for (i in 0 until length.toInt()) {
                val x = i * waveIncrement
                val y = if (i % 2 == 0) {
                   yOffset -amplitude
                } else {
                    yOffset + amplitude
                }

                wavePath.quadraticBezierTo(
                    x + waveIncrement / 2, y,
                    x + waveIncrement, yOffset
                )
            }

            wavePath.lineTo(width, yOffset)
            wavePath.lineTo(height*10000, yOffset)
            wavePath.close()

            drawPath(wavePath, color = color)
        }
    }


@Composable
fun MultiWave(pageCount: Int) {

    val amplitude = remember { Animatable(0f) }
    val length = remember { Animatable(0f) }

    LaunchedEffect(pageCount) {

        launch {
            amplitude.animateTo(when (pageCount) {
                0 -> 10f
                1 -> 5f
                2 -> 22f
                3 -> 11f
                else -> 1f
            })
        }
        launch {
            length.animateTo(when (pageCount) {
                0 -> 2f
                1 -> 1f
                2 -> 3f
                3 -> 1f
                else -> 1f
            })
        }
    }

    MultiWaveContent(amplitude.value, length.value)
}

@Composable
fun MultiWaveContent(amplitude: Float, length: Float) {

        Wave(color = Color(0xFFFDE7A6), amplitude = 32f + amplitude, length = 3f + length, yOffset = 50f)
        Wave(color = Color(0xFFFDDB75), amplitude = 22f + amplitude, length = 4f + length, yOffset = 120f)
        Wave(color = Color(0xFFF7CE54), amplitude = 35f + amplitude, length = 5f + length, yOffset = 180f)
        Wave(color = Color(0xFFFFCE3E), amplitude = 25f + amplitude, length = 5f + length, yOffset = 240f)
        Wave(color = Color(0xFFf4c331), amplitude = 15f + amplitude, length = 4f + length, yOffset = 350f)

}
@Composable
fun MyMultiWaveScreen(pageCount: Int, onClick: () -> Unit) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            ,
        contentAlignment = Alignment.Center
    ) {

            MultiWave(pageCount)


//        Spacer(modifier = Modifier.height(70.dp))

        Button(
            onClick = { onClick.invoke() },
            colors = ButtonColors(
                containerColor= Color.Black,
                contentColor= Color.White,
                disabledContainerColor=Color.White,
                disabledContentColor=Color.White
            ),
            modifier = Modifier.fillMaxWidth(.8f)
        ) {
            Text(text = "Let's get started ", fontSize = 18.sp, modifier = Modifier.padding(10.dp))
        }
    }
}