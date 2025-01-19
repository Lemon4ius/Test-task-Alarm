package com.example.test_project_alarm.screens.home_screen.logic

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun onButtonPhotoClick(
    context: Context,
    snackBarHostState: SnackbarHostState,
    cameraLaunch: ManagedActivityResultLauncher<Uri, Boolean>,
    photoUri: Uri,
    permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
) {
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    when {
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED -> {
            Toast.makeText(context, "Вы не предоставили разрешение Camera", Toast.LENGTH_LONG)
                .show()
            permissionLauncher.launch(
                input = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }

        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED -> {
            Toast.makeText(context, "Вы не предоставили разрешение Location", Toast.LENGTH_LONG)
                .show()
            permissionLauncher.launch(input = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }

        !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
            CoroutineScope(Dispatchers.IO).launch {
                snackBarHostState.showSnackbar(
                    message = "Необходимо включить геолокацию",
                    duration = SnackbarDuration.Short
                )
            }
        }

        else -> {
            cameraLaunch.launch(photoUri)
        }

    }
}