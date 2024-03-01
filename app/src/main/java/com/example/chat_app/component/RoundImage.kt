package com.example.chat_app.component

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

//Todo add contentDescription
@Composable
fun RoundImage(modifier: Modifier, imageUri: String) {
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape),
        model = imageUri,
        contentDescription = null
    )
}

@Composable
fun RoundImage(modifier: Modifier, imageUri: Uri) {
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape),
        model = imageUri,
        contentDescription = null
    )
}