package com.example.test_project_alarm.screens.photo_list

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.test_project_alarm.database.entities.PictureEntity
import com.example.test_project_alarm.utils.ImageDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PhotoElement(pictureEntity: PictureEntity) {
    val context = LocalContext.current
    val path = Uri.parse(pictureEntity.uri).path
    val isImageFullScreen = remember { mutableStateOf(false) }
    val pathView = if (path?.contains(context.packageName) == true) path else path?.split(":")
        ?.last()
    val dateFormat = SimpleDateFormat(
        "hh:mm dd.MM.yyyy",
        Locale.forLanguageTag("ru_RU")
    )
    val pictureDate = dateFormat.format(Date(pictureEntity.date))

    ImageDialog(isImageFullScreen, pictureEntity)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        onClick = { isImageFullScreen.value = true },
    ) {
        Column(modifier = Modifier) {
            AsyncImage(
                Uri.parse(pictureEntity.uri),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Название: ${pictureEntity.name}",
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
            Column(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                Text("Дата сьемки: $pictureDate")
                Text(
                    "Path: $pathView", maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}