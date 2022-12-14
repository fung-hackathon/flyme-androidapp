package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.Gray
import com.example.fung_p2hacks.ui.theme.PrimaryWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState

val ticketList: List<Int> = listOf(R.drawable.moonticket, R.drawable.marsticket, R.drawable.saturnticket, R.drawable.neptuneticket, R.drawable.mysteryticket)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TicketComposable() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = 5,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 10.dp)
        ) { page ->
            Button(
                onClick = {/*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent
                ),
                modifier = Modifier.defaultMinSize(minHeight = 0.dp, minWidth = 0.dp)
            ) {
                Image(
                    painter = painterResource(ticketList[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .offset {
                            val pageOffset =
                                this@HorizontalPager.calculateCurrentOffsetForPage(page)
                            IntOffset(
                                x = -(1.dp * pageOffset).roundToPx(),
                                y = 0
                            )
                        }
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 0.dp, bottom = 10.dp),
            activeColor = PrimaryWhite,
            inactiveColor = Gray
        )
    }
}