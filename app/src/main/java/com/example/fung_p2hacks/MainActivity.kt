package com.example.fung_p2hacks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.fung_p2hacks.HomeScreen.HomeActivity
import com.example.fung_p2hacks.LandingScreen.LandingActivity
import com.example.fung_p2hacks.WalkingScreen.WalkingActivity
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isUserLoggedIn = LocalContext.current
                .getSharedPreferences(stringResource(R.string.is_logged_in), Context.MODE_PRIVATE)?.getBoolean(stringResource(R.string.is_logged_in), false)
                ?: false

            FuNG_p2hacksTheme {
                Surface (color = MaterialTheme.colors.background) {
                    LocalContext.current.startActivity(
                        Intent(
                            LocalContext.current,
                            if (isUserLoggedIn)
                                HomeActivity::class.java
                            else
                                LandingActivity::class.java
                        )
                    )
                    (LocalContext.current as Activity).finish()
                }
            }
        }
    }
}