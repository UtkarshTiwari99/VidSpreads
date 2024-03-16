package com.example.vidspreads.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidspreads.data.model.AlbumData

@Composable
fun AlbumItem(
    albumData: AlbumData,
    totalWidth: Dp,
    modifier: Modifier,
    onClick: (albumData: AlbumData) -> Unit
) {
    Column(modifier.clickable { onClick(albumData) }) {
        Column (Modifier.width(IntrinsicSize.Min)){
        VideoPreviewBox(
            albumData.videoThumbnails,
            modifier = Modifier.size(totalWidth)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Blue)
                .border(0.4.dp, Color.White, RoundedCornerShape(18.dp))
        )
        Text(
            text = albumData.albumName,
            color = Color.White,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontFamily = FontFamily.SansSerif,
            modifier= Modifier.padding(top = 4.dp)
        )
        }

        Text(
            text = albumData.imageCount.toString(),
            color = Color.White,
            fontSize = 13.sp,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = modifier.height(10.dp))
    }
}