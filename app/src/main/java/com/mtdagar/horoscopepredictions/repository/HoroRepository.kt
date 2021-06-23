package com.mtdagar.horoscopepredictions.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtdagar.horoscopepredictions.HoroApplication
import com.mtdagar.horoscopepredictions.Networking
import com.mtdagar.horoscopepredictions.data.HoroDao
import com.mtdagar.horoscopepredictions.data.HoroDatabase
import com.mtdagar.horoscopepredictions.model.Horo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HoroRepository() {

    private val horoDao = HoroDatabase.getDatabase(HoroApplication.applicationContext()).horoDao()

    private val networking: Networking = Networking()
    val readAllData: LiveData<List<Horo>> = horoDao.readAllData()
    private val firstStory = ArrayList<Horo>()

    suspend fun addHoro(horo: Horo){
        horoDao.addHoro(horo)
    }

    suspend fun deleteHoro(horo: Horo){
        horoDao.deleteHoro(horo)
    }

    suspend fun deleteAllHoro(){
        horoDao.deleteAllHoro()
    }

    suspend fun readHoro(): List<Horo>{
        return withContext(Dispatchers.IO){horoDao.readData()}
    }


    //network call
    suspend fun getStory(sign: String, day: String): Horo?{
         return networking.fetchData(sign, day)
    }

    fun loadFirstStories(){

        for (i in 0..11) {
            var day = "today"
            var sign: String

            when(i){
                0 -> {
                    sign = "aries"
                    GlobalScope.launch(Dispatchers.IO) {
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            Log.i("data1", horo.toString())
                            firstStory.add(horo)
                            Log.i("list", firstStory.toString()+Thread.currentThread().name)
                            try {
                                addHoro(horo)
                            }catch (e: Exception){
                                Log.i("exception", e.printStackTrace().toString())
                            }

                            Log.i("dataStored", horoDao.readData().toString())
                        }

                        Log.i("Size0", firstStory.size.toString())
                    }

                }
                1 -> {
                    sign = "virgo"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size1", firstStory.size.toString())
                    }
                }
                2 -> {
                    sign = "taurus"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size2", firstStory.size.toString())
                    }
                }
                3 -> {
                    sign = "cancer"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size3", firstStory.size.toString())
                    }
                }
                4 -> {
                    sign = "gemini"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size4", firstStory.size.toString())
                    }
                }
                5 -> {
                    sign = "libra"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size5", firstStory.size.toString())
                    }
                }
                6 -> {
                    sign = "sagittarius"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size6", firstStory.size.toString())
                    }
                }
                7 -> {
                    sign = "aquarius"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size7", firstStory.size.toString())
                    }
                }
                8 -> {
                    sign = "pisces"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size8", firstStory.size.toString())
                    }
                }
                9 -> {
                    sign = "capricorn"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size9", firstStory.size.toString())
                    }
                }
                10 -> {
                    sign = "scorpio"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size10", firstStory.size.toString())
                    }
                }
                11 -> {
                    sign = "leo"
                    GlobalScope.launch(Dispatchers.IO){
                        val horo = getStory(sign, day)
                        if (horo != null) {
                            firstStory.add(horo)
                            horoDao.addHoro(horo)
                        }
                        Log.i("Size11", firstStory.size.toString())
                    }
                }
            }

        }
        GlobalScope.launch(Dispatchers.IO){
            Log.i("stored", horoDao.readData().toString())
        }


    }


    fun insertDataToDatabase(){
        val color: String = "red"
        val compatibility: String = "single"
        val currentDate: String = "idk"
        val dateRange: String = "idk"
        val description: String = "idk"
        val luckyNumber: String = "idk"
        val luckyTime: String = "idk"
        val mood: String = "idk"
        val day: String = "today"
        val sign: String = "libra"

        val horo = Horo(0, color, compatibility, currentDate, dateRange, description, luckyNumber, luckyTime, mood, day, sign)
        GlobalScope.launch(Dispatchers.IO) {
            addHoro(horo)
            Log.i("stored", horoDao.readData().toString())
        }



    }


}