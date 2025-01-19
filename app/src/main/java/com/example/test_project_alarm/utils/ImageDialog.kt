package com.example.test_project_alarm.utils

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.test_project_alarm.database.entities.PictureEntity

@Composable
fun ImageDialog(isShow: MutableState<Boolean>, imageEntity: PictureEntity) {
    if (isShow.value) {
        Dialog(
            onDismissRequest = { isShow.value = false }
        ) {
            AsyncImage(
                Uri.parse(imageEntity.uri),
                contentDescription = null
            )
        }
    }
}