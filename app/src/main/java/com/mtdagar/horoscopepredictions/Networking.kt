package com.mtdagar.horoscopepredictions

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANResponse
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

class Networking constructor(var progressBar: View) {

    interface NetworkingInterface {
        fun onResponse(sign: String, list: ArrayList<MyStory>, horoObject: HoroStory)

        fun onError(message: String)
    }

    private suspend fun fetchData(sign: String, day: String): HoroStory? {
        val response: ANResponse<JSONObject> =
            AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                .addHeaders(
                    "x-rapidapi-key",
                    "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812"
                )
                .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                .setPriority(Priority.MEDIUM)
                .build()
                .executeForJSONObject() as ANResponse<JSONObject>

        if (response.isSuccess) {
            val result = response.result
            val gson = Gson()
            return gson.fromJson(result.toString(), HoroStory::class.java)
        }else{
            Log.i("Response failed: ", response.toString())
            return null
        }
    }

    fun getStories(Sign: String, networkingInterface: NetworkingInterface) {
        val sign = Sign.lowercase()
        val myStories = ArrayList<MyStory>()
        lateinit var horoObject: HoroStory

        for (i in 0..2) {
            var day: String = ""
            var image: String = ""

            when (i) {
                0 -> {
                    day = "today"
                    image =
                        "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image1.png"
                }
                1 -> {
                    day = "tomorrow"
                    image =
                        "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image2.png"
                }
                2 -> {
                    day = "yesterday"
                    image =
                        "https://raw.githubusercontent.com/mtdagar/HoroscopePredictions/main/app/src/main/res/drawable/image3.png"
                }
            }

            val response: ANResponse<JSONObject> =
                AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                    .addHeaders(
                        "x-rapidapi-key",
                        "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812"
                    )
                    .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .executeForJSONObject() as ANResponse<JSONObject>

            if (response.isSuccess) {
                val result = response.result
                val gson = Gson()

                val horoData = gson.fromJson(result.toString(), HoroStory::class.java)
                horoObject = horoData
                myStories.add(
                    MyStory(
                        image,
                        Calendar.getInstance().time,
                        "$day: ${horoData.description}"
                    )
                )
            }else{
             Log.i("Response failed: ", response.toString())
            }
        }

        networkingInterface.onResponse(sign, myStories, horoObject)

    }
}