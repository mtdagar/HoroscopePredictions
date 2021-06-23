package com.mtdagar.horoscopepredictions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.model.Horo
import kotlinx.coroutines.*

class HoroViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Horo>>
    private val repository: HoroRepository
    private val firstStory = ArrayList<Horo?>()


    init{

        repository = HoroRepository()
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


    fun getFirstStories(): ArrayList<Horo?>{
        return firstStory
    }

    suspend fun readData(): List<Horo>{
        return repository.readHoro()
    }

}