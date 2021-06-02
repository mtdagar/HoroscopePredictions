package com.mtdagar.horoscopepredictions

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(applicationContext);

        val horoList = dummyList(20)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.adapter = HoroAdapter(signList())
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.setHasFixedSize(true)  //for optimization


        //loadHoroscope()


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

        val client = OkHttpClient()

        AndroidNetworking.post("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=aquarius&day=today")
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


        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
            .addPathParameter("pageNumber", "0")
            .addQueryParameter("limit", "3")
            .addHeaders("token", "1234")
            .setTag("test")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    // do anything with response
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })

        /*val request = Request.Builder()
            .url("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=aquarius&day=today")
            .addHeader("x-rapidapi-key", "bfccabaa77mshfedf970e8177411p187c12jsnb66cf8271812")
            .addHeader("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error","Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body =  response?.body?.string()
                Log.i("Response", body.toString())
            }
        })*/
    }

}