package com.example.chat_app.presentation.auth.first

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app.data.repository.AuthRepository
import com.example.chat_app.domain.result.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignUpEmailChanged -> {
                state = state.copy(signUpEmail = event.value)
            }

            is AuthEvent.SignUpEmailValidate -> {
                checkSignUpEmailValidation()
            }

            is AuthEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
                state = state.copy(signUpPasswordLength = event.value.length)
            }

            is AuthEvent.SignUpPasswordVisibilityChange -> {
                state = state.copy(signUpPasswordVisibility = event.value)
            }

            is AuthEvent.SignInEmailChanged -> {
                state = state.copy(signInEmail = event.value)
            }

            is AuthEvent.SignInEmailValidate -> {
                checkSignInEmailValidation()
            }

            is AuthEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
                state = state.copy(signInPasswordLength = event.value.length)
            }

            is AuthEvent.SignInPasswordVisibility -> {
                state = state.copy(signInPasswordVisibility = event.value)
            }

            is AuthEvent.SignUp -> {
                signUp()
            }

            is AuthEvent.SignIn -> {
                signIn()
            }
        }

    }

    private fun checkSignUpEmailValidation() {
        state = state.copy(
            signUpEmailValidate = Patterns.EMAIL_ADDRESS.matcher(state.signUpEmail).matches()
        )
    }

    private fun checkSignInEmailValidation() {
        state = state.copy(
            signInEmailValidate = Patterns.EMAIL_ADDRESS.matcher(state.signInEmail).matches()
        )
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.signUp(
                email = state.signUpEmail,
                password = state.signUpPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.signIn(
                email = state.signInEmail,
                password = state.signInPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

}

data class AuthState(
    val isLoading: Boolean = false,

    val signUpEmail: String = "",
    val signUpEmailValidate: Boolean = false,
    val signUpPassword: String = "",
    val signInPasswordVisibility: Boolean = false,
    val signUpPasswordLength: Int = 0,

    val signInEmail: String = "",
    val signInEmailValidate: Boolean = false,
    val signInPassword: String = "",
    val signInPasswordLength: Int = 0,
    val signUpPasswordVisibility: Boolean = false,
)

sealed class AuthEvent {
    data class SignUpEmailChanged(val value: String) : AuthEvent()
    data object SignUpEmailValidate : AuthEvent()

    data class SignUpPasswordChanged(val value: String) : AuthEvent()
    data class SignUpPasswordVisibilityChange(val value: Boolean) : AuthEvent()

    data class SignInEmailChanged(val value: String) : AuthEvent()
    data object SignInEmailValidate : AuthEvent()

    data class SignInPasswordChanged(val value: String) : AuthEvent()
    data class SignInPasswordVisibility(val value: Boolean) : AuthEvent()

    data object SignUp : AuthEvent()
    data object SignIn : AuthEvent()
}