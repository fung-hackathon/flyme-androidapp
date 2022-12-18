package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fung_p2hacks.API.Model.MyWalkingActivity
import com.example.fung_p2hacks.R
import java.time.LocalDateTime
import java.time.ZoneOffset

private val thinTicketIDs = listOf(
    R.drawable.moonticket_thin,
    R.drawable.marsticket_thin,
    R.drawable.saturnticket_thin,
    R.drawable.neptuneticket_thin,
    R.drawable.mysteryticket_thin
)

private val starNames = listOf("MOON", "MARS", "SATURN", "NEPTUNE", "MYSTERY")

private fun timeElapsedFormatter(timeInSec: Long): String {
    var res = if (timeInSec < 3600) "${timeInSec/60}" else { if (timeInSec < 86400) "${timeInSec/3600}" else "${timeInSec/86400}" }
    //val needPlural = res.toInt() > 1

    res += if (timeInSec < 3600) "min" else { if (timeInSec < 86400) "hour" else "day" }
    //if (needPlural) res += "s"

    return res
}

private fun timeAgoFormatter(timeInSec: Long): String {
    var res = if (timeInSec < 3600) "${timeInSec/60}" else { if (timeInSec < 86400) "${timeInSec/3600}" else "${timeInSec/86400}" }
    //val needPlural = res.toInt() > 1

    res += if (timeInSec < 3600) "分" else { if (timeInSec < 86400) "時間" else "日" }
    res += "前"
    //if (needPlural) res += "s"

    return res
}

private fun getTargetName(walkingType: Int, goalMeter: Int): String {
    return if (walkingType < 4) starNames[walkingType] else "${goalMeter/1000}km"
}

@Composable
fun HistoryCard(activity: MyWalkingActivity) {
    var starID: Int = 0
    if (activity.state.equals("MARS")) starID = 1
    else if (activity.state.equals("SATURN")) starID = 2
    else if (activity.state.equals("NEPTUNE")) starID = 3
    else if (activity.state.equals("MYSTERY")) starID = 4

    val timeInSec = LocalDateTime.parse(activity.finish).toEpochSecond(ZoneOffset.MIN) - LocalDateTime.parse(activity.start).toEpochSecond(ZoneOffset.MIN)

    Box (
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ){
        Image(painter = painterResource(thinTicketIDs[starID]), contentDescription = null)

        Column(modifier = Modifier.padding(top = 3.dp, start = 15.dp)) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "EARTH", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Image(
                    painter = painterResource(R.drawable.rocket), contentDescription = null,
                    modifier = Modifier.size(12.dp, 12.dp).rotate(90f)
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Text(text = starNames[starID], fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = timeElapsedFormatter(timeInSec), fontSize = 24.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "${activity.dist}km", fontSize = 24.sp, fontWeight = FontWeight.Light)
            }

            Text(
                text = timeAgoFormatter(LocalDateTime.now().toEpochSecond(ZoneOffset.MIN) - LocalDateTime.parse(activity.finish).toEpochSecond(
                ZoneOffset.MIN)),
                fontSize = 11.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}