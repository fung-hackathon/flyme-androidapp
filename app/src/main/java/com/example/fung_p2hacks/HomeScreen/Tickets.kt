package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

private val ticketList: List<Int> = listOf(R.drawable.moonticket, R.drawable.marsticket, R.drawable.saturnticket, R.drawable.neptuneticket, R.drawable.mysteryticket)

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TicketComposable(
    onTicketClicked: List<() -> Unit>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = 5,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) { page ->
            Surface (
                onClick = onTicketClicked[page],
                color = MaterialTheme.colors.background
            ) {
                Image(
                    painter = painterResource(ticketList[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .offset {
                            val pageOffset =
                                this@HorizontalPager.calculateCurrentOffsetForPage(page)
                            IntOffset(
                                x = -(10.dp * pageOffset).roundToPx(),
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