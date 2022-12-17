package com.example.fung_p2hacks.WalkingScreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fung_p2hacks.HomeScreen.HomeActivity
import com.example.fung_p2hacks.LocationService.LocationService
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.PrimaryBlack
import com.example.fung_p2hacks.ui.theme.PrimaryWhite

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.*
import java.time.LocalDateTime

class WalkingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mLocationService = LocationService(fusedLocationClient)
        lateinit var activityData: WalkingActivityData

        setContent {
            val thisContext = LocalContext.current
            val thisIntent = (thisContext as WalkingActivity).intent

            //Default is 5 because the number 4 or bigger will treat as "Mystery ticket"
            val walkingType = thisIntent.getIntExtra("walkingType", 5)
            val nowTime = LocalDateTime.now()
            activityData = WalkingActivityData(walkingType, nowTime)

            FuNG_p2hacksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){

                    if (ContextCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(
                            thisContext as Activity,
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                            ),
                            1234
                        )
                    }

                    //Request location updating.
                    mLocationService.startLocationUpdate()
                    WalkingRootComposable(
                        fusedLocationClient,
                        mLocationService,
                        activityData,
                        onFinishPressed = {
                            mLocationService.stopLocationUpdate()
                            activityData.setFinishTime(LocalDateTime.now())

                            //I should show a dialog here.
                            //TODO: Activity Summary

                            val intent = Intent(thisContext, HomeActivity::class.java)
                            thisContext.startActivity(intent)
                            (thisContext as Activity).finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WalkingRootComposable(
    fusedLocationClient: FusedLocationProviderClient,
    mLocationService: LocationService,
    activityData: WalkingActivityData,
    onFinishPressed: () -> Unit
) {
    val walkingType = activityData.getWalkingType()
    val elapsedSeconds = activityData.getElapsedSeconds().observeAsState()
    val meterWalked = activityData.getWalkedDistAsState().observeAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MapComposable(
            fusedLocationClient,
            mLocationService,
            activityData
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WalkingTicketComposable(
                walkingType = walkingType,
                elapsedSeconds = elapsedSeconds,
                meterWalked = meterWalked
            )

            Button(
                onClick = onFinishPressed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryBlack,
                    contentColor = PrimaryWhite
                )
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "おさんぽをおわる")
                    Image(
                        painter = painterResource(R.drawable.chevron_down), contentDescription = null,
                        modifier = Modifier
                            .rotate(270f)
                            .size(15.dp, 15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MapComposable(
    fusedLocationClient: FusedLocationProviderClient,
    mLocationService: LocationService,
    activityData: WalkingActivityData
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
            position = CameraPosition.fromLatLngZoom(pos, 16f)
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
                state = rememberMarkerState(position = LatLng(cLocation.value!!.latitude, cLocation.value!!.longitude)),
                title = "Current Location"
            )
        }


        if (mapReady) {
            onLocationUpdate(
                cameraPositionState = cameraPositionState,
                cLocation = cLocation,
                activityData = activityData
            )
        }
    }
}

@Composable
fun onLocationUpdate(
    cameraPositionState: CameraPositionState,
    cLocation: State<LatLng?>,
    activityData: WalkingActivityData
) {
    cameraPositionState.move(
        CameraUpdateFactory.newCameraPosition(
            CameraPosition.fromLatLngZoom(LatLng(cLocation.value!!.latitude, cLocation.value!!.longitude), 16f)
        )
    )

    activityData.addNewPoints(LatLng(cLocation.value!!.latitude, cLocation.value!!.longitude))
    Log.d("activityData", "History Size = ${activityData.getHistorySize()}")
    Log.d("activityData", "Walked dist = ${activityData.getWalkedDistAsDouble()}")
    Log.d("activityData", "Elapsed = ${activityData.getElapsedSeconds().value}")
}