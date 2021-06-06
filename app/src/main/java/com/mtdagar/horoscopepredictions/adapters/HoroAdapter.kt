package com.mtdagar.horoscopepredictions.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.mtdagar.horoscopepredictions.MainActivity
import com.mtdagar.horoscopepredictions.R
import com.mtdagar.horoscopepredictions.models.HoroItem
import com.mtdagar.horoscopepredictions.models.HoroStory
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import org.json.JSONObject
import java.util.*


class HoroAdapter(private val horoList: List<HoroItem>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<HoroAdapter.HoroViewHolder>(){

    lateinit var horoToday: HoroStory
    lateinit var horoTomorrow: HoroStory
    lateinit var horoYesterday: HoroStory

    var todayLoaded: Boolean = false
    var tomorrowLoaded: Boolean = false
    var yesterdayLoaded: Boolean = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent, false)

        return HoroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HoroViewHolder, position: Int) {
        val currentItem = horoList[position]

        val currentSign = currentItem.text

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentSign

        holder.cardView.setOnClickListener{

            holder.progressBar.visibility = View.VISIBLE

            val myStories = ArrayList<MyStory>()

            for (i in 0..2) {
                when(i){
                    0 -> {
                        val sign = currentItem.text.lowercase()
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
                                    var responseJsonString = gson.toJson(
                                        HoroStory(
                                        response.getString("color"),
                                        response.getString("compatibility"),
                                        response.getString("current_date"),
                                        response.getString("date_range"),
                                        response.getString("description"),
                                        response.getString("lucky_number"),
                                        response.getString("lucky_time"),
                                        response.getString("mood"))
                                    )

                                    horoToday = gson.fromJson(responseJsonString, HoroStory::class.java)

                                    //Log.i("Response Today ->", horoToday.toString())

                                    myStories.add(MyStory("C:\Android\HoroscopePredictions\app\src\main\res\drawable",
                                        Calendar.getInstance().time,
                                        "0: ${horoToday.description}"))

                                    todayLoaded = true
                                    if(todayLoaded && tomorrowLoaded && yesterdayLoaded)
                                        holder.progressBar.visibility = View.INVISIBLE

                                    signalChanged(currentSign, myStories)

                                }

                                override fun onError(error: ANError) {
                                    Log.e("Error", error.toString())
                                    Toast.makeText(MainActivity().applicationContext, "Error fetching data", Toast.LENGTH_SHORT).show()

                                }
                            });
                    }

                    1 -> {
                        val sign = currentItem.text.lowercase()
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
                                    var responseJsonString = gson.toJson(
                                        HoroStory(
                                            response.getString("color"),
                                            response.getString("compatibility"),
                                            response.getString("current_date"),
                                            response.getString("date_range"),
                                            response.getString("description"),
                                            response.getString("lucky_number"),
                                            response.getString("lucky_time"),
                                            response.getString("mood"))
                                    )


                                    horoTomorrow = gson.fromJson(responseJsonString, HoroStory::class.java)

                                    //Log.i("Response Tomorrow ->", horoTomorrow.toString())


                                    myStories.add(MyStory(MainActivity().getHoroImage(),
                                        Calendar.getInstance().time,
                                         "1: ${horoTomorrow.description}"))

                                    tomorrowLoaded = true
                                    if(todayLoaded && tomorrowLoaded && yesterdayLoaded)
                                        holder.progressBar.visibility = View.INVISIBLE

                                    signalChanged(currentSign, myStories)

                                }

                                override fun onError(error: ANError) {
                                    Log.e("Error", error.toString())
                                    Toast.makeText(MainActivity().applicationContext, "Error fetching data", Toast.LENGTH_SHORT).show()

                                }
                            });
                    }

                    2 -> {
                        val sign = currentItem.text.lowercase()
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
                                    var responseJsonString = gson.toJson(
                                        HoroStory(
                                            response.getString("color"),
                                            response.getString("compatibility"),
                                            response.getString("current_date"),
                                            response.getString("date_range"),
                                            response.getString("description"),
                                            response.getString("lucky_number"),
                                            response.getString("lucky_time"),
                                            response.getString("mood"))
                                    )

                                    horoYesterday = gson.fromJson(responseJsonString, HoroStory::class.java)

                                    //Log.i("Response Yesterday ->", horoYesterday.toString())

                                    myStories.add(MyStory(MainActivity().getHoroImage(),
                                        Calendar.getInstance().time,
                                        "2: ${horoYesterday.description}"))

                                    yesterdayLoaded = true
                                    if(todayLoaded && tomorrowLoaded && yesterdayLoaded)
                                        holder.progressBar.visibility = View.INVISIBLE

                                    signalChanged(currentSign, myStories)

                                }

                                override fun onError(error: ANError) {
                                    Log.e("Error", error.toString())
                                    Toast.makeText(MainActivity().applicationContext, "Error fetching data", Toast.LENGTH_SHORT).show()

                                }
                            });
                    }

                }

            }

        }

    }

    override fun getItemCount() = horoList.size

    class HoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.card_image)
        val textView: TextView = itemView.findViewById(R.id.card_text)
        val cardView: CardView = itemView.findViewById(R.id.horo_card)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    private fun showStories(sign: String, list: ArrayList<MyStory>){
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
    }

    fun signalChanged(sign: String, list: ArrayList<MyStory>){
        if(todayLoaded && tomorrowLoaded && yesterdayLoaded) showStories(sign, list)
    }

}