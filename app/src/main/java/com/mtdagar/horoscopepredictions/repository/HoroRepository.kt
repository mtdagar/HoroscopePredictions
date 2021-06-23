package com.mtdagar.horoscopepredictions.repository

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

    private val networking: Networking = Networking()
    private val firstStory = ArrayList<Horo>()
    val readAllData: LiveData<List<Horo>> = horoDao.readAllData()

    private val signs = arrayOf(
        "aries", "virgo", "taurus", "cancer", "gemini", "libra",
        "sagittarius", "aquarius", "pisces", "capricorn", "scorpio",
        "leo"
    )

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

    fun loadFirstStories() {
        for (i in 0..11) {
            var day = "today"
            var sign = signs[i]

            GlobalScope.launch(Dispatchers.IO) {
                val horo = getStory(sign, day)
                if (horo != null) {
                    Log.i("loadFirstStories", signs[i] + ": " + horo.toString())
                    firstStory.add(horo)
                    Log.i("loadFirstStories", "data fetched for $sign $day")
                    addHoro(horo)
                    Log.i("loadFirstStories", "data stored for $sign $day")
                }else{
                    Log.e("Error", "Networking error -> Network.getStory returning null")
                }

                Log.i("loadFirstStories", "sizeOfArray: " + firstStory.size.toString())
            }

        }
    }

}