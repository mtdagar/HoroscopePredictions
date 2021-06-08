package com.mtdagar.horoscopepredictions

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.mtdagar.horoscopepredictions.models.HoroStory
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import org.json.JSONObject
import java.util.*

class Networking constructor(var progressBar: View){

    lateinit var horoToday: HoroStory
    lateinit var horoTomorrow: HoroStory
    lateinit var horoYesterday: HoroStory

    var todayLoaded: Boolean = false
    var tomorrowLoaded: Boolean = false
    var yesterdayLoaded: Boolean = false

    fun getStories(Sign: String, fragmentManager: FragmentManager) {
        val sign = Sign.lowercase()

        val myStories = ArrayList<MyStory>()

        for (i in 0..2) {
            when (i) {
                0 -> {
                    val day = "today"

                    AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                        .addHeaders(
                            "x-rapidapi-key",
                            "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812"
                        )
                        .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {
                                val gson = Gson()
                                //converting the weird JsonObject to Android readable Json object
                                var responseJsonString = gson.toJson(
                                    HoroStory(
                                        response.getString("color"),
                                        response.getString("compatibility"),
                                        response.getString("current_date"),
                                        response.getString("date_range"),
                                        response.getString("description"),
                                        response.getString("lucky_number"),
                                        response.getString("lucky_time"),
                                        response.getString("mood")
                                    )
                                )

                                horoToday = gson.fromJson(responseJsonString, HoroStory::class.java)

                                Log.i("Response Today ->", horoToday.toString())

                                myStories.add(
                                    MyStory(
                                        "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image1.png",
                                        Calendar.getInstance().time,
                                        "Today: ${horoToday.description}"
                                    )
                                )

                                todayLoaded = true

                                if(dataLoaded()){
                                    popStory(sign, myStories, fragmentManager)
                                    resetData()
                                }

                            }

                            override fun onError(error: ANError) {
                                Log.e("Error", error.toString())
                                Toast.makeText(
                                    MainActivity().applicationContext,
                                    "Error fetching data",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        });
                }

                1 -> {
                    val day = "tomorrow"

                    AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                        .addHeaders(
                            "x-rapidapi-key",
                            "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812"
                        )
                        .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {
                                val gson = Gson()
                                //converting the weird JsonObject to Android readable Json object
                                var responseJsonString = gson.toJson(
                                    HoroStory(
                                        response.getString("color"),
                                        response.getString("compatibility"),
                                        response.getString("current_date"),
                                        response.getString("date_range"),
                                        response.getString("description"),
                                        response.getString("lucky_number"),
                                        response.getString("lucky_time"),
                                        response.getString("mood")
                                    )
                                )


                                horoTomorrow =
                                    gson.fromJson(responseJsonString, HoroStory::class.java)

                                Log.i("Response Tomorrow ->", horoTomorrow.toString())


                                myStories.add(
                                    MyStory(
                                        "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image2.png",
                                        Calendar.getInstance().time,
                                        "Tomorrow: ${horoTomorrow.description}"
                                    )
                                )

                                tomorrowLoaded = true

                                if(dataLoaded()){
                                    popStory(sign, myStories, fragmentManager)
                                    resetData()
                                }

                            }

                            override fun onError(error: ANError) {
                                Log.e("Error", error.toString())
                                Toast.makeText(
                                    MainActivity().applicationContext,
                                    "Error fetching data",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        });
                }

                2 -> {
                    val day = "yesterday"

                    AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                        .addHeaders(
                            "x-rapidapi-key",
                            "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812"
                        )
                        .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {
                                val gson = Gson()
                                //converting the weird JsonObject to Android readable Json object
                                var responseJsonString = gson.toJson(
                                    HoroStory(
                                        response.getString("color"),
                                        response.getString("compatibility"),
                                        response.getString("current_date"),
                                        response.getString("date_range"),
                                        response.getString("description"),
                                        response.getString("lucky_number"),
                                        response.getString("lucky_time"),
                                        response.getString("mood")
                                    )
                                )

                                horoYesterday =
                                    gson.fromJson(responseJsonString, HoroStory::class.java)

                                Log.i("Response Yesterday ->", horoYesterday.toString())

                                myStories.add(
                                    MyStory(
                                        "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image3.png",
                                        Calendar.getInstance().time,
                                        "Yesterday: ${horoYesterday.description}"
                                    )
                                )

                                yesterdayLoaded = true

                                if(dataLoaded()){
                                    popStory(sign, myStories, fragmentManager)
                                    resetData()
                                }

                            }

                            override fun onError(error: ANError) {
                                Log.e("Error", error.toString())
                                Toast.makeText(
                                    MainActivity().applicationContext,
                                    "Error fetching data",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        });
                }


            }
        }

    }

    fun dataLoaded(): Boolean{
        return todayLoaded && tomorrowLoaded && yesterdayLoaded
    }

    fun resetData(){
        todayLoaded = false
        tomorrowLoaded = false
        yesterdayLoaded = false
    }

    fun popStory(sign: String, list: ArrayList<MyStory>, fragmentManager: FragmentManager){
        StoryView.Builder(fragmentManager)
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

        progressBar.visibility = View.INVISIBLE

    }
}