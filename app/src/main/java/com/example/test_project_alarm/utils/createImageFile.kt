package com.example.test_project_alarm.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.createImageFile(): File {
    val timeStamp =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.forLanguageTag("ru_Ru")).format(Date())
    val imageFileName = timeStamp
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    if (storageDir?.exists() == false) {
        storageDir.mkdirs()
    }
    return File.createTempFile(
        imageFileName,
        ".jpg",
        storageDir
    )
}