package com.example.chat_app.presentation.auth.second

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chat_app.R
import com.example.chat_app.component.RoundImage
import com.example.chat_app.domain.result.SetUserDataResult
import com.example.chat_app.nav.Screen
import com.example.chat_app.ui.sizes
import com.example.chat_app.ui.spacing
import com.example.chat_app.util.pickMediaLauncher
import com.example.chat_app.util.requestImagePermission
import kotlinx.coroutines.flow.collect

@Composable
fun SetUserDataScreen(
    navController: NavController,
    viewModel: SetUserDataViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val contentResolver = activity.contentResolver
    val dataType = contentResolver.getType(viewModel.state.userImageUri)

    val pickMedia = pickMediaLauncher(
        dataType = dataType,
        setImageUri = {viewModel.onEvent(SetUserDataEvent.OnUserImageChanged(it))})

    val unknownError = stringResource(id = R.string.unknown_error)

    LaunchedEffect(viewModel,context){
        viewModel.authResults.collect{ result ->
            when(result) {
                is SetUserDataResult.Correctly -> {
                    navController.navigate(Screen.HomeScreen.route)
                }
                is SetUserDataResult.UnknownError -> {
                    Toast.makeText(context,unknownError,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
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
            imageUri = viewModel.state.userImageUri,
            contentDescription = stringResource(id = R.string.user_image)
        )

        Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

        TextField(
            value = viewModel.state.username,
            onValueChange = { viewModel.onEvent(SetUserDataEvent.OnUsernameChanged(it)) },
            label = {
                Text(
                    text = stringResource(id = R.string.username),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary
            )
        )

        Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))

        Button(onClick = { viewModel.onEvent(SetUserDataEvent.SendUserData) }) {
            Text(text = stringResource(id = R.string.send))
        }
    }
}

