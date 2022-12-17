package com.example.fung_p2hacks.API.Model

data class ActivityModel(
    val state: String,
    val start: String,
    val finish: String,
    val ticket: String,
    val dist: Float,
    val coordinates: List<CoordinateModel>
)
