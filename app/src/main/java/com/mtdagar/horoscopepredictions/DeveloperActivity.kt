package com.mtdagar.horoscopepredictions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HoroscopePredictions)    //discard splash screen
        setContentView(R.layout.activity_developer)
    }
}