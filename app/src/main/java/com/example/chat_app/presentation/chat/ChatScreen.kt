package com.example.chat_app.presentation.chat

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.chat_app.R
import com.example.chat_app.component.RoundImage
import com.example.chat_app.domain.model.Message
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing


@Composable
fun ChatScreen(
    navController: NavController,
    chatId: String?,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val messages = viewModel.pager.collectAsLazyPagingItems()

    Scaffold(
        topBar = { ChatScreenTopBar(dispatcher) },
        bottomBar = { SendMessageBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {

            MessagesList(messages)

        }
    }
}


@Composable
fun ChatScreenTopBar(dispatcher: OnBackPressedDispatcher) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(MaterialTheme.spacing.small)
    ) {
        Icon(
            modifier = Modifier
                .clickable { dispatcher.onBackPressed() },
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
}

@Composable
fun MessagesList(messages: LazyPagingItems<Message>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        items(
            count = messages.itemCount,
            key = messages.itemKey { it.id }
        ) { index ->

            val message = messages[index]

            message?.let {
                MessageComponent(it)
            }

        }

    }

}

@Composable
fun MessageComponent(message: Message) {

    Text(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(MaterialTheme.spacing.extraSmall),
        text = message.messageText,
        fontWeight = FontWeight.Normal,
        fontSize = MaterialTheme.fontSize.primary
    )

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