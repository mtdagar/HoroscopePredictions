package com.mtdagar.horoscopepredictions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mtdagar.horoscopepredictions.data.HoroDatabase
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.model.Horo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HoroViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Horo>>
    private val repository: HoroRepository


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
            repository.deleteHoro()
        }
    }

}