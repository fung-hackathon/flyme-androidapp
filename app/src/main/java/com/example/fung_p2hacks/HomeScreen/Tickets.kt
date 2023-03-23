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
private val ticketAspectRatio = 1392f / 636f
private val horizontalPaddingValue = 16.dp
private val verticalPaddingValue = 10.dp

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TicketComposable(
    onTicketClicked: List<() -> Unit>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerState = rememberPagerState()

        BoxWithConstraints {
            val ticketWidth = maxWidth - horizontalPaddingValue * 2
            val ticketHeight = ticketWidth / ticketAspectRatio
            val pageSpacing = horizontalPaddingValue / 2

            HorizontalPager(
                count = 5,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = horizontalPaddingValue),
                itemSpacing = pageSpacing,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                Surface (
                    onClick = onTicketClicked[page],
                    color = MaterialTheme.colors.background
                ) {
                    Image(
                        painter = painterResource(ticketList[page]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = ticketWidth, height = ticketHeight)
                    )
                }
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