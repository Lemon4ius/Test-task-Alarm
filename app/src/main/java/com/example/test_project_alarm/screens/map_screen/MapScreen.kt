package com.example.test_project_alarm.map_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.test_project_alarm.database.DataBaseProvider
import com.example.test_project_alarm.database.entities.PictureEntity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MapScreen() {
    val pictureList = remember { mutableStateListOf<PictureEntity>() }
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(0.0, 0.0),
            0f
        )
    }
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            pictureList.addAll(DataBaseProvider.getPictureDao().getAllPicture())
            if (pictureList.isNotEmpty())
                cameraPosition.position = CameraPosition.fromLatLngZoom(
                    LatLng(
                        pictureList.last().latitude,
                        pictureList.last().longitude
                    ), 10f
                )
        }
    }
    Scaffold {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            cameraPositionState = cameraPosition
        ) {
            for (item in pictureList) {
                Marker(
                    state = remember {
                        MarkerState(
                            position = LatLng(
                                item.latitude,
                                item.longitude
                            )
                        )
                    },
                    title = item.name
                )
            }
        }
    }
}