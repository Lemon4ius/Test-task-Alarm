package com.example.test_project_alarm.screens.photo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.test_project_alarm.ui.theme.Test_Project_AlarmTheme

@Composable
fun PhotoListScreen(){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    ) {
        it
        Text(text = "PhotoList")
    }
}

@Preview
@Composable
fun PreviewPhotoListView(){
    Test_Project_AlarmTheme {
        PhotoListScreen()
    }
}