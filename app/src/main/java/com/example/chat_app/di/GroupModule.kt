package com.example.chat_app.di

import com.example.chat_app.data.remote.GroupApi
import com.example.chat_app.data.repository.GroupRepository
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
object GroupModule {


    @Provides
    @ViewModelScoped
    private fun providesGroupApi() : GroupApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupApi::class.java)
    }

    @Provides
    @ViewModelScoped
    private fun providesGroupRepository(api : GroupApi) : GroupRepository {
        return GroupRepository(api)
    }
}