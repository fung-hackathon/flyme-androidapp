package com.example.fung_p2hacks.WalkingScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.time.LocalDateTime
import java.time.ZoneOffset


//Be careful. walkingType is not MutableLiveData since it should be immutable.
data class WalkingActivityData(
    private val walkingType: Int,
    private val startTime: LocalDateTime
) {
    private lateinit var finishTime: LocalDateTime
    private var elapsedSeconds: MutableLiveData<Long> = MutableLiveData(0)
    private var distSum: MutableLiveData<Double> = MutableLiveData(0.0)
    private val LocationHistory: MutableList<LatLng> = mutableListOf()

    fun getElapsedSeconds(): MutableLiveData<Long> {
        return elapsedSeconds
    }

    fun getWalkingType(): Int {
        return walkingType
    }

    fun getHistorySize(): Int {
        return LocationHistory.size
    }

    fun addNewPoints(latestPoint: LatLng) {
        if (getHistorySize() > 1) {
            val lastPoint = LocationHistory.last()
            val dist = SphericalUtil.computeDistanceBetween(lastPoint, latestPoint)
            val prevSum = distSum.value!!
            distSum.value = prevSum + dist

            Log.d("activityData", "lastDist = ${dist}")
        }

        LocationHistory.add(latestPoint)
        elapsedSeconds.value = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - startTime.toEpochSecond(ZoneOffset.UTC)
    }

    fun getWalkedDistAsState(): MutableLiveData<Double> {
        return distSum
    }

    fun getWalkedDistAsDouble(): Double {
        return distSum.value!!
    }

    fun setFinishTime(time: LocalDateTime) {
        finishTime = time
    }
}