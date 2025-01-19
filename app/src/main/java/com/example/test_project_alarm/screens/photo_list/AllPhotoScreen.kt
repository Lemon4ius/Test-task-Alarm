package com.example.test_project_alarm.screens.photo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_project_alarm.database.DataBaseProvider
import com.example.test_project_alarm.database.entities.PictureEntity
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme
import com.example.test_project_alarm.utils.DatePickerModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PhotoListScreen() {
    val defaultPictureEntityList = remember { mutableListOf<PictureEntity>() }
    var isFilterUse by rememberSaveable { mutableStateOf(false) }
    val photoList = remember { mutableStateListOf<PictureEntity>() }

    var isDatePickerShow by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        getPicture(defaultPictureEntityList, photoList)
    }
    if (isDatePickerShow) {
        DatePickerModal(onDismiss = { isDatePickerShow = false }, onDateSelected = { date ->
            isFilterUse = true
            val dateFormater = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.forLanguageTag("ru_RU")
            )
            if (date != null) {
                val selectedDate = dateFormater.format(Date(date))
                var tempList = mutableListOf<PictureEntity>()
                tempList.addAll(defaultPictureEntityList)
                tempList = defaultPictureEntityList.filter {
                    val picDate = dateFormater.format(Date(it.date))
                    picDate == selectedDate
                }.toMutableStateList()
                photoList.clear()
                photoList.addAll(tempList)
            }
        })
    }
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Cyan),
        floatingActionButton = {
            Row {
                if (isFilterUse) {
                    FloatingActionButton(
                        modifier = Modifier,
                        containerColor = Color.Red,
                        onClick = {
                            isFilterUse = false
                            getPicture(defaultPictureEntityList, photoList)
                        }) {
                        Icon(Icons.Filled.Clear, contentDescription = "", tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                FloatingActionButton(onClick = {
                    isDatePickerShow = true
                }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "")
                }
            }
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        ) {
            items(photoList.size) { index ->
                PhotoElement(photoList[index])
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}


fun getPicture(
    defaultList: MutableList<PictureEntity>,
    photoList: SnapshotStateList<PictureEntity>,
) {
    CoroutineScope(Dispatchers.IO).launch {
        val pictureListDB = DataBaseProvider.appDataBase.getPictureDao().getAllPicture()
        defaultList.addAll(pictureListDB)
        photoList.addAll(pictureListDB.toMutableStateList())
    }
}


@Preview
@Composable
fun PreviewPhotoListView() {
    Test_Project_AlarmTheme {
        PhotoListScreen()
    }
}

