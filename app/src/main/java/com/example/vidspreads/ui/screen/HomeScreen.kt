package com.example.vidspreads.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vidspreads.data.model.AlbumData
import com.example.vidspreads.ui.component.ListOfAlbum

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(
    navController: NavController,
    name: String,
    listOfAlbumData: List<AlbumData>,
    onClick: (albumData: AlbumData) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ListOfAlbum(listOfAlbumData,3, Modifier.fillMaxWidth().padding(top = 20.dp), onClick)
    }
}