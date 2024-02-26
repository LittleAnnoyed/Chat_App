package com.example.chat_app.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chat_app.R
import com.example.chat_app.domain.model.AuthResult
import com.example.chat_app.ui.fontSize
import com.example.chat_app.ui.spacing
import com.example.chat_app.nav.Screen


@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val accExistsMessage = stringResource(id = R.string.acc_exist_message)
    val errorMessage = stringResource(id = R.string.unknown_error)

    LaunchedEffect(key1 = authViewModel, key2 = context) {
        authViewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    navController.navigate(Screen.HomeScreen.route){
                        popUpTo(Screen.RegisterScreen.route){
                            inclusive = true
                        }
                    }
                }

                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        accExistsMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // Register Screen Ui
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .background(MaterialTheme.colorScheme.background)
                .weight(1f, true),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = stringResource(id = R.string.create_acc),
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.fontSize.title
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            AuthEmailTextField(
                value = authViewModel.state.signUpEmail,
                onValueChanged = { authViewModel.onEvent(AuthEvent.SignUpEmailChanged(it)) },
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            AuthPasswordTextField(
                value = authViewModel.state.signUpPassword,
                onValueChanged = { authViewModel.onEvent(AuthEvent.SignUpPasswordChanged(it)) },
                isPasswordVisible = authViewModel.state.signUpPasswordVisibility,
                changePasswordVisibility = {
                    authViewModel.onEvent(
                        AuthEvent.SignUpPasswordVisibilityChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            Button(onClick = {
                authViewModel.onEvent(AuthEvent.SignUp)
            }) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.large)
                .clickable { navController.navigate(Screen.LoginScreen.route) },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(stringResource(id = R.string.move_to_login_screen_first_part))
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.move_to_login_screen_second_part))
                }
            })

    }

}
