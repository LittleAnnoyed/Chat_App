package com.example.chat_app.presentation.find

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.chat_app.R
import com.example.chat_app.component.RoundImage
import com.example.chat_app.domain.user.UserListItem
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing

@Composable
fun FindUserScreen(
    navController: NavController,
    viewModel: FindUserViewModel = hiltViewModel()
) {

    val users: LazyPagingItems<UserListItem> = viewModel.pager.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(vertical = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.find_user),
                fontSize = MaterialTheme.fontSize.title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

        }

        Column {

            SearchBar()

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            UsersList(users)
        }
    }

}

@Composable
fun SearchBar() {

    //Todo add content desc
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    )

}

@Composable
fun UsersList(users: LazyPagingItems<UserListItem>) {

    LazyColumn(Modifier.fillMaxSize()) {
        items(
            count = users.itemCount,
            key = users.itemKey { it.id }
        ) { index ->

            val user = users[index]

            user?.let {
                UserListItemComponent(user)
            }

        }
    }
}

//todo add content desc
@Composable
fun UserListItemComponent(user: UserListItem) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {

        RoundImage(
            modifier = Modifier
                .size(MaterialTheme.sizes.smallRoundImageSize),
            imageUri = user.userImageUri
        )

        Spacer(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))

        Text(
            text = user.name,
            fontSize = MaterialTheme.fontSize.primary,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}