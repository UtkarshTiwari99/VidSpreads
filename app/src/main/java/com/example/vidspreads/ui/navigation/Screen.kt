package com.example.vidspreads.ui.navigation

sealed class Screen (val route:String){
    data object VideoScreen: Screen("video_screen")
    data object MainScreen: Screen("main_screen")
    data object UploadScreen :Screen("upload_screen")
    data object AlbumScreen :Screen("album_screen")
}