package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fung_p2hacks.ui.theme.PrimaryBlack
import com.example.fung_p2hacks.ui.theme.PrimaryWhite

@Composable
fun UserHistoryComposable() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "おさんぽのりれき")

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.height(30.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryWhite,
                    contentColor = PrimaryBlack
                )
            ) {
                Text(
                    text = "すべて表示",
                    color = PrimaryBlack,
                    fontSize = 11.sp
                )
            }
        }
    }
}