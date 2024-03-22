package com.example.vidspreads.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import com.example.vidspreads.data.viewmodel.VideoViewModel
import android.util.Log
import androidx.compose.runtime.LaunchedEffect


@Composable
fun VideoUI(modifier: Modifier, videoViewModel: VideoViewModel){
    println("VideoUI")
    val visible= remember { mutableStateOf(false) }
    val video= remember { videoViewModel.currentVideo}
    val player= remember { videoViewModel.player }
    val isPlaying= remember { videoViewModel.isPlaying }
    val duration= remember { videoViewModel.duration }
    val currentTime= remember {
        videoViewModel.currentTime
    }
    val bufferPercentage= remember {
        videoViewModel.bufferPercentage
    }

    Column (modifier = modifier){
        val outerModifier = Modifier.fillMaxSize()
        Box (modifier = outerModifier
            .clickable { visible.value = !visible.value }){
            var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }

            val lifecycleOwner = LocalLifecycleOwner.current

            val modifier =  Modifier.fillMaxSize()


            LaunchedEffect(lifecycle.name,lifecycleOwner) {
                val observer =
                    LifecycleEventObserver { _, event ->
                        lifecycle = event
                    }
                when (lifecycle.name) {
                    Lifecycle.Event.ON_CREATE.name ->{
                        Log.e("player a","a")
                        lifecycleOwner.lifecycle.addObserver(observer)
                        videoViewModel.changeOrientation(true)
                    }
                    Lifecycle.Event.ON_RESUME.name -> {
                        Log.e("player b","b")
                        videoViewModel.changeOrientation(true)
                        videoViewModel.playOrPause(true)
                    }
                    Lifecycle.Event.ON_PAUSE.name -> {
                        Log.e("player c","c")
                         videoViewModel.playOrPause(false)
                    }
                    Lifecycle.Event.ON_STOP.name -> {
                            Log.e("player d","d")
                            lifecycleOwner.lifecycle.removeObserver(observer)
                            videoViewModel.playOrPause(false)
                            videoViewModel.changeOrientation(false)
                    }
                }
            }

            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        this.player = player
                        this.useController=false
                    }
                },
                update = {
                    when (lifecycle) {
                        Lifecycle.Event.ON_CREATE -> {
                            it.onResume()
                        }
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                        }
                        Lifecycle.Event.ON_RESUME -> {
                            it.onResume()
                        }
                        Lifecycle.Event.ON_STOP -> {
                            it.onPause()
                        }
                        else -> Unit
                    }
                },
                    modifier = modifier
            )

            PlayerControllerUI(
                    video.value,
                    visible.value,
                    isPlaying.value,
                    duration.longValue,
                    currentTime.longValue,
                    bufferPercentage.longValue,
                {
                    videoViewModel.playOrPause(it)
                },
                {
                    videoViewModel.onFastRewind()
                },
                {
                    videoViewModel.onFastForward()
                },
                {
                    videoViewModel.onSeekChange(it)
                },
                {
                    videoViewModel.convertHHMMSS(it)
                },
              modifier = modifier
            )
        }
    }
}