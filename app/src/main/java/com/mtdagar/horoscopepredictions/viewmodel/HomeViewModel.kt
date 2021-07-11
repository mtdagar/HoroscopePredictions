package com.mtdagar.horoscopepredictions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtdagar.horoscopepredictions.R
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.model.HoroCardItem
import kotlinx.coroutines.*

class HomeViewModel: ViewModel() {

    private val horoCards: MutableLiveData<List<HoroCardItem>>
    private val repository = HoroRepository()
    private val firstStories = ArrayList<Horo?>()

//    https://i.ibb.co/cbdT0hb/virgo-1.webp
//    https://i.ibb.co/2hW3fr7/taurus-1.webp
//    https://i.ibb.co/DY9JvfB/scorpio-1.webp
//    https://i.ibb.co/ccJK82F/sagittarius-1.webp
//    https://i.ibb.co/QFBcg2M/pisces-1.webp
//    https://i.ibb.co/nRqLXgH/libra-1.webp
//    https://i.ibb.co/ZdRxMt9/leo-1.webp
//    https://i.ibb.co/YDQmKJz/gemini-1.webp
//    https://i.ibb.co/chZHrFP/capricornus-1.webp
//    https://i.ibb.co/wwHzRHS/cancer-1.webp
//    https://i.ibb.co/M9138KP/aries-1.webp
//    https://i.ibb.co/JzzVxyv/aquarius-1.webp



    init {
        horoCards = MutableLiveData<List<HoroCardItem>>(signList())
    }

    fun horoCards(): LiveData<List<HoroCardItem>>{
        return horoCards
    }

    private fun signList(): List<HoroCardItem> {

        val list: List<HoroCardItem> = listOf(
            HoroCardItem(R.drawable.ic_horoscope_card, "Aries"),
            HoroCardItem(R.drawable.ic_virgo_1, "Virgo"),
            HoroCardItem(R.drawable.ic_taurus_1, "Taurus"),
            HoroCardItem(R.drawable.ic_cancer_1, "Cancer"),
            HoroCardItem(R.drawable.ic_gemini_1, "Gemini"),
            HoroCardItem(R.drawable.ic_libra_1, "Libra"),
            HoroCardItem(R.drawable.ic_sagittarius_1, "Sagittarius"),
            HoroCardItem(R.drawable.ic_aquarius_1, "Aquarius"),
            HoroCardItem(R.drawable.ic_pisces_1, "Pisces"),
            HoroCardItem(R.drawable.ic_capricornus_1, "Capricorn"),
            HoroCardItem(R.drawable.ic_scorpio_1, "Scorpio"),
            HoroCardItem(R.drawable.ic_leo_1, "Leo"),
        )

        return list
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

    suspend fun readData(): List<Horo>{
        return repository.readHoro()
    }

}

