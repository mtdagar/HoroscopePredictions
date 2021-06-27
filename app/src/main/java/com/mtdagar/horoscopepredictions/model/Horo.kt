package com.mtdagar.horoscopepredictions.model

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(color)
        parcel.writeString(compatibility)
        parcel.writeString(currentDate)
        parcel.writeString(dateRange)
        parcel.writeString(description)
        parcel.writeString(luckyNumber)
        parcel.writeString(luckyTime)
        parcel.writeString(mood)
        parcel.writeString(day)
        parcel.writeString(sign)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Horo> {
        override fun createFromParcel(parcel: Parcel): Horo {
            return Horo(parcel)
        }

        override fun newArray(size: Int): Array<Horo?> {
            return arrayOfNulls(size)
        }
    }
}