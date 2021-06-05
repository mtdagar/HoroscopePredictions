package com.mtdagar.horoscopepredictions

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.mtdagar.horoscopepredictions.adapters.HoroAdapter
import com.mtdagar.horoscopepredictions.models.HoroItem
import com.mtdagar.horoscopepredictions.models.HoroStory
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var horoToday: HoroStory
    lateinit var horoTomorrow: HoroStory
    lateinit var horoYesterday: HoroStory

    var loading: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.adapter = HoroAdapter(signList(), supportFragmentManager)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)  //for optimization


        AndroidNetworking.initialize(applicationContext);



    }

    private fun dummyList(size: Int): List<HoroItem>{
        val list = ArrayList<HoroItem>()
        for(i in 0 until size){
            val drawable = R.drawable.ic_horoscope_card
            val item = HoroItem(drawable, "Today's Horoscope")
            list += item
        }

        return list
    }

    fun fetchPredictions(sign: String){

        for(i in 0..2){

            when(i){
                0 -> {
                    val day = "today"

                    AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                        .addHeaders("x-rapidapi-key", "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812")
                        .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {

                                val gson = Gson()

                                //converting the weird JsonObject to Android readable Json object
                                var responseJsonString = gson.toJson(HoroStory(
                                    response.getString("color"),
                                    response.getString("compatibility"),
                                    response.getString("current_date"),
                                    response.getString("date_range"),
                                    response.getString("description"),
                                    response.getString("lucky_number"),
                                    response.getString("lucky_time"),
                                    response.getString("mood")))


                                horoToday = gson.fromJson(responseJsonString, HoroStory::class.java)

                                Log.i("Response Today ->", horoToday.toString())

                            }

                            override fun onError(error: ANError) {
                                Log.e("Error", error.toString())
                                Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                            }
                        });
                }

                1 -> {
                    val day = "tomorrow"

                    AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                        .addHeaders("x-rapidapi-key", "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812")
                        .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {

                                val gson = Gson()

                                //converting the weird JsonObject to Android readable Json object
                                var responseJsonString = gson.toJson(HoroStory(
                                    response.getString("color"),
                                    response.getString("compatibility"),
                                    response.getString("current_date"),
                                    response.getString("date_range"),
                                    response.getString("description"),
                                    response.getString("lucky_number"),
                                    response.getString("lucky_time"),
                                    response.getString("mood")))


                                horoTomorrow = gson.fromJson(responseJsonString, HoroStory::class.java)

                                Log.i("Response Tomorrow ->", horoTomorrow.toString())

                            }

                            override fun onError(error: ANError) {
                                Log.e("Error", error.toString())
                                Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                            }
                        });
                }

                2 -> {
                    val day = "yesterday"

                    AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
                        .addHeaders("x-rapidapi-key", "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812")
                        .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {

                                val gson = Gson()

                                //converting the weird JsonObject to Android readable Json object
                                var responseJsonString = gson.toJson(HoroStory(
                                    response.getString("color"),
                                    response.getString("compatibility"),
                                    response.getString("current_date"),
                                    response.getString("date_range"),
                                    response.getString("description"),
                                    response.getString("lucky_number"),
                                    response.getString("lucky_time"),
                                    response.getString("mood")))


                                horoYesterday = gson.fromJson(responseJsonString, HoroStory::class.java)

                                Log.i("Response Yesterday ->", horoYesterday.toString())

                            }

                            override fun onError(error: ANError) {
                                Log.e("Error", error.toString())
                                Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                            }
                        });
                }


            }
        }
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

    private fun loadHoroscope(){

        val day = "today"
        val sign = "libra"

        AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=$day")
            .addHeaders("x-rapidapi-key", "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812")
            .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val gson = Gson()

                    //converting the weird JsonObject to Android readable Json object
                    var responseJsonString = gson.toJson(HoroStory(
                        response.getString("color"),
                        response.getString("compatibility"),
                        response.getString("current_date"),
                        response.getString("date_range"),
                        response.getString("description"),
                        response.getString("lucky_number"),
                        response.getString("lucky_time"),
                        response.getString("mood")))


                    horoToday = gson.fromJson(responseJsonString, HoroStory::class.java)

                    Log.i("Response", horoToday.toString())

                }

                override fun onError(error: ANError) {
                    Log.e("Error", error.toString())
                }
            });

        /*AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=aquarius&day=today")
            .addHeaders("x-rapidapi-key", "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812")
            .addHeaders("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.i("Response", response.toString())
                }

                override fun onError(error: ANError) {
                    Log.e("Error", error.toString())
                }
            });

         */

    }

    fun getHoroImage(): String{
        val url = "https://i.imgur.com/UgW8oFM.jpg"
        return url
    }

    fun getHoroDescription(day: String): String{
        return when(day) {
            "today" -> horoToday.description
            "tomorrow" -> horoTomorrow.description
            "yesterday" -> horoYesterday.description
            else -> horoToday.description
        }
    }



}