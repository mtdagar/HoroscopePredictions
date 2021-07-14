package com.mtdagar.horoscopepredictions

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.viewmodel.StoryViewModel
import jp.shts.android.storiesprogressview.StoriesProgressView

//convert to custom view

class StoryActivity : AppCompatActivity() {

    private lateinit var storyViewModel: StoryViewModel
    private val networking: Networking = Networking()

    private lateinit var storyView: StoryView


    var firstHoro: Horo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_story)


        storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        setContentView(R.layout.activity_story)

        //horo from intent
        firstHoro = intent.extras?.getParcelable("firstStory") as Horo?
        storyViewModel.horoToday = firstHoro

        storyView = findViewById(R.id.storyView)


        storyViewModel.loading.observe(this, androidx.lifecycle.Observer{
            storyView.setProgressBarVisibility(it)
        })

        //start loading next stories
        if(networking.isNetworkConnected()){
            storyViewModel.loadTomorrow(firstHoro?.sign!!)
            storyViewModel.loadYesterday(firstHoro?.sign!!)
        }

        storyViewModel.tomorrowLoaded.observe(this, androidx.lifecycle.Observer {
            if(it && storyViewModel.counter==1){
                storyView.setStory(storyViewModel.horoTomorrow, storyViewModel.counter)
            }else if(it==false && storyViewModel.counter==1){
                if(networking.isNetworkConnected()) {
                    storyViewModel.loadTomorrow(firstHoro?.sign!!)
                }else{
                    Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        storyViewModel.yesterdayLoaded.observe(this, androidx.lifecycle.Observer {
            if(it && storyViewModel.counter==2){
                storyView.setStory(storyViewModel.horoYesterday, storyViewModel.counter)
            }else if(it==false && storyViewModel.counter==2){
                if(networking.isNetworkConnected()) {
                    storyViewModel.loadYesterday(firstHoro?.sign!!)
                }else{
                    Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        //set first story
        storyView.setStory(firstHoro, storyViewModel.counter)

    }

}