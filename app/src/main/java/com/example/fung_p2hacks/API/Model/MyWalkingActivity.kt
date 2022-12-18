package com.example.fung_p2hacks.API.Model

data class MyWalkingActivity(
    val user: User,
    val state: String,
    val start: String,
    val ticket: String,
    val finish: String,
    val dist: Float
)