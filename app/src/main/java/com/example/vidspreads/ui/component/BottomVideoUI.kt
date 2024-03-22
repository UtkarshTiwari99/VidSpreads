package com.example.vidspreads.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidspreads.data.model.Video

@Composable
fun BottomVideoUI(video: Video, modifier: Modifier){
    Column (modifier =  modifier.padding(horizontal = 8.dp)){
        Text(text = video.title, color = Color.White, fontSize =22.sp, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(bottom = 2.dp))
        Text(text = "${video.size}", fontWeight = FontWeight.Medium, fontSize = 13.sp)
        Row (modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Row (verticalAlignment = Alignment.CenterVertically){
                Icon(
                    Icons.Rounded.SelfImprovement, contentDescription = "Channel Logo",
                    Modifier
                        .scale(1.2f)
                        .padding(start = 4.dp, end = 12.dp)
                        .clip(CircleShape)
                        .background(Color.White), Color.Black
                )
                Text(
                    text = "Channel",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = {}, modifier = Modifier
                    .padding(end = 10.dp)
                    .background(
                        Color.Transparent
                    )
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text(text = "Subscribe", color = Color.Black)
            }
        }
    }
}