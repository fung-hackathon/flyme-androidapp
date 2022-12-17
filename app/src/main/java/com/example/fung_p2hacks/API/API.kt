package com.example.fung_p2hacks.API

import com.example.fung_p2hacks.API.Model.CredentialModel
import com.example.fung_p2hacks.API.Model.LogInUser
import com.example.fung_p2hacks.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FlymeAPI {
    @GET("/ping")
    suspend fun ping(): Call<String>

    @POST("/login")
    fun userLogIn(@Body post: LogInUser): Call<CredentialModel>

    companion object {
        var flymeAPI: FlymeAPI? = null
        fun Instance(): FlymeAPI{
            if (flymeAPI == null){
                flymeAPI = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(FlymeAPI::class.java)
            }
            return flymeAPI!!
        }
    }
}