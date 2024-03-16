package com.example.vidspreads

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.util.Log
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.vidspreads.data.utls.VideoFetchUtils
import com.example.vidspreads.data.viewmodel.VideoViewModel
import com.example.vidspreads.ui.navigation.Navigation
import com.example.vidspreads.ui.theme.VidSpreadsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.vidspreads.ui.component.TopAppBar
import com.google.accompanist.permissions.isGranted
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val videoFetchUtils: VideoFetchUtils = VideoFetchUtils()

    class VideoViewModelFactory(private val player: Player) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VideoViewModel(player) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private lateinit var requiredPermissions: String

    private lateinit var videoViewModel: VideoViewModel

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = VideoViewModelFactory(getExoplayer())
        videoViewModel = ViewModelProvider(this, factory)[VideoViewModel::class.java]
        lifecycleScope.launch {
            videoViewModel.orientation.collect { state ->
                changeOrientation(orientation = state)
            }
        }

        requiredPermissions =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
//            } else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_VIDEO
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        setContent {
            VidSpreadsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val permission = rememberPermissionState(requiredPermissions)
                    val requestPermissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { result ->
                        val isGranted: Boolean = result
                        if (isGranted) {
                            Log.e("player", "Yes Permission Granted")
                        } else {
                            Log.e("player", "Permission Denied")
                            openAppSetting(this)
                        }
                    }
                    val lifecycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(lifecycleOwner) {
                        val observer =
                            LifecycleEventObserver { _, event ->
                                Log.e("player", permission.status.isGranted.toString())
                                if (event == Lifecycle.Event.ON_START) {
                                    if (permission.status.isGranted) {
                                        Log.e("player", "Permission Granted")
                                        videoViewModel.listOfAlbums.value =
                                            videoFetchUtils.fetchAlbums(contentResolver)
                                    } else {
                                        Log.e("player", "Permission Requested")
                                        requestPermissionLauncher.launch(requiredPermissions)
//                                        requestLaunchPermissions(permission)
//                                           permission.launchPermissionRequest()
                                    }
                                }
                            }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }
                    Column (modifier = Modifier.fillMaxSize()
                        .background(Color.Black)){
                        TopAppBar(
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFF2F2c77), Color(0x11232149)),
                                        start = Offset(0f, 80f),
                                        end = Offset(9.8f, Float.POSITIVE_INFINITY)
                                    )
                                )
                        )
                        Navigation(videoViewModel)
                    }
                }
            }
        }
    }


//    @OptIn(ExperimentalPermissionsApi::class)
//    private fun requestLaunchPermissions(permission: PermissionState) {
//        Log.e("player", "Permissions")
//        permission.launchPermissionRequest()
//    }

    private fun openAppSetting(activity: Activity) {
        Toast.makeText(
            activity,
            "Go to Setting and Enable All Permission",
            Toast.LENGTH_LONG
        ).show()

        val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        settingIntent.data = Uri.parse("package:${activity.packageName}")
        activity.startActivityForResult(settingIntent, 1001)

    }

    private fun changeOrientation(orientation: Int) {
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }
    }

    private fun getExoplayer(): Player {
        return ExoPlayer.Builder(application).build()
    }

}

@Preview(showBackground = true)
@Composable
fun MainUIPreview() {
    VidSpreadsTheme {
    }
}