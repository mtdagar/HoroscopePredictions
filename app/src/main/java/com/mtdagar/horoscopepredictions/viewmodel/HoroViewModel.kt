package com.mtdagar.horoscopepredictions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mtdagar.horoscopepredictions.data.HoroDatabase
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.model.Horo
import kotlinx.coroutines.*

class HoroViewModel(application: Application): AndroidViewModel(application) {

    private val repository: HoroRepository = HoroRepository()
    private val firstStories = ArrayList<Horo?>()


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

    suspend fun readData(): List<Horo>{
        return repository.readHoro()
    }

}