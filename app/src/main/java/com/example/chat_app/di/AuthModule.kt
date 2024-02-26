package com.example.chat_app.di

import android.content.SharedPreferences
import com.example.chat_app.data.remote.AuthApi
import com.example.chat_app.data.repository.AuthRepository
import com.example.chat_app.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(api: AuthApi,prefs: SharedPreferences): AuthRepository{
        return AuthRepository(api, prefs)
    }
}