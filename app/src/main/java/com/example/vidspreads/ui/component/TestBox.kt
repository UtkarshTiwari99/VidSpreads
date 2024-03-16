package com.example.vidspreads.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(modifier: Modifier){
    Column{
        var text= remember {
            mutableStateOf("")
        }
        Box(modifier = Modifier
            .drawBehind {
                drawLine(
                    color = Color.Gray.copy(0.99f),
                    start = Offset(0f, -1f),
                    end = Offset(size.width, -1f),
                    strokeWidth = 1f
                )
                drawLine(
                    color = Color.Gray.copy(0.99f),
                    start = Offset(0f, size.height+1),
                    end = Offset(size.width, size.height+1),
                    strokeWidth = 1f
                )

            }
        ){
            var isLabel= remember {
                mutableStateOf(false)
            }
            TextField(
                value = text.value,
                onValueChange ={text.value=it},
                modifier = modifier
                    .onFocusChanged { isLabel.value = it.hasFocus } ,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black.copy(0.18f),
                    unfocusedContainerColor = Color.Black.copy(0.18f),
                    disabledContainerColor = Color.Black.copy(0.18f),
                    focusedIndicatorColor = Color.Black.copy(0.18f),
                    unfocusedIndicatorColor = Color.Black.copy(0.18f),
                ),
                label = {if(isLabel.value) Text("Title",color= Color.White) else Text("Create Title",color= Color.Gray) },
                placeholder = {
                    Text("Create Title",color= Color.Gray)
                },
                maxLines = 1
            )
        }
    }
}