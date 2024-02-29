package com.example.chat_app.di

import android.content.SharedPreferences
import com.example.chat_app.data.listener.ChatWebSocketListener
import com.example.chat_app.data.remote.ChatApi
import com.example.chat_app.data.repository.ChatRepository
import com.example.chat_app.util.Constants.BASE_URL
import com.example.chat_app.util.Constants.WEBSOCKET_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.net.SocketFactory


@Module
@InstallIn(ViewModelComponent::class)
object ChatModule {


    @Provides
    @ViewModelScoped
    fun providesChatApi(): ChatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providesWebSocketListener(): WebSocketListener {
        return ChatWebSocketListener()
    }

    @Provides
    @ViewModelScoped
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .socketFactory(SocketFactory.getDefault())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun providesWebSocket(client: OkHttpClient, listener: WebSocketListener): WebSocket {
        val request = Request
            .Builder()
            .url(WEBSOCKET_URL)
            .build()
        return client.newWebSocket(request, listener)
    }

    @Provides
    @ViewModelScoped
    fun providesGson() : Gson {
        return Gson()
    }

    @Provides
    @ViewModelScoped
    fun providesChatRepository(
        api: ChatApi,
        ws: WebSocket,
        listener: WebSocketListener,
        gson: Gson,
        prefs: SharedPreferences,
    ): ChatRepository {
        return ChatRepository(api, ws, gson, prefs)
    }

}