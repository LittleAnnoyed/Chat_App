package com.example.chat_app.data.mapper

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

fun Bitmap.toFile(): File {
    val byteArray = bitmapToByteArray(this)
    val title = generateFileTitle()
    return byteArrayToFile(byteArray,title)
}

private fun bitmapToByteArray(bitmap: Bitmap?) : ByteArray {
    val outputStream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG,100,outputStream)
    return outputStream.toByteArray()
}

private fun generateFileTitle(): String {
    var title: String = ""
    for (i in 1..20) {
        val number = Random.nextInt(0,9)
        title += number.toString()
    }
    return title
}

private fun byteArrayToFile(byteArray: ByteArray?, title: String?): File {
    val file = File.createTempFile(title ?: "title","")
    val outputStream = FileOutputStream(file)
    outputStream.write(byteArray)
    return file
}