package com.mtdagar.horoscopepredictions

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.mtdagar.horoscopepredictions.adapters.HoroAdapter
import com.mtdagar.horoscopepredictions.models.HoroItem
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainActivityInterface {


    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        //progressBar = findViewById(R.id.progressBar)

        recyclerView.adapter = HoroAdapter(signList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)  //for optimization


        AndroidNetworking.initialize(applicationContext);

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

    override fun popStory(sign: String, list: ArrayList<MyStory>){
        StoryView.Builder(supportFragmentManager)
            .setStoriesList(list) // Required
            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
            .setTitleText(sign) // Default is Hidden
            .setSubtitleText("") // Default is Hidden
            .setTitleLogoUrl("") // Default is Hidden
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

        //progressBar.visibility = View.INVISIBLE

    }

}

interface MainActivityInterface{
    fun popStory(sign: String, list: java.util.ArrayList<MyStory>)
}