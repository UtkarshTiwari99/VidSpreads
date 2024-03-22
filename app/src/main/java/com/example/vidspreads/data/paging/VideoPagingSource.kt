package com.example.vidspreads.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vidspreads.data.model.Video
import com.example.vidspreads.data.utls.VideoFetchUtils
import javax.inject.Inject
import javax.inject.Singleton

const val NETWORK_PAGE_SIZE = 10
const val STARTING_PAGE_INDEX= 1

@Singleton
class VideoPagingSource @Inject constructor(private val videoService: VideoFetchUtils): PagingSource<Int, Video>() {

    lateinit var bucketId: String

    var count: Int=0
    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        Log.e("plays",params.key.toString())
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        val videos = videoService.findVideosByAlbumId(bucketId, pageIndex-1)
        Log.e("player paging",videos.toString())
        val nextKey =
            if (videos.isEmpty()|| (NETWORK_PAGE_SIZE*pageIndex >= count)) {
                null
            } else {
                pageIndex + 1
            }
        return LoadResult.Page(
            data = videos,
            prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
            nextKey = nextKey
        )
    }
}