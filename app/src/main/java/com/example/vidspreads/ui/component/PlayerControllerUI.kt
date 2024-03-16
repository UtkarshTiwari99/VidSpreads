package com.example.vidspreads.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidspreads.data.model.Video

@Composable
fun PlayerControllerUI(
    video: Video,
    visible:Boolean,
    isPlaying: Boolean,
    duration: Long,
    currentTime: Long,
    bufferPercentage: Long,
    playOrPause: (play:Boolean) -> Unit,
    onFastRewind: () -> Unit,
    onFastForward: () -> Unit,
    onSeekChange: (to:Float) -> Unit,
    convertHHMMSS:(time:Long)->String,
    modifier: Modifier
) {

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = modifier.background(Color.Black.copy(alpha = 0.2f))) {
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                Box {
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 22.dp)
                ) {
                    Icon(
                        Icons.Rounded.FastRewind, contentDescription = "FastRewind",
                        Modifier
                            .scale(2.2f)
                            .padding(13.dp)
                            .clip(CircleShape)
                            .clickable { onFastRewind() }, Color.White
                    )
                    Icon(
                        if (!isPlaying) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
                        contentDescription = "Play-Pause",
                        Modifier
                            .scale(2.2f)
                            .padding(13.dp)
                            .clip(CircleShape)
                            .clickable { playOrPause(!isPlaying) }, Color.White
                    )
                    Icon(
                        Icons.Rounded.FastForward, contentDescription = "FastForward",
                        Modifier
                            .scale(2.2f)
                            .padding(13.dp)
                            .clip(CircleShape)
                            .clickable { onFastForward() }, Color.White
                    )
                }
                Column (modifier =Modifier.fillMaxWidth()){
                    Icon(
                        Icons.Rounded.Fullscreen, contentDescription = "FastRewind",
                        Modifier
                            .scale(1f)
                            .align(Alignment.End)
                            .padding(end = 14.dp)
                            .clip(CircleShape)
                            .clickable {
//                                toggleFullScreen()
                                       }, Color.White
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        val modifier = Modifier
                        Text(
                            text = convertHHMMSS(currentTime),
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = modifier.weight(0.1f)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.8f)
                                .padding(horizontal = 2.dp)
                        ) {

                            Slider(
                                value = bufferPercentage.toFloat(),
                                enabled = false,
                                onValueChange = { /*do nothing*/ },
                                valueRange = 0f..100f,
                                colors =
                                SliderDefaults.colors(
                                    disabledThumbColor = Color.Transparent,
                                    disabledActiveTrackColor = Color.LightGray,
                                    disabledInactiveTrackColor = Color.Gray,
                                )
                            )

                            println(bufferPercentage)

                            Slider(
                                value = currentTime.toFloat(),
                                onValueChange = {
                                    onSeekChange(it)
                                },
                                valueRange = 0f..duration.toFloat(),
                                enabled = true,
                                colors =
                                SliderDefaults.colors(
                                    thumbColor = Color.White,
                                    activeTrackColor = Color.White,
                                    activeTickColor = Color.White,
                                    inactiveTrackColor = Color.Transparent
                                )
                            )
                        }
                        Text(
                            text = convertHHMMSS(duration),
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(0.1f)
                        )
                    }
                }
            }
        }
    }
}