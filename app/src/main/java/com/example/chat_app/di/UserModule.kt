package com.example.chat_app.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.chat_app.data.remote.UserApi
import com.example.chat_app.data.repository.UserRepository
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
object UserModule {

    @Provides
    @ViewModelScoped
    fun provideUserApi() : UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providesUserRepository(userApi: UserApi,prefs: SharedPreferences): UserRepository {
        return UserRepository(userApi, prefs)
    }

}