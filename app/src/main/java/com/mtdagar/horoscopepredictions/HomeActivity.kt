package com.mtdagar.horoscopepredictions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.horoscopepredictions.adapters.HoroAdapter
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import java.util.*
import kotlin.collections.ArrayList


/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class HomeActivity : AppCompatActivity(), HomeActivityInterface {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var repository: HoroRepository
    private val networking: Networking = Networking()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HoroscopePredictions)    //discard splash screen
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)



        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {

            homeViewModel.test()

//            GlobalScope.launch(Dispatchers.IO) {
//                Log.i("stored", homeViewModel.readData().size.toString())
//                Log.i("stored", homeViewModel.readData().toString())
//            }
        }

        homeViewModel.horoCards().observe(this, androidx.lifecycle.Observer {
            recyclerView.adapter = HoroAdapter(it, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
        })




//        recyclerView.setHasFixedSize(true)  //for optimization
    }




    override fun showStory(sign: String) {

        var data: List<Horo>
        val myStories = ArrayList<MyStory>()
        val image =
            "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image3.png"
        var storyIndex: Int


//        lifecycleScope.launch {
//            data = mHomeViewModel.readData()
//
//            for(i in data){
//                if(i.sign == sign) {
//                    storyIndex = data.indexOf(i)
//
//                    val intent = Intent(this@HomeActivity, com.mtdagar.horoscopepredictions.StoryView::class.java)
//                    intent.putExtra("firstStory", data[storyIndex])
//                    startActivity(intent)
//                }
//            }
//        }
//
//
//
//
//        //read pre loaded first stories
//        data = mHomeViewModel.readData()
//
//            for(i in data){
//                if(i.sign == sign) {
//                    storyIndex = data.indexOf(i)
//
//                    val intent = Intent(this@HomeActivity, com.mtdagar.horoscopepredictions.StoryView::class.java)
//                    intent.putExtra("firstStory", data[storyIndex])
//                    startActivity(intent)
//                }
//            }
//
//        }

    }

//    fun showStoryOld(sign: String) {
//
//        var data: List<Horo>
//        val myStories = ArrayList<MyStory>()
//        val image =
//            "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image3.png"
//        var storyIndex: Int
//
//        GlobalScope.launch(Dispatchers.IO) {
//            //read pre loaded first stories
//            data = mHoroViewModel.readData()
//
//            for(i in data){
//                if(i.sign == sign) {
//                    storyIndex = data.indexOf(i)
//
//                    myStories.add(
//                        MyStory(
//                            image,
//                            Calendar.getInstance().time,
//                            "today: " + data[storyIndex].description
//                        )
//                    )
//                }
//            }
//
//
//        }
//
//
//        StoryView.Builder(supportFragmentManager)
//            .setStoriesList(myStories) // Required
//            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
//            .setTitleText(sign) // Default is Hidden
//            .setSubtitleText("Compatibility: {horoObject.compatibility}") // Default is Hidden
//            .setStoryClickListeners(object : StoryClickListeners {
//                override fun onDescriptionClickListener(position: Int) {
//                    //your action
//                }
//
//                override fun onTitleIconClickListener(position: Int) {
//                    //your action
//                }
//            }) // Optional Listeners
//            .build() // Must be called before calling show method
//            .show()
//
//    }

//    override fun popStory(sign: String, list: ArrayList<MyStory>, horoObject: Horo) {
//        var logoUrl: String =
//            Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.aquarius_1}")
//                .toString()
//        when (sign) {
//            "aquarius" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.aquarius_1}")
//                    .toString()
//            "aries" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.aries_1}")
//                    .toString()
//            "cancer" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.cancer_1}")
//                    .toString()
//            "capricorn" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.capricornus_1}")
//                    .toString()
//            "gemini" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.gemini_1}")
//                    .toString()
//            "leo" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.leo_1}")
//                    .toString()
//            "libra" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.libra_1}")
//                    .toString()
//            "pisces" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.pisces_1}")
//                    .toString()
//            "sagittarius" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.sagittarius_1}")
//                    .toString()
//            "scorpio" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.scorpio_1}")
//                    .toString()
//            "taurus" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.taurus_1}")
//                    .toString()
//            "virgo" -> logoUrl =
//                Uri.parse("android.resource://com.mtdagar.horoscopepredictions/${R.drawable.virgo_1}")
//                    .toString()
//        }
//
//        StoryView.Builder(supportFragmentManager)
//            .setStoriesList(list) // Required
//            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
//            .setTitleText(sign) // Default is Hidden
//            .setSubtitleText("Compatibility: ${horoObject.compatibility}") // Default is Hidden
//            .setTitleLogoUrl(logoUrl) // Default is Hidden
//            .setStoryClickListeners(object : StoryClickListeners {
//                override fun onDescriptionClickListener(position: Int) {
//                    //your action
//                }
//
//                override fun onTitleIconClickListener(position: Int) {
//                    //your action
//                }
//            }) // Optional Listeners
//            .build() // Must be called before calling show method
//            .show()
//    }

}

interface HomeActivityInterface {
    fun showStory(sign: String)
}