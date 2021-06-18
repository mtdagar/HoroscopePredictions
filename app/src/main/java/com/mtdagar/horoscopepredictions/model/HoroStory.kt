package com.mtdagar.horoscopepredictions.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HoroStory(
    @Json(name = "color")
    val color: String,
    @Json(name = "compatibility")
    val compatibility: String,
    @Json(name = "current_date")
    val currentDate: String,
    @Json(name = "date_range")
    val dateRange: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "lucky_number")
    val luckyNumber: String,
    @Json(name = "lucky_time")
    val luckyTime: String,
    @Json(name = "mood")
    val mood: String
)