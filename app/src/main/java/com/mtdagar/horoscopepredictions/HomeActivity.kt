package com.mtdagar.horoscopepredictions

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.horoscopepredictions.adapters.HoroAdapter
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.model.HoroItem
import com.mtdagar.horoscopepredictions.model.HoroStory
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.viewmodel.HoroViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory

/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class HomeActivity : AppCompatActivity(), HomeActivityInterface {

    private lateinit var mHoroViewModel: HoroViewModel
    private lateinit var repository: HoroRepository
    private val networking: Networking = Networking()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HoroscopePredictions)    //discard splash screen
        mHoroViewModel = ViewModelProvider(this).get(HoroViewModel::class.java)




//        lifecycleScope.launch(Dispatchers.IO){
//            mHoroViewModel.loadFirstStories()
//        }

        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                Log.i("stored", mHoroViewModel.readData().size.toString())
                Log.i("stored", mHoroViewModel.readData().toString())
            }
        }

        recyclerView.adapter = HoroAdapter(signList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)  //for optimization
    }


        private fun signList(): List<HoroItem>{
        val list = ArrayList<HoroItem>()
        for(i in 0 until 11){
            var drawable = R.drawable.ic_horoscope_card
            var cardText = "Zodiac sign"
            when(i){
                0 -> {drawable = R.drawable.ic_aries_1
                    cardText = "Aries"
                }
                1 -> {drawable = R.drawable.ic_virgo_1
                    cardText = "Virgo"
                }
                2 -> {drawable = R.drawable.ic_taurus_1
                    cardText = "Taurus"
                }
                3 -> {drawable = R.drawable.ic_cancer_1
                    cardText = "Cancer"
                }
                4 -> {drawable = R.drawable.ic_gemini_1
                    cardText = "Gemini"
                }
                5 -> {drawable = R.drawable.ic_libra_1
                    cardText = "Libra"
                }
                6 -> {drawable = R.drawable.ic_sagittarius_1
                    cardText = "Sagittarius"
                }
                7 -> {drawable = R.drawable.ic_aquarius_1
                    cardText = "Aquarius"
                }
                8 -> {drawable = R.drawable.ic_pisces_1
                    cardText = "Pisces"
                }
                9 -> {drawable = R.drawable.ic_capricornus_1
                    cardText = "Capricorn"
                }
                10 -> {drawable = R.drawable.ic_scorpio_1
                    cardText = "Scorpio"
                }
                11 -> {drawable = R.drawable.ic_leo_1
                    cardText = "Leo"
                }
            }
            val item = HoroItem(drawable, cardText)
            list += item
        }

        return list
    }

    override fun popStory(sign: String, list: ArrayList<MyStory>, horoObject: HoroStory){
        var logoUrl: String = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.aquarius_1}").toString()
        when(sign){
            "aquarius" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.aquarius_1}").toString()
            "aries" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.aries_1}").toString()
            "cancer" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.cancer_1}").toString()
            "capricorn" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.capricornus_1}").toString()
            "gemini" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.gemini_1}").toString()
            "leo" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.leo_1}").toString()
            "libra" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.libra_1}").toString()
            "pisces" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.pisces_1}").toString()
            "sagittarius" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.sagittarius_1}").toString()
            "scorpio" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.scorpio_1}").toString()
            "taurus" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.taurus_1}").toString()
            "virgo" -> logoUrl = Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.virgo_1}").toString()
        }

        StoryView.Builder(supportFragmentManager)
            .setStoriesList(list) // Required
            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
            .setTitleText(sign) // Default is Hidden
            .setSubtitleText("Compatibility: ${horoObject.compatibility}") // Default is Hidden
            .setTitleLogoUrl(logoUrl) // Default is Hidden
            .setStoryClickListeners(object : StoryClickListeners {
                override fun onDescriptionClickListener(position: Int) {
                    //your action
                }

                override fun onTitleIconClickListener(position: Int) {
                    //your action
                }
            }) // Optional Listeners
            .build() // Must be called before calling show method
            .show()
    }

    private fun insertDataToDatabase(){
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
            repository.addHoro(horo)
        }

        Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()

    }
}

interface HomeActivityInterface{
    fun popStory(sign: String, list: java.util.ArrayList<MyStory>, horoObject: HoroStory)
}