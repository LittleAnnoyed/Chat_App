package com.example.chat_app.presentation.camera

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.chat_app.R
import com.example.chat_app.ui.spacing
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CameraScreen(
    setImageUri: (Uri) -> Unit,
    uri: Uri
) {
    val previewView = PreviewView(LocalContext.current)
    val imageCapture: ImageCapture? = null
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { _ ->
            previewView.apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        })

        //todo add content desc
        Icon(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = MaterialTheme.spacing.medium)
                .clickable {
                    takePhoto(
                        imageCapture,
                        contentResolver = contentResolver,
                        context
                    ) { setImageUri(uri) }
                },
            painter = painterResource(id = R.drawable.camera),
            contentDescription = null
        )
    }
}

fun startCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
) {

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({

        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        val imageCapture = ImageCapture.Builder()
            .build()

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageCapture
            )
        } catch (exc: Exception) {
            Log.e("Camera", exc.toString())
        }

    }, ContextCompat.getMainExecutor(context))
}

private fun takePhoto(
    imageCap: ImageCapture?,
    contentResolver: ContentResolver,
    context: Context,
    setImageUri: (Uri?) -> Unit
) {

    val imageCapture = imageCap ?: return

    val name = SimpleDateFormat(
        "yyyy-MM-dd-HH-mm-ss-SSS",
        Locale.GERMANY
    ).format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
    ).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                setImageUri(output.savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", exception.toString())
            }

        }
    )
}