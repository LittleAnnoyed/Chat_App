package com.example.chat_app.data.repository

import android.content.SharedPreferences
import android.graphics.Bitmap
import com.example.chat_app.data.mapper.toFile
import com.example.chat_app.data.remote.GroupApi
import com.example.chat_app.domain.model.GroupData
import com.example.chat_app.domain.result.CreateResult
import com.example.chat_app.util.Constants.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class GroupRepository(
    private val groupApi: GroupApi,
    private val prefs: SharedPreferences
) {

    //todo add CreateResult.NameIsTaken
    suspend fun createGroup(
        groupName: String,
        groupImage: File,
        groupMembers: List<String>
    ): CreateResult = withContext(Dispatchers.IO) {

        try {
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

    suspend fun groupLeave(groupId: String)  = withContext(Dispatchers.IO){
        try {
            val userId = prefs.getString(USER_ID, null)
            groupApi.groupLeave(userId!!, groupId)
        } catch (e: Exception) {
            println("Error")
        }
    }
}