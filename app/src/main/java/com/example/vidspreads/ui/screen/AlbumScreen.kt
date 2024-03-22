package com.example.vidspreads.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vidspreads.data.model.Video
import com.example.vidspreads.data.model.VideoData
import com.example.vidspreads.ui.component.ListOfVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AlbumScreen(videos: Flow<PagingData<Video>>, onClick:(video:Video)->Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ListOfVideo(videos.collectAsLazyPagingItems(), Modifier.fillMaxWidth()) {
            onClick(it)
        }
    }
}