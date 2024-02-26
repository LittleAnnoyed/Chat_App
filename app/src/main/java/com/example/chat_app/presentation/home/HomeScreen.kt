package com.example.chat_app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.chat_app.R
import com.example.chat_app.domain.model.Chat
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val chats = homeViewModel.pager.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        HomeToolbar()

        if (chats.itemCount != 0) {
            UserChats(chats)
        } else {
            Text(
                text = stringResource(id = R.string.empty_chat_list_message),
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.fontSize.secondary,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }


    }

}

@Composable
fun HomeToolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.sizes.toolbarHeight)
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.chats),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = MaterialTheme.fontSize.title,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun UserChats(chats: LazyPagingItems<Chat>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(
            count = chats.itemCount,
            key = chats.itemKey { it.id }
        ) { index ->

            val chat = chats[index]

            chat?.let {
                ChatComponent(it)
            }

        }
    }
}

@Composable
fun ChatComponent(chat: Chat) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
            .shadow(elevation = 4.dp),
    ) {
        RoundImage(
            modifier = Modifier
                .size(MaterialTheme.sizes.smallRoundImageSize),
            chat.chatImageUri
        )

        Spacer(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))

        Text(
            text = chat.name,
            fontSize = MaterialTheme.fontSize.primary,
            color = MaterialTheme.colorScheme.onSecondary
        )

    }

}

//Todo add contentDescription
@Composable
fun RoundImage(modifier: Modifier, chatImageUri: String) {
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape),
        model = chatImageUri,
        contentDescription = null
    )
}