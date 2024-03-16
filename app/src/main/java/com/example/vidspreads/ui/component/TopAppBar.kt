package com.example.vidspreads.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidspreads.ui.component.HomeSearchBar

@Composable
fun TopAppBar(modifier: Modifier){

    Column(modifier=modifier) {
        Row(modifier = modifier
            .padding(12.dp, 20.dp, 12.dp, 14.dp), Arrangement.SpaceBetween){
            Row(verticalAlignment =  Alignment.CenterVertically) {
                Icon(
                    Icons.Rounded.AccountCircle,"as",
                    Modifier
                        .padding(0.dp, 0.dp, 2.dp, 0.dp)
                        .scale(1.4f, 1.28f), Color.Red)
                Text("VidSpreads", Modifier.padding(4.dp,0.dp), Color.White, 20.sp , FontStyle.Italic)
            }
            Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", Modifier.scale(1.2f,1f), Color.White)
        }
        HomeSearchBar(modifier= Modifier.padding(start = 10.dp,end = 10.dp, bottom = 30.dp))
    }
}