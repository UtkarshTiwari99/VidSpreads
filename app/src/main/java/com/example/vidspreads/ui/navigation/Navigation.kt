package com.example.vidspreads.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vidspreads.data.viewmodel.VideoViewModel
import com.example.vidspreads.ui.screen.HomeScreen
import com.example.vidspreads.ui.screen.UploadScreen
import com.example.vidspreads.ui.screen.VideoScreen
import android.util.Log
import com.example.vidspreads.ui.screen.AlbumScreen

@Composable
fun Navigation( videoVideoModel: VideoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route =Screen.MainScreen.route) {
            Log.e("player data", videoVideoModel.listOfAlbums.value.toString())
            HomeScreen(navController = navController,
                name = "VidStream", listOfAlbumData = videoVideoModel.listOfAlbums.value, onClick = {

                }
            )
        }
        composable(route = Screen.VideoScreen.route){
            VideoScreen(navController =navController,videoViewModel = videoVideoModel)
        }
        composable(route = Screen.UploadScreen.route){
            UploadScreen(navController=navController ,modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.22f)))
        }
        composable(route = Screen.AlbumScreen.route){
            AlbumScreen(videoVideoModel.videoList.value){
                if (it.title != videoVideoModel.currentVideo.value.title) {
                        videoVideoModel.setVideo(it)
                    }
                    navController.navigate(Screen.VideoScreen.route)
            }
        }
    }
}