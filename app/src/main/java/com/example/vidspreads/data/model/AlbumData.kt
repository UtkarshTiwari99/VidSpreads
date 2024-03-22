package com.example.vidspreads.data.model

import android.graphics.Bitmap
import kotlinx.coroutines.flow.MutableStateFlow

data class AlbumData(
    val id: String,
    val videoThumbnails: MutableStateFlow<Bitmap?>,
    val albumName: String,
    val imageCount:Int,
)