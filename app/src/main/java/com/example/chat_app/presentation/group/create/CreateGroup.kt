package com.example.chat_app.presentation.group.create

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.chat_app.R
import com.example.chat_app.component.RoundImage
import com.example.chat_app.domain.user.UserListItem
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing
import com.example.chat_app.util.pickMediaLauncher
import com.example.chat_app.util.requestImagePermission


@Composable
fun GroupCreateScreen(
    navController: NavController,
    viewModel: GroupCreateViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val contentResolver = activity.contentResolver
    val dataType = contentResolver.getType(viewModel.state.groupImageUri)

    val pickMedia = pickMediaLauncher(
        dataType = dataType,
        setImageUri = { viewModel.onEvent(GroupCreateEvent.OnGroupCreateImageChanged(it)) })

    Scaffold(
        topBar = { ToGroupTopBar(viewModel) },
        floatingActionButton = { ToGroupFab(viewModel, navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val users = viewModel.pager.collectAsLazyPagingItems()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                items(
                    count = users.itemCount,
                    key = users.itemKey { it.id }
                ) { index ->

                    val user = users[index]

                    user?.let {
                        UserAddComponent(user = it, viewModel = viewModel)
                    }

                }
            }

            if (viewModel.state.createGroupAlertDialog) {
                CreateGroupDialog(viewModel, pickMedia, context, activity)
            }

        }
    }

}

@Composable
fun ToGroupTopBar(viewModel: GroupCreateViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(id = R.string.search_bar_text),
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_bar_text)
                )
            },
            value = viewModel.state.keyword,
            onValueChange = { viewModel.onEvent(GroupCreateEvent.OnSearchBarTextChanged(it)) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        )
    }
}

@Composable
fun UserAddComponent(user: UserListItem, viewModel: GroupCreateViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {
        RoundImage(
            modifier = Modifier
                .size(MaterialTheme.sizes.smallRoundImageSize)
                .weight(2f),
            imageUri = user.userImageUri ?: "",
            contentDescription = stringResource(id = R.string.user_image)
        )

        Spacer(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.small)
        )

        Text(
            modifier = Modifier.weight(2f),
            text = user.name,
            fontSize = MaterialTheme.fontSize.itemTitle
        )

        Spacer(modifier = Modifier.padding(start = MaterialTheme.spacing.small))

        Checkbox(
            modifier = Modifier.weight(1f),
            checked = viewModel.state.isChosen,
            onCheckedChange = {
                viewModel.onEvent(GroupCreateEvent.ChangeCheckBoxValue(it))
                if (viewModel.state.isChosen) {
                    viewModel.onEvent(GroupCreateEvent.RemoveFromGroupCreate(user.id))
                } else {
                    viewModel.onEvent(GroupCreateEvent.AddGroupCreate(user.id))
                }
            })
    }
}

@Composable
fun ToGroupFab(viewModel: GroupCreateViewModel, navController: NavController) {
    FloatingActionButton(
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        onClick = { viewModel.onEvent(GroupCreateEvent.CreateGroupCreate) }) {
        Icon(
            modifier = Modifier
                .clickable {
                    viewModel.onEvent(GroupCreateEvent.OpenCreateGroupDialogCreate)
                },
            imageVector = Icons.Default.Create,
            contentDescription = stringResource(id = R.string.group_create)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupDialog(
    viewModel: GroupCreateViewModel,
    pickMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    context: Context,
    activity: Activity
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxSize(0.8f),
        onDismissRequest = { viewModel.onEvent(GroupCreateEvent.CloseCreateGroupDialogCreate) }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            RoundImage(
                modifier = Modifier
                    .size(MaterialTheme.sizes.bigRoundImageSize)
                    .clickable {
                        requestImagePermission(context, activity)
                        pickMedia
                            .launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                imageUri = viewModel.state.groupImageUri,
                contentDescription = stringResource(id = R.string.group_image)
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            TextField(
                value = viewModel.state.groupName,
                onValueChange = { viewModel.onEvent(GroupCreateEvent.OnGroupCreateNameChanged(it)) },
                label = {
                    Text(
                        text = stringResource(id = R.string.group_name),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.fontSize.itemTitle
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )

            Button(onClick = { viewModel.onEvent(GroupCreateEvent.CreateGroupCreate) }) {
                Text(text = stringResource(id = R.string.group_create))
            }

        }
    }
}