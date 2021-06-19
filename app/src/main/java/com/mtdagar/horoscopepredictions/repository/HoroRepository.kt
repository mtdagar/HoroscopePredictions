package com.mtdagar.horoscopepredictions.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtdagar.horoscopepredictions.Networking
import com.mtdagar.horoscopepredictions.data.HoroDao
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.model.HoroStory


class HoroRepository(private val horoDao: HoroDao) {

    private val networking: Networking = Networking()
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

    //network call
    suspend fun getStory(sign: String, day: String): HoroStory{
         return networking.fetchData(sign, day)
    }





}