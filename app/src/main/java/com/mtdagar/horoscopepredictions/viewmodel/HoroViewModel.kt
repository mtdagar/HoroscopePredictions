package com.mtdagar.horoscopepredictions.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mtdagar.horoscopepredictions.data.HoroDatabase
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.model.HoroStory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HoroViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Horo>>
    private val repository: HoroRepository
    private val firstStory = ArrayList<HoroStory>()


    init{
        val horoDao = HoroDatabase.getDatabase(application).horoDao()
        repository = HoroRepository(horoDao)
        readAllData = repository.readAllData
    }

    fun addHoro(horo: Horo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHoro(horo)
        }
    }

    fun deleteHoro(horo: Horo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHoro(horo)
        }
    }

    fun deleteAllHoro(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHoro()
        }
    }

    suspend fun loadFirstStories(){

        for (i in 0..11) {
            var day = "today"
            var sign: String

            when(i){
                0 -> {
                    sign = "aries"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }

                }
                1 -> {
                    sign = "virgo"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                2 -> {
                    sign = "taurus"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                3 -> {
                    sign = "cancer"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                4 -> {
                    sign = "gemini"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                5 -> {
                    sign = "libra"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                6 -> {
                    sign = "sagittarius"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                7 -> {
                    sign = "aquarius"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                8 -> {
                    sign = "pisces"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                9 -> {
                    sign = "capricorn"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                10 -> {
                    sign = "scorpio"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
                11 -> {
                    sign = "leo"
                    withContext(Dispatchers.IO){
                        firstStory.add(repository.getStory(sign, day))
                    }
                }
            }

        }

    }

    fun getFirstStories(): ArrayList<HoroStory>{
        return firstStory
    }

}