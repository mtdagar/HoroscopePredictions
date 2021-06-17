package com.mtdagar.horoscopepredictions.data

import androidx.lifecycle.LiveData

class HoroRepository(private val horoDao: HoroDao) {

    val readAllData: LiveData<List<Horo>> = horoDao.readAllData()

    suspend fun addHoro(horo: Horo){
        horoDao.addHoro(horo)
    }

}