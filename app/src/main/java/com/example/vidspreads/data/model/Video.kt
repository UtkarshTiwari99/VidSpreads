package com.example.vidspreads.data.model

import android.graphics.Bitmap

data class Video(
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: Bitmap?,
    val channel: String,
    val views: Int
)