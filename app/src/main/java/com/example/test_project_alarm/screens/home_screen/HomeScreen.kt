package com.example.test_project_alarm.home_screen

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File
import java.util.Objects


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val capturedPhotoUri = remember { mutableStateOf<Uri>(Uri.EMPTY) }


    val file = File(context.cacheDir, "/temp_image_${System.currentTimeMillis()}.jpg")
    val photoUri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${context.packageName}.provider", file
    )
    val cameraLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                capturedPhotoUri.value = photoUri
                Toast.makeText(context, "Фото сохранено: $photoUri", Toast.LENGTH_SHORT).show()
                Log.d("Photo URI", photoUri.toString())
            } else {
                Toast.makeText(context, "Снимок не был сделан", Toast.LENGTH_SHORT).show()
            }
        }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                onClick = {
                    onMakePhotoFromCamera(
                        context = context,
                        uriInputLauncher = photoUri,
                        cameraPermissionState = cameraPermissionState,
                        cameraLaunch = cameraLaunch
                    )
                },
            ) {
                Row {
                    Text(text = "Сделать снимок")
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(imageVector = Icons.Filled.Create, contentDescription = "Снимок")
                }
            }
            Image(
                modifier = Modifier.wrapContentSize(),
                painter = rememberAsyncImagePainter(capturedPhotoUri.value),
                contentDescription = capturedPhotoUri.value.path,
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun onMakePhotoFromCamera(
    context: Context,
    uriInputLauncher: Uri,
    cameraPermissionState: PermissionState,
    cameraLaunch: ManagedActivityResultLauncher<Uri, Boolean>,
) {
    if (!cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.shouldShowRationale) {
            Log.d("Permission status", "isShouldShowRationale")
            cameraPermissionState.launchPermissionRequest()

        } else {
            Log.d("Permission status", "not ShouldShowRationale")
            cameraPermissionState.launchPermissionRequest()
        }
    } else {
        cameraLaunch.launch(uriInputLauncher)
        Toast.makeText(context, "Permission Given Already", Toast.LENGTH_SHORT)
            .show()
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Test_Project_AlarmTheme {
        HomeScreen()
    }
}