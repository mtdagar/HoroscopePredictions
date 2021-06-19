package com.mtdagar.horoscopepredictions

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class MainActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, splashTimeOut)

    }
}