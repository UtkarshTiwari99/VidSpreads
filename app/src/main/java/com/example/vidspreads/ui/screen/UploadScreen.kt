package com.example.vidspreads.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.VideoFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vidspreads.ui.component.TextBox
import com.example.vidspreads.ui.component.VideoPreviewBox

@Composable
fun UploadScreen(modifier: Modifier, navController: NavHostController){

    Column (modifier=modifier.fillMaxSize()){
        Box {
//            VideoPreviewBox(
//                thumbnailBitmap = ,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Black)
//                    .padding(vertical = 4.dp)
//                    .height(190.dp)
//                    .align(Alignment.Center)
//            )

            IconButton(
                onClick = {},
                modifier = Modifier.padding(top=18.dp, start = 14.dp),
                content = {
                    Icon(
                        Icons.Rounded.VideoFile, "", modifier = Modifier
                        .scale(2f, 2f)
                        .align(Alignment.TopStart), Color.Blue.copy(0.99f))
                }
            )

        }

        Row (modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)){

            Icon(
                Icons.Rounded.CheckCircle, "", modifier = Modifier
                    .scale(1.49f, 1.49f)
                    .padding(14.dp, 10.dp, 12.dp, 10.dp)
                    .align(Alignment.CenterVertically), Color.White
            )

            Text(
                text = "title",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                modifier= Modifier.align(Alignment.CenterVertically)
            )

        }

        TextBox(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Row {
                Icon(
                    Icons.Rounded.AutoAwesome, "", modifier = Modifier, Color.White
                )
                Text(
                    "Add description",
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Icon(Icons.Rounded.ArrowForwardIos, "", modifier = Modifier, Color.White)
        }

    }
}