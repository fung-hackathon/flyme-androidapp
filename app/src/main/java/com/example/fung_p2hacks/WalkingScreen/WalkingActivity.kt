package com.example.fung_p2hacks.WalkingScreen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fung_p2hacks.LocationService.LocationService
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.R

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.*

class WalkingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mLocationService = LocationService(fusedLocationClient)

        setContent {
            FuNG_p2hacksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    val thisContext = LocalContext.current
                    if (ContextCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(
                            thisContext as Activity,
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            ),
                            1234
                        )
                    }

                    mLocationService.startLocationUpdate()
                    WalkingRootComposable(fusedLocationClient, mLocationService)
                }
            }
        }
    }
}

@Composable
fun WalkingRootComposable(
    fusedLocationClient: FusedLocationProviderClient,
    mLocationService: LocationService
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MapComposable(
            fusedLocationClient,
            mLocationService
        )

        Column (
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.marsticket), contentDescription = null)
            Button(onClick = {}) {
                Text(text = "aaa")
            }
        }
    }
}

@Composable
fun MapComposable(
    fusedLocationClient: FusedLocationProviderClient,
    mLocationService: LocationService
) {
    var lat by remember { mutableStateOf(1.35) }
    var lng by remember { mutableStateOf(103.87) }
    var pos = LatLng(lat, lng)
    var finished by remember { mutableStateOf(false) }
    var mapReady by remember { mutableStateOf(false) }

    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
        .addOnSuccessListener { location ->
            lat = location.latitude
            lng = location.longitude
            pos = LatLng(lat, lng)
            finished = true
        }

    if (finished) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pos, 18f)
        }

        var uiSettings by remember { mutableStateOf(MapUiSettings()) }
        uiSettings = uiSettings.copy(zoomControlsEnabled = false)

        val cLocation = mLocationService.currentLocation.observeAsState()

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            onMapLoaded = { mapReady = true }
        ) {
            Marker(
                state = MarkerState(position = LatLng(lat, lng)),
                title = "Current Location"
            )
        }

        if (mapReady) {
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(LatLng(cLocation.value!!.latitude, cLocation.value!!.longitude), 18f)
                )
            )
        }
    }
}