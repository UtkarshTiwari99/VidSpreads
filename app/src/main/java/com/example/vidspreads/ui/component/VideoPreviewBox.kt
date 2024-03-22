package com.example.vidspreads.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun VideoPreviewBox(thumbnailBitmap: State<Bitmap?>, modifier: Modifier){
    Log.e("player", (thumbnailBitmap).toString())
    Image(thumbnailBitmap.value?.asImageBitmap()?: ImageBitmap(10, 10),"",
        modifier = modifier, contentScale = ContentScale.FillBounds
    )
}