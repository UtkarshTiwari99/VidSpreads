package com.example.vidspreads.ui.screen;

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.vidspreads.ui.component.VideoUI
import com.example.vidspreads.data.viewmodel.VideoViewModel

@Composable
fun VideoScreen(
        videoViewModel: VideoViewModel,
        navController: NavHostController
) {
        val modifier = Modifier
                .fillMaxSize()
        VideoUI(
                modifier, videoViewModel
        )
}