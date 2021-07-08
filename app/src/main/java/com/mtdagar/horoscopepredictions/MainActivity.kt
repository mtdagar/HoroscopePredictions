package com.mtdagar.horoscopepredictions

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class MainActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 1000
    private lateinit var repository: HoroRepository
    private val networking: Networking = Networking()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(applicationContext);

        repository = HoroRepository()

        //if internet connected then load stories in splash
        //else exit splash
        if(networking.isNetworkConnected()){
            GlobalScope.launch(Dispatchers.IO) {
                repository.loadFirstStories()
            }

            Handler().postDelayed(Runnable {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }, splashTimeOut)
        }else{
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }

}

