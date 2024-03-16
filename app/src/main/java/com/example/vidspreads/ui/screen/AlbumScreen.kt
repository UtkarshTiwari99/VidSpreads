package com.example.vidspreads.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vidspreads.data.model.Video
import com.example.vidspreads.ui.component.ListOfVideo

@Composable
fun AlbumScreen(videos:List<Video>,onClick:(video:Video)->Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ListOfVideo(videos, Modifier.fillMaxWidth()) {
            onClick(it)
        }
    }
}