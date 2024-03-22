package com.example.vidspreads.data.datasource

import com.example.vidspreads.data.model.AlbumData
import com.example.vidspreads.data.utls.VideoFetchUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumDataSource @Inject constructor(private val videoFetchUtils: VideoFetchUtils) {
    suspend fun getAlbumData(): List<AlbumData> {
        return videoFetchUtils.fetchAlbums()
    }
}