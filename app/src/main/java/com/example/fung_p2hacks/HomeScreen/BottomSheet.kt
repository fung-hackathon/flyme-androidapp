package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.ui.theme.PrimaryBlack
import com.example.fung_p2hacks.ui.theme.PrimaryWhite

@Composable
fun BottomSheet() {
    FuNG_p2hacksTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Divider(
                    color = PrimaryWhite,
                    thickness = 5.dp,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, start = 125.dp, end = 125.dp)
                        .clip(RoundedCornerShape(3.dp))
                )

                FriendsWrappingComposable(
                    onClick = {
                        //TODO
                    }
                )

                BottomSheetItem(
                    text = "友だち申請",
                    onClick = {
                        //TODO
                    },
                    additionalItem = { PendingFriendsComposable(num = 0) } //TODO: make num variable
                )
                BottomSheetItem(
                    text = "プロフィール設定",
                    onClick = {
                        //TODO
                    }
                )
                BottomSheetItem(
                    text = "ログアウト",
                    onClick = {
                        //TODO
                    }
                )
            }
        }
    }
}


//onClick for Surface is in ExperimentalMaterialApi.
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FriendsWrappingComposable(
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colors.background
    ) {
        AddingFriendsComposable()
    }
}

@Composable
fun AddingFriendsComposable() {
    Box(
        modifier = Modifier
    ) {
        Image(
            painter = painterResource(R.drawable.friendaddingbox),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 10.dp)
        ) {
            Text(
                text = "友だちを追加",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
            Image(
                painter = painterResource(R.drawable.chevron_down),
                contentDescription = null,
                modifier = Modifier
                    .rotate(270F)
                    .size(45.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetItem(
    text: String,
    onClick: () -> Unit,
    additionalItem: @Composable (() -> Unit) = {}
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Text(text = text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.padding(5.dp))
                additionalItem()
            }

            Image(
                painter = painterResource(R.drawable.chevron_down),
                contentDescription = null,
                modifier = Modifier
                    .rotate(270F)
                    .size(20.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun PendingFriendsComposable(num: Int) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(25.dp)),
        color = PrimaryWhite,
        contentColor = PrimaryBlack
    ) {
        if (num > 0) {
            Text(
                text = "新着${num}人",
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = PrimaryBlack,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun friendBoxPreview() {
    FuNG_p2hacksTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            AddingFriendsComposable()
        }
    }
}