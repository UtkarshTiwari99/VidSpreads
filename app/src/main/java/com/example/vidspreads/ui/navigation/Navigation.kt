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
                    try {
                        navController.navigate(Screen.AlbumScreen.route +"/"+ it.id+"/"+it.imageCount)
                    }catch (e:Exception){
                        Log.e("player nav",e.message.toString())
                    }
                    Log.e("player nav",it.toString())
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
        composable(route = Screen.AlbumScreen.route+"/{albumId}/{size}"){ navBackStack ->
            val albumId = navBackStack.arguments?.getString("albumId")
            val size = navBackStack.arguments?.getString("size")
            videoVideoModel.getVideos(albumId?:"",size?.toInt()?:0)
            AlbumScreen(videoVideoModel.images){
                if (it.title != videoVideoModel.currentVideo.value.title) {
                        videoVideoModel.setVideo(it)
                }
                navController.navigate(Screen.VideoScreen.route)
            }
        }
    }
}