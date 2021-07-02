package com.mtdagar.horoscopepredictions.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel: ViewModel() {

    private val repository = HoroRepository()

    private val _tomorrowLoaded = MutableLiveData<Boolean>()
    val tomorrowLoaded: LiveData<Boolean> = _tomorrowLoaded

    var horoToday: Horo? = null
    var horoTomorrow: Horo? = null
    var horoYesterday: Horo? = null


    var yesterdayLoaded = false



    fun loadTomorrow(sign: String){
        viewModelScope.launch(Dispatchers.IO) {
            horoTomorrow = repository.getStory(sign, "tomorrow")
            _tomorrowLoaded.postValue(true)
        }
    }

}