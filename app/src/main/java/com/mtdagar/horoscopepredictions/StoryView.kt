package com.mtdagar.horoscopepredictions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.viewmodel.StoryViewModel
import jp.shts.android.storiesprogressview.StoriesProgressView

//load images from glide
//convert to custom view

class StoryView : AppCompatActivity(), StoriesProgressView.StoriesListener {

    private lateinit var storyViewModel: StoryViewModel
    private val networking: Networking = Networking()

    private var storiesProgressView: StoriesProgressView? = null
    private lateinit var storyDescription: TextView
    private lateinit var storyProgressBar: ProgressBar
    private lateinit var storySignImage: ImageView
    private lateinit var storySignName: TextView
    private lateinit var storyDateRange: TextView
    private lateinit var storyDay: TextView


    private var storiesCount = 3

    var firstHoro: Horo? = null

    var pressTime = 0L
    var limit = 500L


    private val onTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            // inside on touch method we are
            // getting action on below line.
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    // on action down when we press our screen
                    // the story will pause for specific time.
                    pressTime = System.currentTimeMillis()

                    // on below line we are pausing our indicator.
                    storiesProgressView?.pause()
                    return false
                }
                MotionEvent.ACTION_UP -> {

                    // in action up case when user do not touches
                    // screen this method will skip to next image.
                    val now = System.currentTimeMillis()

                    // on below line we are resuming our progress bar for status.
                    storiesProgressView!!.resume()

                    // on below line we are returning if the limit < now - presstime
                    return limit < now - pressTime
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_view)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        setContentView(R.layout.activity_story_view)

        //horo from intent
        firstHoro = intent.extras?.getParcelable("firstStory") as Horo?
        storyViewModel.horoToday = firstHoro


        storiesProgressView = findViewById<View>(R.id.stories) as StoriesProgressView

        storyProgressBar = findViewById(R.id.storyProgressBar)
        storySignImage = findViewById(R.id.signImage)
        storySignName = findViewById(R.id.signName)
        storyDateRange = findViewById(R.id.dateRange)
        storyDay = findViewById(R.id.day)
        storyDescription = findViewById(R.id.storyDescription)


        storiesProgressView!!.setStoriesCount(storiesCount)
        storiesProgressView!!.setStoryDuration(3000L)

        storiesProgressView!!.setStoriesListener(this)

        storiesProgressView!!.startStories(storyViewModel.counter)



        storyViewModel.loading.observe(this, androidx.lifecycle.Observer{
            if(it){
                storyProgressBar.visibility = View.VISIBLE
            }else{
                storyProgressBar.visibility = View.INVISIBLE
            }
        })

        //start loading next stories
        if(networking.isNetworkConnected()){
            storyViewModel.loadTomorrow(firstHoro?.sign!!)
            storyViewModel.loadYesterday(firstHoro?.sign!!)
        }

        storyViewModel.tomorrowLoaded.observe(this, androidx.lifecycle.Observer {
            if(it && storyViewModel.counter==1){
                updateStory(storyViewModel.horoTomorrow, storyViewModel.counter)
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
                updateStory(storyViewModel.horoYesterday, storyViewModel.counter)
            }else if(it==false && storyViewModel.counter==2){
                if(networking.isNetworkConnected()) {
                    storyViewModel.loadYesterday(firstHoro?.sign!!)
                }else{
                    Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        //set first story
        updateStory(firstHoro, storyViewModel.counter)


        // initializing previous view.
        val reverse: View = findViewById(R.id.reverse)

        reverse.setOnClickListener{
            storiesProgressView!!.reverse()
        }

        reverse.setOnTouchListener(onTouchListener)

        // initializing skip view.
        val skip: View = findViewById(R.id.skip)
        skip.setOnClickListener{
            storiesProgressView!!.skip()
        }

        skip.setOnTouchListener(onTouchListener)
    }


    override fun onNext() {
        //move to next progress view of story.

        storyViewModel.counter++

        when(storyViewModel.counter){
            1 -> {
                if(storyViewModel.tomorrowLoaded.value == true){
                    updateStory(storyViewModel.horoTomorrow, storyViewModel.counter)
                }else{
                    if(networking.isNetworkConnected()) {
                        storyViewModel.loadTomorrow(firstHoro?.sign!!)
                    }else{
                        Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            2 -> {
                if(storyViewModel.yesterdayLoaded.value == true){
                    updateStory(storyViewModel.horoYesterday, storyViewModel.counter)
                }else{
                    if(networking.isNetworkConnected()) {
                        storyViewModel.loadYesterday(firstHoro?.sign!!)
                    }else{
                        Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onPrev() {
        if (storyViewModel.counter - 1 < 0) onComplete()

        storyViewModel.counter--

        when(storyViewModel.counter){
            0 -> {
                updateStory(firstHoro, storyViewModel.counter)
            }
            1 -> {
                if(storyViewModel.tomorrowLoaded.value == true){
                    updateStory(storyViewModel.horoTomorrow, storyViewModel.counter)
                }else{
                    storyViewModel.loadTomorrow(firstHoro?.sign!!)
                }
            }
            2 -> {
                if(storyViewModel.yesterdayLoaded.value == true){
                    updateStory(storyViewModel.horoYesterday, storyViewModel.counter)
                }else{
                    storyViewModel.loadYesterday(firstHoro?.sign!!)
                }
            }
        }

    }

    override fun onComplete() {
        //moving back to initial home activity
        val intent = Intent(this@StoryView, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        // Very important !
        storiesProgressView!!.destroy()
        super.onDestroy()
    }

    private fun updateStory(horo: Horo?, counter: Int){
        when(horo!!.sign){
            "aries" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/M9138KP/aries-1.webp")
                    .into(storySignImage)
                storySignName.text = "Aries"}
            "virgo" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/cbdT0hb/virgo-1.webp")
                    .into(storySignImage)
                storySignName.text = "Virgo" }
            "taurus" -> {
                Glide.with(this)
                .load("https://i.ibb.co/2hW3fr7/taurus-1.webp")
                .into(storySignImage)
                storySignName.text = "Taurus"}
            "cancer" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/wwHzRHS/cancer-1.webp")
                    .into(storySignImage)
                storySignName.text = "Cancer"}
            "gemini" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/YDQmKJz/gemini-1.webp")
                    .into(storySignImage)
                storySignName.text = "Gemini"}
            "libra" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/nRqLXgH/libra-1.webp")
                    .into(storySignImage)
                storySignName.text = "Libra"}
            "sagittarius" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/ccJK82F/sagittarius-1.webp")
                    .into(storySignImage)
                storySignName.text = "Sagittarius" }
            "aquarius" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/JzzVxyv/aquarius-1.webp")
                    .into(storySignImage)
                storySignName.text = "Aquarius"}
            "pisces" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/QFBcg2M/pisces-1.webp")
                    .into(storySignImage)
                storySignName.text = "Pisces"}
            "capricorn" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/chZHrFP/capricornus-1.webp")
                    .into(storySignImage)
                storySignName.text = "Capricorn"}
            "scorpio" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/DY9JvfB/scorpio-1.webp")
                    .into(storySignImage)
                storySignName.text = "Scorpio"}
            "leo" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/ZdRxMt9/leo-1.webp")
                    .into(storySignImage)
                storySignName.text = "Leo"}
        }

        when(counter){
            0 -> storyDay.text = "Today"
            1 -> storyDay.text = "Tomorrow"
            2 -> storyDay.text = "Yesterday"
        }

        storyDateRange.text = horo.dateRange
        storyDescription.text = horo.description
    }
}