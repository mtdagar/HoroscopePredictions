package com.mtdagar.horoscopepredictions.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HoroDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHoro(horo: Horo)

    @Query("SELECT * FROM horo_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Horo>>

}