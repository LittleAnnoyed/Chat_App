package com.example.chat_app.data.repository

import android.graphics.Bitmap
import com.example.chat_app.data.mapper.toFile
import com.example.chat_app.data.remote.GroupApi
import com.example.chat_app.domain.model.GroupData
import com.example.chat_app.domain.result.CreateResult
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class GroupRepository(
    private val groupApi: GroupApi
) {

    //todo add CreateResult.NameIsTaken
    suspend fun createGroup(groupName: String, groupImage: File, groupMembers: List<String>): CreateResult {

        return try {
            groupApi.createGroup(
                groupImage = MultipartBody.Part.createFormData(
                    "groupImage",
                    groupImage.name,
                    groupImage.asRequestBody()
                ),
                name = groupName,
                members = groupMembers
            )

            CreateResult.CreatedCorrectly

        } catch (e: Exception) {
            CreateResult.UnknownError
        }

    }
}