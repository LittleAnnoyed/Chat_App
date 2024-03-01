package com.example.chat_app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizes(
    val toolbarHeight: Dp = 64.dp,
    val smallRoundImageSize: Dp = 32.dp,
    val bigRoundImageSize: Dp = 128.dp
)

val LocalSizes = compositionLocalOf { Sizes() }

val MaterialTheme.sizes: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalSizes.current