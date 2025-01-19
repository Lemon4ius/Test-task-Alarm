package com.example.test_project_alarm.home_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.test_project_alarm.R
import com.example.test_project_alarm.screens.home_screen.logic.cameraLaunchSuccess
import com.example.test_project_alarm.screens.home_screen.logic.onButtonPhotoClick
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme
import com.example.test_project_alarm.utils.createImageFile
import com.example.test_project_alarm.utils.getPermissionLaunch
import java.util.Objects


@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val permissionLauncher = getPermissionLaunch()
    val capturedPhotoUri = remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val isLoading = remember { mutableStateOf(false) }
    val file = remember {
        mutableStateOf(
            context.createImageFile()
        )
    }
    val photoUri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${context.packageName}.provider",
        file.value
    )
    val cameraLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                isLoading.value = true
                cameraLaunchSuccess(
                    file = file,
                    context = context,
                    capturedPhotoUri = capturedPhotoUri,
                    permissionLauncher = permissionLauncher,
                    isLoading = isLoading
                )
            } else Toast.makeText(context, "Снимок не был сделан", Toast.LENGTH_SHORT).show()
        }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { padding ->
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .height(100.dp)
                        .width(50.dp),
                    onClick = {
                        onButtonPhotoClick(
                            context = context,
                            snackBarHostState = snackBarHostState,
                            cameraLaunch = cameraLaunch,
                            photoUri = photoUri,
                            permissionLauncher = permissionLauncher
                        )
                    },
                ) {
                    Icon(ImageVector.vectorResource(R.drawable.camera_ic), contentDescription = "")
                }
            }
            if (isLoading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .pointerInput(Unit) {}, contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview()
@Composable
fun HomeScreenPreview() {
    Test_Project_AlarmTheme {
        HomeScreen()
    }
}





