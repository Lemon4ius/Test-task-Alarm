package com.example.test_project_alarm.screens.home_screen.logic

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import com.example.test_project_alarm.shared_pref.DataStoreConstant
import com.example.test_project_alarm.shared_pref.DataStoreProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File

suspend fun saveFilePicture(context: Context, file: File): String = coroutineScope {
    val contentResolver = context.contentResolver
    val folderUriStr =
        DataStoreProvider(context).readStringValue(DataStoreConstant.PHOTO_PATH.name)
    val folderUri = Uri.parse(folderUriStr)

    if (folderUriStr == DataStoreConstant.EMPTY_PATH.name) {
        context.openFileOutput(file.name, Context.MODE_PRIVATE).use { output ->
            output.write(file.readBytes())
        }
        return@coroutineScope Uri.fromFile(file).toString()
    } else {
        contentResolver.takePersistableUriPermission(
            folderUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        val documentUri = DocumentsContract.buildDocumentUriUsingTree(
            folderUri,
            DocumentsContract.getTreeDocumentId(folderUri)
        )
        val newFileUri = DocumentsContract.createDocument(
            contentResolver,
            documentUri,
            "image/jpg",
            file.name
        )
        newFileUri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                try {
                    file.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Не удалось сохранить", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return@coroutineScope newFileUri.toString()
    }
}