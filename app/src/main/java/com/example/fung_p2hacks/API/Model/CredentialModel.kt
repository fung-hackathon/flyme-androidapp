package com.example.fung_p2hacks.API.Model

import com.google.gson.annotations.SerializedName

data class CredentialModel(
    @SerializedName("token") val token: String
)

data class LogInUser(
    @SerializedName("userID") val userID: String,
    @SerializedName("passwd") val passwd: String
)