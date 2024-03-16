package com.example.vidspreads.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vidspreads.data.model.Video

@Composable
fun ListOfVideo(videos: List<Video>, modifier: Modifier, onVideoClick: (video: Video) -> Unit){
    println("list$videos");
    LazyColumn(modifier=modifier) {
        items(videos) { video ->
            VideoItem(video = video,modifier= Modifier.fillMaxWidth(),onVideoClick)
        }
    }
}