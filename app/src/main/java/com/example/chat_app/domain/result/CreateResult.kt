package com.example.chat_app.domain.result

sealed class CreateResult() {

    data object CreatedCorrectly : CreateResult()
    data object NameIsTaken: CreateResult()
    data object UnknownError: CreateResult()

}