package com.example.chat_app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class FontSize(
    val primary: TextUnit = 16.sp,
    val secondary: TextUnit = 14.sp,
    val button: TextUnit = 14.sp,
    val title: TextUnit = 20.sp,
    val itemTitle: TextUnit = 18.sp
)

val LocalFontSize = compositionLocalOf { FontSize() }

val MaterialTheme.fontSize: FontSize
    @Composable
    @ReadOnlyComposable
    get() = LocalFontSize.current