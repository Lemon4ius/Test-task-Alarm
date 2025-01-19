package com.example.test_project_alarm.screens.home_screen.logic

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

fun locationDetect(
    context: Context,
    permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    callback: (lat: Double, long: Double) -> Unit

) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                CancellationTokenSource().token

            override fun isCancellationRequested(): Boolean = false
        }).addOnSuccessListener { location ->
        if (location == null)
            Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
        else {
            val lat = location.latitude
            val long = location.longitude
            callback(lat, long)
        }
    }
}