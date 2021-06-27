package com.mtdagar.horoscopepredictions.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mtdagar.horoscopepredictions.model.Horo

@Dao
interface HoroDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHoro(horo: Horo)

    @Delete
    fun deleteHoro(horo: Horo)

    @Query("DELETE FROM horo_table")
    fun deleteAllHoro()

    @Query("SELECT * FROM horo_table ORDER BY id ASC")
    fun readAllData(): List<Horo>

    @Query("SELECT * FROM horo_table ORDER BY id ASC")
    fun readData(): List<Horo>

}