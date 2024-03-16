package com.example.vidspreads.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(modifier: Modifier) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(modifier = modifier.fillMaxWidth(),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search", color = Color.White)
        },
        leadingIcon = {
            Row {
                Icon(Icons.Default.Search, contentDescription = "Search")
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .background(Color.White)
                        .border(2.dp, Color.White)
                )
            }
        }
    ) {
    }
}