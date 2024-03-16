package com.example.vidspreads.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun VideoPreviewBox(thumbnailBitmap: Bitmap?, modifier: Modifier){
    Image(thumbnailBitmap?.asImageBitmap()?:ImageBitmap(10, 10),"",
        modifier = modifier, contentScale = ContentScale.FillBounds
    )
}