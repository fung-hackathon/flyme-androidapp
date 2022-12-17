package com.example.fung_p2hacks.LandingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fung_p2hacks.API.FlymeAPI
import com.example.fung_p2hacks.API.Model.LogInUser
import com.example.fung_p2hacks.BuildConfig
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LandingScreenViewModel: ViewModel() {
    fun tryLogIn(userid: String, password: String) {
        viewModelScope.launch {
            val api = FlymeAPI.Instance()
            val logInUser: LogInUser = LogInUser("fun", "string")


            val res = api.userLogIn(logInUser).execute()
            if (res.code() == 200) {
                println("Credential: ${res.body()!!.token}")
            }
            else {
                println("Error!: ${res.raw()}")
            }
//
//            val api2 = Retrofit.Builder().baseUrl(BuildConfig.API_URL).build().create(FlymeAPI::class.java)
//
//            try {
//                val res = api2.ping().execute()
//                if (res.code() == 200) {
//                    println(res.raw().message)
//                }
//            } catch (e: Exception) {
//                println("Error!: ${e}")
//            }
        }
    }
}