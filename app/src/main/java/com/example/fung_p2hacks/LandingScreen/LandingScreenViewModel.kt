package com.example.fung_p2hacks.LandingScreen

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fung_p2hacks.API.FlymeAPI
import com.example.fung_p2hacks.API.Model.LogInUser
import com.example.fung_p2hacks.BuildConfig
import com.example.fung_p2hacks.R
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LandingScreenViewModel: ViewModel() {
    fun tryLogIn(userid: String, password: String, context: Context) {
        var token: String? = null

        viewModelScope.launch {
            val api = FlymeAPI.Instance()
            val logInUser: LogInUser = LogInUser(userid, password)
            try {
                val res = api.userLogIn(logInUser)
                if (res.code() == 200) {
                    println("Credential: ${res.body()!!.token}")
                    token = res.body()!!.token

                    val idPref = context.getSharedPreferences(context.getString(R.string.user_id), Context.MODE_PRIVATE)
                    val pasPref = context.getSharedPreferences(context.getString(R.string.user_password), Context.MODE_PRIVATE)
                    val tokenPref = context.getSharedPreferences(context.getString(R.string.api_access_token), Context.MODE_PRIVATE)

                    with(idPref.edit()) {
                        putString("user_id", userid)
                        apply()
                    }

                    with(pasPref.edit()) {
                        putString("user_password", password)
                        apply()
                    }

                    with(tokenPref.edit()) {
                        putString("current_token", token)
                        apply()
                    }
                } else {
                    println("Error!: ${res.raw()}")
                }
            } catch (e: Exception) {
                println("Error!: ${e}")
            }
        }
    }
}