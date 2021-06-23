package com.mtdagar.horoscopepredictions

import android.app.Application
import android.content.Context


//class to get application context from non Application classes
class HoroApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: HoroApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        val context: Context = HoroApplication.applicationContext()
    }


}