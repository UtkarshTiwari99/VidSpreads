package com.example.vidspreads.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.vidspreads.data.model.Video


@Composable
fun ListOfVideo(videos: LazyPagingItems<Video>, modifier: Modifier, onVideoClick: (video: Video) -> Unit){
    println("list$videos");
    LazyColumn(modifier=modifier) {
        items(videos) { video ->
            if (video != null) {
                VideoItem(video = video, modifier = Modifier.fillMaxWidth(), onVideoClick)
            }
        }
    }
}

fun <T: Any> androidx.compose.foundation.lazy.LazyListScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {

    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }

}