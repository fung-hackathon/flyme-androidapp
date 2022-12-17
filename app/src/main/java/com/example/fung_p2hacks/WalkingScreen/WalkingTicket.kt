package com.example.fung_p2hacks.WalkingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.PrimaryWhite

private val walkingTicketList: List<Int> = listOf(R.drawable.moonticket_thick, R.drawable.marsticket_thick, R.drawable.saturnticket_thick, R.drawable.neptuneticket_thick, R.drawable.mysteryticket_walking)
private val targetNames: List<String> = listOf("MOON", "MARS", "SATURN", "NEPTUNE")
private val goalMeters: List<Int> = listOf(1500, 4000, 8000, 10000)

fun timeElapsedFormatter(timeInSec: Long): String {
    var res = if (timeInSec < 3600) "${timeInSec/60}" else { if (timeInSec < 86400) "${timeInSec/3600}" else "${timeInSec/86400}" }
    //val needPlural = res.toInt() > 1

    res += if (timeInSec < 3600) "min" else { if (timeInSec < 86400) "hour" else "day" }
    //if (needPlural) res += "s"

    return res
}

private fun getTargetName(walkingType: Int, goalMeter: Int): String {
    return if (walkingType < 4) targetNames[walkingType] else "${goalMeter/1000}km"
}

@Composable
fun WalkingTicketComposable(
    walkingType: Int,
    elapsedSeconds: State<Long?>,
    meterWalked: State<Double?>
){
    WalkingTicketComposable(
        walkingType = walkingType,
        elapsedSeconds = elapsedSeconds.value!!,
        meterWalked = meterWalked.value!!.toInt()
    )
}

@Composable
fun WalkingTicketComposable(
    walkingType: Int,
    elapsedSeconds: Long,
    meterWalked: Int,
) {
    //Set defined meter if walkingType < 4. Otherwise 10[km]*ceil(meterWalked/10000)
    val goalMeter = if (walkingType < 4) goalMeters[walkingType] else 10000*((meterWalked + 9999)/10000)

    Box(
        modifier = Modifier.padding(15.dp)
    ) {
        Image(painter = painterResource(walkingTicketList[walkingType]), contentDescription = null)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = timeElapsedFormatter(elapsedSeconds),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.padding(start = 20.dp))
                Text(
                    text = "${meterWalked/1000}.${(meterWalked/100)%10}km",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light
                )
            }

            ProgressBarComposable(
                maxValue = goalMeter.toDouble(),
                currentValue = meterWalked.toDouble(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "EARTH", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = getTargetName(walkingType, goalMeter),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ProgressBarComposable(
    modifier: Modifier = Modifier,
    minValue: Double = 0.0,
    maxValue: Double = 100.0,
    currentValue: Double
) {
    val fixedMaxValue = maxValue - minValue
    val fixedCurrentValue = currentValue - minValue
    val progress = fixedCurrentValue / fixedMaxValue

    BoxWithConstraints(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Gray)
                .align(Alignment.CenterStart)
        )
        val screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }

        Box(
            modifier = Modifier
                .width(screenWidth * progress.toFloat())
                .height(5.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(PrimaryWhite)
                .align(Alignment.CenterStart)
        )

        var rocketPos = screenWidth * progress.toFloat() - (screenWidth * 4 / 100)
        if (rocketPos <= 0.dp) {
            rocketPos = 0.dp
        }
        else if (rocketPos > screenWidth - (screenWidth * 10 / 100)) {
            rocketPos = screenWidth - (screenWidth * 10 / 100)
        }

        Image(
            painter = painterResource(R.drawable.rocket),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp, 30.dp)
                .align(Alignment.CenterStart)
                .offset(x = rocketPos)
                .rotate(90f)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun progressBarPreview50() {
    FuNG_p2hacksTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            ProgressBarComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(start = 20.dp, end = 20.dp),
                currentValue = 50.0
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun progressBarPreview25() {
    FuNG_p2hacksTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            ProgressBarComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(start = 20.dp, end = 20.dp),
                currentValue = 25.0
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ticketPreview() {
    FuNG_p2hacksTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            WalkingTicketComposable(walkingType = 0, elapsedSeconds = 360, meterWalked = 900)
            WalkingTicketComposable(walkingType = 1, elapsedSeconds = 360, meterWalked = 3600)
            WalkingTicketComposable(walkingType = 2, elapsedSeconds = 360, meterWalked = 6500)
            WalkingTicketComposable(walkingType = 3, elapsedSeconds = 3600, meterWalked = 9000)
            //WalkingTicketComposable(walkingType = 4, secondsElapse = 10800, meterWalked = 36000)
        }
    }
}