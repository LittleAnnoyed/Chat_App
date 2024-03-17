package com.example.chat_app.data.remote

import com.example.chat_app.domain.model.GroupData
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface GroupApi {


    @POST("group/create")
    suspend fun createGroup(
        @Part groupImage: MultipartBody.Part,
        @Part(value = "name") name: String,
        @Part(value = "members") members: List<String>
    )

    @POST("group/leave")
    suspend fun groupLeave(
        @Part(value = "userId") userId: String,
        @Part(value = "groupId") groupId: String
    )

}