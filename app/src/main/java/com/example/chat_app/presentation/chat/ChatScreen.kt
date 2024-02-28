package com.example.chat_app.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chat_app.R
import com.example.chat_app.component.RoundImage
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing


@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {

    Scaffold(
        bottomBar = { SendMessageBottomBar()}
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(MaterialTheme.spacing.small)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))

                RoundImage(
                    modifier = Modifier.size(MaterialTheme.sizes.smallRoundImageSize),
                    imageUri = ""
                )

                Spacer(modifier = Modifier.padding(start = MaterialTheme.spacing.small))

                Text(
                    text = "",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.fontSize.title,
                    fontWeight = FontWeight.SemiBold
                )
            }

            LazyColumn() {}
        }
    }
}

@Composable
fun SendMessageBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = MaterialTheme.spacing.medium)
    ) {
        Icon(
            modifier = Modifier.padding(start = MaterialTheme.spacing.small),
            painter = painterResource(id = R.drawable.image),
            contentDescription = stringResource(R.string.add_image)
        )
        TextField(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.extraSmall)
                .weight(1f, true),
            value = "", onValueChange = {})

        Icon(
            modifier = Modifier.padding(end = MaterialTheme.spacing.small),
            imageVector = Icons.Default.Send,
            contentDescription = stringResource(id = R.string.send_message)
        )
    }
}