package com.mtdagar.horoscopepredictions.repository

import androidx.lifecycle.LiveData
import com.mtdagar.horoscopepredictions.data.HoroDao
import com.mtdagar.horoscopepredictions.model.Horo

class HoroRepository(private val horoDao: HoroDao) {

    val readAllData: LiveData<List<Horo>> = horoDao.readAllData()

    suspend fun addHoro(horo: Horo){
        horoDao.addHoro(horo)
    }

    suspend fun deleteHoro(horo: Horo){
        horoDao.deleteHoro(horo)
    }

    suspend fun deleteAllHoro(){
        horoDao.deleteAllHoro()
    }




}