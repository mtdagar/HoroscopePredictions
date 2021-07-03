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
import androidx.lifecycle.ViewModelProvider
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.viewmodel.StoryViewModel
import jp.shts.android.storiesprogressview.StoriesProgressView


class StoryView : AppCompatActivity(), StoriesProgressView.StoriesListener {

    private lateinit var storyViewModel: StoryViewModel

    private var storiesProgressView: StoriesProgressView? = null
    private var storyImageView: ImageView? = null
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

        storiesProgressView!!.setStoriesCount(storiesCount)
        storiesProgressView!!.setStoryDuration(3000L)

        storiesProgressView!!.setStoriesListener(this)

        storiesProgressView!!.startStories(storyViewModel.counter)



        storyDescription = findViewById(R.id.storyDescription)


        storyViewModel.loading.observe(this, androidx.lifecycle.Observer{
            if(it){
                storyProgressBar.visibility = View.VISIBLE
            }else{
                storyProgressBar.visibility = View.INVISIBLE
            }
        })

        //start loading next stories
        storyViewModel.loadTomorrow(firstHoro?.sign!!)
        storyViewModel.loadYesterday(firstHoro?.sign!!)

        storyViewModel.tomorrowLoaded.observe(this, androidx.lifecycle.Observer {
            if(it && storyViewModel.counter==1){
                storyDescription.text = "Tomorrow: " + storyViewModel.horoTomorrow!!.description
            }else if(it==false && storyViewModel.counter==1){
                storyViewModel.loadTomorrow(firstHoro?.sign!!)
            }
        })

        storyViewModel.yesterdayLoaded.observe(this, androidx.lifecycle.Observer {
            if(it && storyViewModel.counter==2){
                storyDescription.text = "Yesterday: " + storyViewModel.horoYesterday!!.description
            }else if(it==false && storyViewModel.counter==2){
                storyProgressBar.visibility = View.VISIBLE
                storyViewModel.loadYesterday(firstHoro?.sign!!)
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

    private fun updateStory(horo: Horo?, counter: Int){
        when(horo!!.sign){
            "aries" -> {storySignImage.setImageResource(R.drawable.aries)
                storySignName.text = "Aries"}
            "virgo" -> {storySignImage.setImageResource(R.drawable.virgo)
                storySignName.text = "Virgo" }
            "taurus" -> {storySignImage.setImageResource(R.drawable.taurus)
                storySignName.text = "Taurus"}
            "cancer" -> { storySignImage.setImageResource(R.drawable.cancer)
                storySignName.text = "Cancer"}
            "gemini" -> {storySignImage.setImageResource(R.drawable.gemini)
                storySignName.text = "Gemini"}
            "libra" -> {storySignImage.setImageResource(R.drawable.libra)
                storySignName.text = "Libra"}
            "sagittarius" -> {storySignImage.setImageResource(R.drawable.sagittarius)
                storySignName.text = "Sagittarius" }
            "aquarius" -> {storySignImage.setImageResource(R.drawable.aquarius)
                storySignName.text = "Aquarius"}
            "pisces" -> {storySignImage.setImageResource(R.drawable.pisces)
                storySignName.text = "Pisces"}
            "capricorn" -> {storySignImage.setImageResource(R.drawable.capricornus)
                storySignName.text = "Capricorn"}
            "scorpio" -> {storySignImage.setImageResource(R.drawable.scorpio)
                storySignName.text = "Scorpio"}
            "leo" -> {storySignImage.setImageResource(R.drawable.leo)
                storySignName.text = "Leo"}
        }

        storyDateRange.text = horo!!.dateRange
        storyDay.text = horo!!.day
        storyDescription.text = horo!!.description
    }


    override fun onNext() {
        //move to next progress view of story.

        storyViewModel.counter++

        when(storyViewModel.counter){
            1 -> {
                if(storyViewModel.tomorrowLoaded.value == true){
                    storyDescription.text = "Tomorrow: " + storyViewModel.horoTomorrow!!.description
                }else{
                    storyViewModel.loadTomorrow(firstHoro?.sign!!)
                }
            }
            2 -> {
                if(storyViewModel.yesterdayLoaded.value == true){
                    storyDescription.text = "Yesterday: " + storyViewModel.horoYesterday!!.description
                }else{
                    storyViewModel.loadYesterday(firstHoro?.sign!!)
                }
            }
        }

    }

    override fun onPrev() {
        //move to previous progress view of story.
        if (storyViewModel.counter - 1 < 0) return

        storyViewModel.counter--

        when(storyViewModel.counter){
            0 -> {
                storyDescription.text = storyViewModel.counter.toString() + "Today: " + firstHoro!!.description
            }
            1 -> {
                if(storyViewModel.tomorrowLoaded.value == true){
                    storyDescription.text = "Tomorrow: " + storyViewModel.horoTomorrow!!.description
                }else{
                    storyViewModel.loadTomorrow(firstHoro?.sign!!)
                }
            }
            2 -> {
                if(storyViewModel.yesterdayLoaded.value == true){
                    storyDescription.text = "Yesterday: " + storyViewModel.horoYesterday!!.description
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
}