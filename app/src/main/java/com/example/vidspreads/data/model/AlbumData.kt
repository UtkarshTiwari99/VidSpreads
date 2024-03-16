package com.example.vidspreads.data.model

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Video.Thumbnails

data class AlbumData(
    val id: String,
    val videoThumbnails : Bitmap?,
    val albumName: String,
    val imageCount:Int,
)