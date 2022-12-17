package com.example.fung_p2hacks.LocationService

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.*
import com.google.android.gms.maps.model.LatLng

class LocationService(private val fusedLocationClient: FusedLocationProviderClient) {
    var currentLocation: MutableLiveData<LatLng> = MutableLiveData(LatLng(0.0,0.0))

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()

                if (currentLocation.value?.equals(LatLng(location.latitude, location.longitude)) == false) {
                    currentLocation.value = LatLng(location.latitude, location.longitude)

                    Log.d("Location Data", "Got Location: ${location.latitude}, ${location.longitude}")
                    Log.d("Location Data", "Live Location: ${currentLocation.value?.latitude}, ${currentLocation.value?.longitude}")
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    fun startLocationUpdate() {
        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}