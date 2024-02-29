package com.example.chat_app.presentation.group.create

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chat_app.R
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing
import com.example.chat_app.util.Constants

@Composable
fun CreateGroupScreen(
    navController: NavController,
    viewModel: CreateGroupViewModel
) {

    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val contentResolver = activity.contentResolver
    val dataType = contentResolver.getType(viewModel.state.groupImageUri)

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (dataType == Constants.IMAGE_JPEG_TYPE)
            uri?.let {
                viewModel.state = viewModel.state.copy(groupImageUri = uri)
            }
    }

    Scaffold(topBar = { CreateGroupTopBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                modifier = Modifier
                    .clickable {
                        requestImagePermission(context, activity)
                        pickMedia
                            .launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                model = viewModel.state.groupImageUri,
                contentDescription = stringResource(id = R.string.group_image)
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            TextField(
                value = "",
                onValueChange = {},
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

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(id = R.string.create_group))
            }
        }
    }

}

@Composable
fun CreateGroupTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.sizes.toolbarHeight)
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(MaterialTheme.spacing.small)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

        Text(text = stringResource(R.string.create_group))
    }
}

private fun requestImagePermission(context: Context, activity: Activity) {
    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
    if (!hasPermission) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            0
        )
    }
}