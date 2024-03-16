package com.example.vidspreads.data.utls

import android.content.ContentResolver
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.example.vidspreads.data.model.AlbumData
import android.util.Log

class VideoFetchUtils {

    fun fetchAlbums(contentResolver: ContentResolver): List<AlbumData> {
        val albums = mutableListOf<AlbumData>()
        val albumSet = mutableSetOf<String>()
        val albumMap = mutableMapOf<String, Triple<String,String, Int>>()
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

            var i=0

            cursor?.use { cursor ->
                Log.e("player",cursor.count.toString()+" "+cursor.moveToNext())
                while (cursor.moveToNext()) {
                    var videoCount=0
                    val folderId =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID))
                    if (!albumSet.contains(folderId)) {
                        albumSet.add(folderId)
                        videoCount=0
                    }
                    val albumName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    if(albumMap.contains(folderId))
                        videoCount=albumMap[folderId]?.third?:0
                    albumMap[folderId] = Triple(folderId,albumName,videoCount+1)
                }
            }
            cursor?.close()
        } catch (e: Exception) {
            Log.e("player error", e.message.toString())
        }
        albumMap.forEach { (folderId, pair) ->
            val thumbNail = getAlbumThumbnail(contentResolver, bucketId = folderId)
            val mediaMetadataRetriever= MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(thumbNail.path);
            val bitmap = mediaMetadataRetriever.getFrameAtTime(10)
            albums.add(AlbumData(folderId, bitmap, pair.second, pair.third))
        }
        albums.stream().limit(5).forEach {
            Log.e("player",it.albumName+" "+it.imageCount)
        }
        return albums
    }

    private fun getAlbumThumbnail(contentResolver: ContentResolver, bucketId: String): Uri {
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA
        )
        val selection = "${MediaStore.Video.Media.BUCKET_ID} = ?"
        val selectionArgs = arrayOf(bucketId)
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val thumbnailUriString =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                return Uri.parse("file://$thumbnailUriString")
            }
        }
        return Uri.EMPTY
    }
}