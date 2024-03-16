package com.example.vidspreads.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.vidspreads.data.model.AlbumData

@Composable
fun ListOfAlbum(list: List<AlbumData>,rowLength:Int, modifier: Modifier, onVideoClick: (albumData: AlbumData) -> Unit){
    val totalWidth = (4*LocalConfiguration.current.screenWidthDp.dp)/(5*rowLength)
    LazyVerticalGrid(columns = GridCells.Fixed(rowLength), verticalArrangement = Arrangement.SpaceBetween, modifier = modifier){
        items(list) {
            AlbumItem(it,totalWidth,Modifier.padding(horizontal = totalWidth/8),onVideoClick)
        }
    }
}