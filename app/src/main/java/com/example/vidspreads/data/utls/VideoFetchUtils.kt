package com.example.vidspreads.data.utls

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.example.vidspreads.data.model.AlbumData
import android.util.Log
import com.example.vidspreads.data.model.Video
import com.example.vidspreads.data.paging.NETWORK_PAGE_SIZE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoFetchUtils @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    private var contentResolver: ContentResolver = applicationContext.contentResolver

    suspend fun fetchAlbums(): List<AlbumData> {
        Log.e("players album","album")
        val albums = mutableListOf<AlbumData>()
        val albumSet = mutableSetOf<String>()
        val albumMap = mutableMapOf<String, Pair<Pair<String, Uri>, Pair<String, Int>>>()
        try {
            val projection = arrayOf(
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME
            )
            val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

            val cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder
            )

            cursor?.use {
                Log.e("player VideoUtils",it.count.toString() + " " + it.moveToNext())
                while (it.moveToNext()) {
                    var videoCount = 0
                    val folderId =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID))
                    if (!albumSet.contains(folderId)) {
                        albumSet.add(folderId)
                        videoCount = 0
                    }
                    val albumName =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val videoUriString =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    val thumbnailUri = Uri.parse("file://$videoUriString")
                    if (albumMap.contains(folderId))
                        videoCount = albumMap[folderId]?.second?.second ?: 0
                    albumMap[folderId] =
                        Pair(Pair(folderId, thumbnailUri), Pair(albumName, videoCount + 1))
                }
            }
            cursor?.close()
        } catch (e: Exception) {
            Log.e("player error", e.message.toString())
        }
        albumMap.forEach { (key, data) ->
            val thumbnailOfVideo = MutableStateFlow<Bitmap?>(null)
            CoroutineScope(Dispatchers.IO).launch {
                thumbnailOfVideo.value = getThumbnailOfVideo(data.first.second)
            }
            albums.add(AlbumData(data.first.first, thumbnailOfVideo, data.second.first, data.second.second))
        }
        return albums
    }

    private suspend fun getThumbnailOfVideo(videoUri:Uri): Bitmap? {
//        val mediaMetadataRetriever = MediaMetadataRetriever()
//        mediaMetadataRetriever.setDataSource(thumbnailUri.path)
//        return mediaMetadataRetriever.getFrameAtTime(10)
       return ThumbnailUtils.createVideoThumbnail(videoUri.path?:"",MediaStore.Video.Thumbnails.FULL_SCREEN_KIND)
    }

    @SuppressLint("SuspiciousIndentation")
    fun findVideosByAlbumId(albumId: String, page:Int): List<Video> {
        Log.e("players videos", "$albumId $page")
        val videos: MutableList<Video> = mutableListOf()

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_TAKEN,
            OpenableColumns.SIZE
        )

        val selection = "${MediaStore.Video.Media.BUCKET_ID} = ?"
        val selectionArgs = arrayOf(albumId)

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.use {
            var items=0
            it.move((page* NETWORK_PAGE_SIZE))
            while (it.moveToNext()) {
                val videoId =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val videoUriString =
                    it.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                val videoUriPath= "file://$videoUriString"
                val videoUri = Uri.parse(videoUriPath)
                val videoName =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                val thumbnailOfVideo = MutableStateFlow<Bitmap?>(null)
                    CoroutineScope(Dispatchers.Default).launch {
                        thumbnailOfVideo.value = getThumbnailOfVideo(videoUri)
                    }
                val dateAddedIndex = it.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN)
                val dateCreated = it.getLong(dateAddedIndex)
                val durationIndex = it.getColumnIndex(MediaStore.Video.Media.DURATION)
                val duration = it.getLong(durationIndex)
                val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                val size = it.getLong(sizeIndex)
                videos.add(Video(videoName,videoUriPath,thumbnailOfVideo,size,dateCreated,duration))
                items++
                if(items>= NETWORK_PAGE_SIZE)
                    break
            }
        }
        cursor?.close()
        Log.e("players videos",videos.size.toString())
        return videos
    }

}