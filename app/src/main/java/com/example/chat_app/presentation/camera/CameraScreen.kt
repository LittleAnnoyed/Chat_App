package com.example.chat_app.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.chat_app.R

@Composable
fun CameraScreen() {

    CameraContent()
}

@Composable
fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap?= null
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember{ LifecycleCameraController(context) }

    //todo add content decs
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Take photo") },
                icon = { Icon(painter = painterResource(id = R.drawable.camera), contentDescription = null) },
                onClick = { capturePhoto(context,cameraController,onPhotoCaptured) })
        }
    ) {paddingValues ->

        Box(modifier = Modifier.fillMaxSize()){
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                }
            )

            if (lastCapturedPhoto != null) {
                LastPhotoPreview(
                    modifier = Modifier.align(alignment = Alignment.BottomStart),
                    lastCapturedPhoto = lastCapturedPhoto
                )
            }

        }
    }
}

fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    TODO("Not yet implemented")
}

@Composable
fun LastPhotoPreview(modifier: Modifier, lastCapturedPhoto: Bitmap) {

}
