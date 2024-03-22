package com.example.vidspreads.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidspreads.data.model.Video
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun VideoItem(video: Video, modifier: Modifier, onVideoClick: (video: Video) -> Unit){
//    Spacer(modifier = modifier.height(8.dp))
    Column (Modifier.clickable { onVideoClick(video) }){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) {
            VideoPreviewBox(
                video.thumbnailUrl.collectAsState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp, 0.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Blue)
            )
            Text(
                text = convertHHMMSS(video.duration), color = Color.White, fontSize = 13.sp, modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(14.dp, 8.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(Color.Black)
                    .padding(2.4.dp, 2.dp)
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, 4.9.dp, 0.dp, 0.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                Icons.Rounded.CheckCircle, "", modifier = modifier
                    .weight(0.18f)
                    .scale(1.8f, 1.8f)
                    .alignByBaseline()
                    .padding(14.dp, 10.dp, 14.dp, 10.dp), Color.White
            )

            Column(modifier = Modifier.weight(0.9f)) {

                Text(
                    text = video.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(0.dp, 1.dp, 0.dp, 0.dp)
                ) {

//                    Text(
//                        text = "Channel",
//                        color = Color.White,
//                        fontSize = 13.sp,
//                        modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
//                        fontFamily = FontFamily.SansSerif
//                    )

                    Text(
                        text = convertVideoSize(video.size),
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
                        fontFamily = FontFamily.SansSerif
                    )

                    Text(
                        text = convertLongToDateString(video.dateCreated),
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(
                            0.dp,
                            0.dp,
                            10.dp,
                            0.dp,
                        ),
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
//            Icon(Icons.Rounded.Notifications,"",modifier.fillMaxWidth(), Color.White)
        }
    }
    Spacer(modifier = modifier.height(10.dp))
}

@Composable
fun convertHHMMSS(time:Long):String{
    val hr=time/3600000
    val min=(time%3600000)/60000
    val sec=((time%3600000)%60000)/1000
    return (if(hr<=0)"" else if (hr<10) "0${hr}:" else "${hr}:") +
            (if(min<=0)"00:" else if (min<10) "0${min}:" else "${min}:") +
            (if(sec<=0) "00" else if (sec<10) "0${sec}" else "$sec")
}

@Composable
fun convertVideoSize(size:Long):String{
    val gb=size/1073741824
    val mb=(size%1073741824)/1048576
    val kb=((size%1073741824)%1048576)/1024
    return (if(gb<=0)"" else "${gb}GB ") +
            (if(mb<=0)"" else "${mb}MB ") +
            (if(kb<=0) "" else "${kb}KB")
}

fun convertLongToDateString(dateCreatedInMillis: Long): String {
    val dateFormat = SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault())
    val date = Date(dateCreatedInMillis)
    val monthNumber =date.month
    val dateString=dateFormat.format(date)
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, monthNumber - 1)
    val month=SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
    return dateString.substring(0,3)+month+dateString.substring(5,dateString.length)
}