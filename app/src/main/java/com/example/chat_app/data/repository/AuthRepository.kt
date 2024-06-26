package com.example.chat_app.data.repository

import android.content.SharedPreferences
import android.graphics.Bitmap
import com.example.chat_app.data.remote.AuthApi
import com.example.chat_app.data.dto.AuthDto
import com.example.chat_app.data.mapper.toFile
import com.example.chat_app.domain.result.AuthResult
import com.example.chat_app.domain.result.SetUserDataResult
import com.example.chat_app.util.Constants.JWT_TOKEN
import com.example.chat_app.util.Constants.USERNAME
import com.example.chat_app.util.Constants.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class AuthRepository(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) {


    suspend fun signUp(email: String, password: String): AuthResult<Unit> =
        withContext(Dispatchers.IO) {
            try {
                api.register(
                    request = AuthDto(email, password)
                )
                signIn(email, password)
                AuthResult.Authorized()
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    AuthResult.Unauthorized()
                } else {
                    AuthResult.UnknownError()
                }
            } catch (e: Exception) {
                AuthResult.UnknownError()
            }
        }

    suspend fun signIn(email: String, password: String): AuthResult<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.login(
                    request = AuthDto(email, password)
                )
                prefs.edit().putString(JWT_TOKEN, response.token).apply()
                prefs.edit().putString(USER_ID, response.userId).apply()
                if (response.username != null) {
                    AuthResult.Authorized()
                } else {
                    AuthResult.DataNotSet()
                }


            } catch (e: HttpException) {
                if (e.code() == 401) {
                    AuthResult.Unauthorized()
                } else {
                    AuthResult.UnknownError()
                }
            } catch (e: Exception) {
                AuthResult.UnknownError()
            }
        }

    suspend fun authenticate(): AuthResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val token =
                prefs.getString(JWT_TOKEN, null) ?: return@withContext AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.Unauthorized()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }

    }

    suspend fun setUserData(username: String, userImage: File): SetUserDataResult<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val userId = prefs.getString(USER_ID, null)

                api.setUserData(
                    userId = userId!!,
                    username = username,
                    userImageUri = MultipartBody.Part.createFormData(
                        "userImage",
                        userImage.name,
                        userImage.asRequestBody()
                    )
                )

                prefs.edit().putString(USERNAME, username).apply()
                SetUserDataResult.Correctly()
            } catch (e: Exception) {
                SetUserDataResult.UnknownError()
            }
        }
}