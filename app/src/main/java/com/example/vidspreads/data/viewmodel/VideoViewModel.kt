package com.example.vidspreads.data.viewmodel

import android.content.pm.ActivityInfo
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vidspreads.data.datasource.AlbumDataSource
import com.example.vidspreads.data.datasource.VideoDataSource
import com.example.vidspreads.data.model.AlbumData
import com.example.vidspreads.data.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class VideoViewModel (var player: Player, private val videoDataSource: VideoDataSource,private val albumDataSource: AlbumDataSource):ViewModel() {

    lateinit var images: Flow<PagingData<Video>>
    fun getVideos(bucketId: String,count:Int) {
        images= videoDataSource.getVideos(bucketId,count)
            .cachedIn(viewModelScope)
    }

    fun getAlbumData(){
       viewModelScope.launch {
            listOfAlbums.value= albumDataSource.getAlbumData()
       }
    }

    var orientation = MutableStateFlow(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT)
    private set

    private var job: Job?=null

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val listOfAlbums = mutableStateOf(emptyList<AlbumData>())

    var currentVideo= mutableStateOf(Video("","", MutableStateFlow(null),0,0,0))
        private set(value) {
            duration.longValue = 0L
            isPlaying.value = false
            currentTime.longValue=0L
            bufferPercentage.longValue=0L
            job?.cancel()
            field = value
        }

    var duration= mutableLongStateOf(0L)

    var isPlaying= mutableStateOf(false)
        private set

    var currentTime = mutableLongStateOf(0L)
        private set

    var bufferPercentage= mutableLongStateOf(0L)
        private set

    fun setVideo(video: Video){
        currentVideo.value=video
        setUpPlayerForVideo(video)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        player.pause()
        player.clearMediaItems()
        player.release()
    }

    fun onFastForward(){
        player.seekForward()
        currentTime.longValue= player.currentPosition
    }

    fun onFastRewind(){
        player.seekBack()
        currentTime.longValue=player.currentPosition
    }

    fun playOrPause(play:Boolean){
        Log.e("Player",if(play) "playing" else "pause")
        if(play)
            player.play()
        else
            player.pause()
        isPlaying.value=play
    }

    fun onSeekChange(to:Float){
        player.seekTo(to.toLong())
        currentTime.longValue=player.currentPosition
    }

    private fun setUpPlayerForVideo(video: Video){
        val hlsUri=video.videoUrl
        try {
            viewModelScope.launch {
                player.clearMediaItems()
                if (player.mediaItemCount == 0) {
                    player.setMediaItem(MediaItem.fromUri(hlsUri))
                    attachCounter()
                    start()
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun start(){
        player.prepare()
        player.play()
        isPlaying.value= true
    }

    private fun attachCounter(){
        job=coroutineScope.launch {
            while (true) {
                duration.longValue = player.duration.coerceAtLeast(0L)
                currentTime.longValue = player.currentPosition.coerceAtLeast(0L)
                bufferPercentage.longValue = player.bufferedPercentage.toLong()
                delay(1000)
            }
        }
    }

    fun convertHHMMSS(time:Long):String{
        val hr=time/3600000
        val min=time/60000
        val sec=(time%60000)/1000
        return (if(hr<=0)"" else if (hr<10) "0${hr}:" else "${hr}:") +
                (if(min<=0)"00:" else if (min<10) "0${min}:" else "${min}:") +
                (if(sec<=0) "00" else if (sec<10) "0${sec}" else "$sec")
    }

//    fun play(){player.play()}

//    fun pause(){player.pause()}

//    fun seekTo(to: Long){player.seekTo(to)}

    fun changeOrientation(flag: Boolean) {
        viewModelScope.launch {
            if (flag) {
                orientation.value = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                orientation.value = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            }
        }
    }


}