package com.mtdagar.horoscopepredictions.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "horo_table")
data class Horo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var color: String?,

    val compatibility: String?,
    @SerializedName(value = "current_date")
    val currentDate: String?,
    @SerializedName(value = "date_range")
    val dateRange: String?,

    val description: String?,
    @SerializedName(value = "lucky_number")
    val luckyNumber: String?,
    @SerializedName(value = "lucky_time")
    val luckyTime: String?,

    val mood: String?,

    val day: String?,

    val sign: String?
)