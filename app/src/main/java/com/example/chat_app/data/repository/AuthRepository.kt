package com.example.chat_app.data.repository

import android.content.SharedPreferences
import com.example.chat_app.data.remote.AuthApi
import com.example.chat_app.data.dto.AuthDto
import com.example.chat_app.domain.model.AuthResult
import com.example.chat_app.util.Constants.JWT_TOKEN
import com.example.chat_app.util.Constants.USER_ID
import retrofit2.HttpException

class AuthRepository(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) {


    suspend fun signUp(email: String, password: String): AuthResult<Unit> {
        return try {
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

    suspend fun signIn(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.login(
                request = AuthDto(email, password)
            )
            prefs.edit().putString(JWT_TOKEN, response.token).apply()
            prefs.edit().putString(USER_ID, response.userId).apply()

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

    suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString(JWT_TOKEN,null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException){
            if (e.code() == 401){
                AuthResult.Unauthorized()
            } else {
                AuthResult.Unauthorized()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
        }
    }
}