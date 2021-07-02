package com.mtdagar.horoscopepredictions.repository

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import com.mtdagar.horoscopepredictions.HoroApplication
import com.mtdagar.horoscopepredictions.data.HoroDatabase
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HoroRepository() {

    private val horoDao = HoroDatabase.getDatabase(HoroApplication.applicationContext()).horoDao()

    private val networking = Networking()
    private val firstStory = ArrayList<Horo>()

    private val signs = arrayOf(
        "aries", "virgo", "taurus", "cancer", "gemini", "libra",
        "sagittarius", "aquarius", "pisces", "capricorn", "scorpio",
        "leo"
    )

    fun getCurrentDate(): String {
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )
        val calendar = Calendar.getInstance()
        val month = monthName[calendar.get(Calendar.MONTH)]
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)

        return "$month $day,$year"
    }

    suspend fun addHoro(horo: Horo) {
        horoDao.addHoro(horo)
    }

    suspend fun deleteHoro(horo: Horo) {
        horoDao.deleteHoro(horo)
    }

    suspend fun deleteAllHoro() {
        horoDao.deleteAllHoro()
    }

    suspend fun readHoro(): List<Horo> {
        return withContext(Dispatchers.IO) { horoDao.readData() }
    }


    //network call
    suspend fun getStory(sign: String, day: String): Horo? {
        return networking.fetchData(sign, day)
    }

    suspend fun loadFirstStories() {
        val day = "today"
        val currentDate = getCurrentDate()

        val loadedData = readHoro()

        fun loadStories(){
            for (j in 0..11) {
                val sign = signs[j]

                GlobalScope.launch(Dispatchers.IO) {
                    val horo = getStory(sign, day)
                    if (horo != null) {
                        firstStory.add(horo)
                        addHoro(horo)
                        Log.i("loadFirstStories", "data stored for $sign $day")
                    } else {
                        Log.e("Error", "Networking error -> Network.getStory returning null")
                    }
                    Log.i("loadFirstStories", "sizeOfArray: " + firstStory.size.toString())
                }
            }
        }

        if(loadedData.isNotEmpty()){
            if(loadedData[0].currentDate != currentDate){

                deleteAllHoro()

                loadStories()
            }
        }else if(loadedData.isEmpty()){
            loadStories()
        }

    }

}