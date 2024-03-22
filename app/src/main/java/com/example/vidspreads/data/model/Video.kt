package com.example.vidspreads.data.model

import android.graphics.Bitmap
import kotlinx.coroutines.flow.MutableStateFlow

data class Video(
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: MutableStateFlow<Bitmap?>,
    val size: Long,
    val dateCreated:Long,
    val duration:Long
)