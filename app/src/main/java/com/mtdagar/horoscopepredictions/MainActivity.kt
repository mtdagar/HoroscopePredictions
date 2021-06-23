package com.mtdagar.horoscopepredictions

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class MainActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 5000
    private lateinit var repository: HoroRepository
    private val networking: Networking = Networking()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(applicationContext);

        repository = HoroRepository()

        repository.loadFirstStories()


        Handler().postDelayed(Runnable {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, splashTimeOut)

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

