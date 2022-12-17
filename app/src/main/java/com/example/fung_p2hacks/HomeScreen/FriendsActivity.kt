package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme

@Composable
fun FriendsActivityListComposable() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
        Text(text = "友達のおさんぽ")
        FriendColumn()
    }
}

@Composable
fun FriendColumn() {
    LazyColumn() {
        //TODO: Implement expandable lazy list
    }
}

@Composable
fun FriendActivityComposable(friendName: String, destination: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FriendActivityLeft(friendName = friendName)
        Text(text = destination, fontWeight = FontWeight.Black, fontSize = 20.sp)
    }
}

@Composable
fun FriendActivityLeft(friendName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(Color.Red)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Column (
            modifier = Modifier.size(width = Dp.Infinity, height = 45.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = friendName, fontWeight = FontWeight.Bold)
            Text(text = "1日前", fontSize = 12.sp) //TODO: Make text variable
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendPreview() {
    FuNG_p2hacksTheme {
        Surface (color = MaterialTheme.colors.background) {
            FriendActivityComposable("Yourein", "MOON")
        }
    }
}