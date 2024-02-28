package com.example.chat_app.presentation.auth.first

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.chat_app.R



@Composable
fun AuthEmailTextField(
    value: String,
    onValueChanged: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp)),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = R.string.login_email)
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        label = {
            Text(text = stringResource(id = R.string.login_email))
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun AuthPasswordTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    changePasswordVisibility: (Boolean) -> Unit
) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp)),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = R.string.password)
            )
        },
        trailingIcon = {
            if (!isPasswordVisible) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            changePasswordVisibility(true)
                        },
                    painter = painterResource(id = R.drawable.password_visibility_off),
                    contentDescription = stringResource(id = R.string.password_show),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            } else {
                Icon(
                    modifier = Modifier.clickable {
                        changePasswordVisibility(false)
                    },
                    painter = painterResource(id = R.drawable.password_visibility_on),
                    contentDescription = stringResource(id = R.string.password_hide),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        },
        label = {
            Text(
                text = stringResource(id = R.string.password),
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (!isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}