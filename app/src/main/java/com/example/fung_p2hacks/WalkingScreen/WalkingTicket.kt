package com.example.fung_p2hacks.WalkingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.PrimaryWhite

@Composable
fun WalkingTicketComposable() {

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
            modifier = Modifier.size(40.dp, 40.dp)
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