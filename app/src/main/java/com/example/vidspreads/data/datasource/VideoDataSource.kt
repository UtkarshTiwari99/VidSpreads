package com.example.vidspreads.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vidspreads.data.model.Video
import com.example.vidspreads.data.paging.NETWORK_PAGE_SIZE
import com.example.vidspreads.data.paging.VideoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoDataSource @Inject constructor(
    private val pagingSource: VideoPagingSource
) {
    fun getVideos(bucketId: String, count: Int): Flow<PagingData<Video>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                val pagingSource= pagingSource
                pagingSource.bucketId=bucketId
                pagingSource.count =count
                pagingSource
            }
        ).flow
    }
}