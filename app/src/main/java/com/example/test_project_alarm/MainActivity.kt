package com.example.test_project_alarm

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.test_project_alarm.main_screen.MainScreen
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Test_Project_AlarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Test()

                    MainScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun Test() {
        val context = LocalContext.current
        val storagePermission =
            rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
        LaunchedEffect(1) {
            if (!storagePermission.status.isGranted) {
                if (storagePermission.status.shouldShowRationale) {
                    Toast.makeText(
                        context,
                        "Приложению необходимо разрашение для сохранения фотографий",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    storagePermission.launchPermissionRequest()
                }
            }
        }
    }
}