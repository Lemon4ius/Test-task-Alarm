package com.example.test_project_alarm.screens.setting_screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_project_alarm.shared_pref.DataStoreConstant
import com.example.test_project_alarm.shared_pref.DataStoreProvider
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingScreen() {
    val context = LocalContext.current
    var currentPath = Uri.EMPTY
    val pathView = rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val pathStore =
                DataStoreProvider(context).readStringValue(DataStoreConstant.PHOTO_PATH.name)
            withContext(Dispatchers.Main) {
                if (pathStore == DataStoreConstant.EMPTY_PATH.name) {
                    pathView.value = context.filesDir.toString()
                } else {
                    pathView.value = Uri.parse(pathStore).path.toString()
                }
                currentPath = Uri.parse(pathStore)
            }

        }
    }
    val directoryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocumentTree()) {
            currentPath = it
            pathView.value = it?.path.toString()
            CoroutineScope(Dispatchers.IO).launch {
                DataStoreProvider(context).saveStringValue(
                    DataStoreConstant.PHOTO_PATH.name,
                    currentPath.toString()
                )
            }
        }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 50.dp),
                label = { Text("Укажите путь") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "folder"
                    )
                },
                singleLine = true,
                value = pathView.value.split(":").last(),
                onValueChange = {},
                readOnly = true,
                enabled = true,
                interactionSource = remember {
                    MutableInteractionSource()
                }.also { interactionSource ->
                    LaunchedEffect(Unit) {
                        interactionSource.interactions.collect { interaction ->
                            if (interaction is PressInteraction.Release) {
                                directoryLauncher.launch(currentPath)
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingScreen() {
    Test_Project_AlarmTheme {
        SettingScreen()
    }
}