package com.mtdagar.horoscopepredictions.model

import com.google.gson.annotations.SerializedName

data class HoroStory(
    @SerializedName(value = "color")
    val color: String,
    @SerializedName(value = "compatibility")
    val compatibility: String,
    @SerializedName(value = "current_date")
    val currentDate: String,
    @SerializedName(value = "date_range")
    val dateRange: String,
    @SerializedName(value = "description")
    val description: String,
    @SerializedName(value = "lucky_number")
    val luckyNumber: String,
    @SerializedName(value = "lucky_time")
    val luckyTime: String,
    @SerializedName(value = "mood")
    val mood: String,
    @SerializedName(value = "day")
    val day: String,
    @SerializedName(value = "sign")
    val sign: String
)
//{
//    constructor() : this("null", "null",
//        "null", "null", "null",
//        "null", "null", "null",
//        "null", "null")
//}
