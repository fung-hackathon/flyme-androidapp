package com.example.fung_p2hacks.HomeScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fung_p2hacks.BuildConfig
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme

@Composable
fun TopBarComposable(
    onIconClick: () -> Unit
) {
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
    val userId = LocalContext.current
        .getSharedPreferences(stringResource(R.string.user_id), Context.MODE_PRIVATE)?.getString(stringResource(R.string.user_id), "")
        ?: ""
    val userToken = LocalContext.current
        .getSharedPreferences(stringResource(R.string.api_access_token), Context.MODE_PRIVATE)?.getString(stringResource(R.string.api_access_token), "")
        ?: ""
    val iconUrl = BuildConfig.API_URL + "/icon/" + userId

    /*
    //For debugging. Uncomment if you want.
    println(userId)
    println(userToken)
    println(iconUrl)
     */

    Surface(
        onClick = onClicked,
        color = MaterialTheme.colors.background
    ) {
        if (userToken == "") {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White))
        }
        else {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .addHeader("Authorization", "Bearer $userToken")
                .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
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