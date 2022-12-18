package com.example.fung_p2hacks.API

import com.example.fung_p2hacks.API.Model.CredentialModel
import com.example.fung_p2hacks.API.Model.LogInUser
import com.example.fung_p2hacks.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface FlymeAPI {
    @GET("/ping")
    suspend fun ping(): Call<String>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/login")
    suspend fun userLogIn(@Body post: LogInUser): Response<CredentialModel>

    @GET("/icon/{userID}")
    suspend fun getUserIcon(@Path("userID") userID: String): Response<String>

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