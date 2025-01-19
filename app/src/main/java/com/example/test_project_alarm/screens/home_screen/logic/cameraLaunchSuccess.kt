package com.example.test_project_alarm.screens.home_screen.logic

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.MutableState
import com.example.test_project_alarm.database.DataBaseProvider
import com.example.test_project_alarm.database.entities.PictureEntity
import com.example.test_project_alarm.shared_pref.DataStoreConstant
import com.example.test_project_alarm.shared_pref.DataStoreProvider
import com.example.test_project_alarm.utils.createImageFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

fun cameraLaunchSuccess(
    capturedPhotoUri: MutableState<Uri>,
    context: Context,
    file: MutableState<File>,
    permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    isLoading: MutableState<Boolean>
) {
    capturedPhotoUri.value = Uri.fromFile(file.value)
    CoroutineScope(Dispatchers.IO).launch {
        val pathToSave =
            DataStoreProvider(context).readStringValue(DataStoreConstant.PHOTO_PATH.name)
        val path = saveFilePicture(
            context = context,
            file = file.value
        )
        locationDetect(context, permissionLauncher, callback = { lat, long ->
            CoroutineScope(Dispatchers.IO).launch {
                val job = launch {
                    DataBaseProvider.appDataBase.getPictureDao().addPicture(
                        PictureEntity(
                            name = file.value.name,
                            date = System.currentTimeMillis(),
                            uri = if (pathToSave == DataStoreConstant.EMPTY_PATH.name)
                                file.value.path
                            else path,
                            latitude = lat,
                            longitude = long
                        )
                    )
                }
                job.join()
                file.value = context.createImageFile()
                isLoading.value = false
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}