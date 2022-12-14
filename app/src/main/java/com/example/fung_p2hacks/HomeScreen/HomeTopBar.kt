package com.example.fung_p2hacks.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme

@Composable
fun TopBarComposable(onIconClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Flyme", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        TopIconComposable(
            onClicked = onIconClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopIconComposable(
    onClicked: () -> Unit
) {
    Surface(
        onClick = onClicked,
        color = MaterialTheme.colors.background
    ) {
        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Red))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FuNG_p2hacksTheme {
        Surface (color = MaterialTheme.colors.background) {
            TopBarComposable({ /*Dummy*/ })
        }
    }
}