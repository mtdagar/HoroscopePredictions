package com.mtdagar.horoscopepredictions.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANResponse
import com.androidnetworking.common.Priority
import com.google.gson.Gson
import com.mtdagar.horoscopepredictions.model.Horo
import org.json.JSONObject


class Networking {

    suspend fun fetchData(sign: String, day: String): Horo? {
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
            var result = response.result
            val gson = Gson()
            response.result.put("day", day)
            response.result.put("sign", sign)

            val horoData = gson.fromJson(result.toString(), Horo::class.java)

            Log.i("NetworkResponse", horoData.toString())
            return horoData

        } else {
            Log.i("Response failed: ", response.error.toString())
            return null
        }


    }
}