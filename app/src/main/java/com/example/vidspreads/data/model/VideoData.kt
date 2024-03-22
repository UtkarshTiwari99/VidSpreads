package com.example.vidspreads.data.model

import android.graphics.Bitmap
import android.net.Uri

data class VideoData(
    val videoId:String,
    val title: String,
    val videoUrl: Uri,
    val thumbnailUrl: Bitmap?,
)