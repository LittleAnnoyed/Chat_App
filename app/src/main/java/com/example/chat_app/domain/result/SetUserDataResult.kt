package com.example.chat_app.domain.result

sealed class SetUserDataResult<T> (val data: T? = null) {

    class Correctly <T>(data: T? = null): SetUserDataResult<T>()
    class UnknownError <T> : SetUserDataResult<T>()
}