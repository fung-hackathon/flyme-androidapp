package com.example.fung_p2hacks.HomeScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.ui.theme.Purple200

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuNG_p2hacksTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background){
                    Root()
                }
            }
        }
    }
}

@Composable
fun Root() {
    Column {
        TopBarComposable()
//        TicketComposable()
//        FriendsActivityComposable()
//        MyHistoryComposable()
    }
}