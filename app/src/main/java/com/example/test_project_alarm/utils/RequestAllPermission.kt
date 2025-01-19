package com.example.test_project_alarm.utils

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


@Composable
fun getPermissionLaunch(): ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                }

                permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false) -> {

                }
                permissions.getOrDefault(Manifest.permission.CAMERA,false)->{

                }
                else -> {
                    // No location access granted.
                }
            }
        }
}