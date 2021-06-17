package com.mtdagar.horoscopepredictions.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "horo_table")
data class Horo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var color: String,
    val compatibility: String,
    val currentDate: String,
    val dateRange: String,
    val description: String,
    val luckyNumber: String,
    val luckyTime: String,
    val mood: String
)