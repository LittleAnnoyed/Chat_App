package com.example.chat_app.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val loginWrongMessage = stringResource(id = R.string.login_wrong_message)
    val unknownError = stringResource(id = R.string.unknown_error)

    LaunchedEffect(key1 = authViewModel, key2 = context){
        authViewModel.authResults.collect { result ->
            when(result){
                is AuthResult.Authorized -> {
                    navController.navigate(Screen.HomeScreen.route){
                        popUpTo(Screen.LoginScreen.route){
                            inclusive = true
                        }
                    }
                }

                is AuthResult.Unauthorized -> {
                    Toast.makeText(context,loginWrongMessage,Toast.LENGTH_SHORT).show()
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(context,unknownError,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Login UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.large)
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true)
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.fontSize.title
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

            Text(
                text = stringResource(id = R.string.login_communicat),
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.fontSize.itemTitle
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            AuthEmailTextField(
                value = authViewModel.state.signInEmail,
                onValueChanged = { authViewModel.onEvent(AuthEvent.SignInEmailChanged(it)) })

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            AuthPasswordTextField(
                value = authViewModel.state.signInPassword,
                onValueChanged = { authViewModel.onEvent(AuthEvent.SignInPasswordChanged(it)) },
                isPasswordVisible = authViewModel.state.signInPasswordVisibility,
                changePasswordVisibility = {
                    authViewModel.onEvent(
                        AuthEvent.SignInPasswordVisibility(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            Button(onClick = { authViewModel.onEvent(AuthEvent.SignIn) }) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }


        Text(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.large)
                .clickable { navController.navigate(Screen.RegisterScreen.route) },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(stringResource(id = R.string.move_to_register_screen_first_part))
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.move_to_register_screen_second_part))
                }
            })

    }
}
