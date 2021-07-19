package com.mtdagar.horoscopepredictions

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.viewmodel.StoryViewModel
import jp.shts.android.storiesprogressview.StoriesProgressView


class StoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), StoriesProgressView.StoriesListener {

    private var storyViewModel: StoryViewModel
    private val networking: Networking = Networking()
    private var storiesProgressView: StoriesProgressView? = null
    private val storyDescription: TextView
    private val storyProgressBar: ProgressBar
    private val storySignImage: ImageView
    private val storySignName: TextView
    private val storyDateRange: TextView
    private val storyDay: TextView

    private var storiesCount = 3
    var pressTime = 0L
    var limit = 500L


    private val onTouchListener: OnTouchListener = object : OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    pressTime = System.currentTimeMillis()

                    storiesProgressView?.pause()
                    return false
                }
                MotionEvent.ACTION_UP -> {

                    val now = System.currentTimeMillis()

                    storiesProgressView?.resume()

                    return limit < now - pressTime
                }
            }
            return false
        }
    }

    private val parentActivity: FragmentActivity by lazy {
        try {
            context as FragmentActivity
        } catch (exception: ClassCastException) {
            throw ClassCastException("Please ensure that the provided Context is a valid FragmentActivity")
        }
    }


    init {

        storyViewModel = ViewModelProvider(parentActivity).get(StoryViewModel::class.java)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.view_story, this)

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


        val reverse: View = findViewById(R.id.reverse)

        reverse.setOnClickListener {
            storiesProgressView!!.reverse()
        }

        reverse.setOnTouchListener(onTouchListener)

        // initializing skip view.
        val skip: View = findViewById(R.id.skip)
        skip.setOnClickListener {
            storiesProgressView!!.skip()
        }

        skip.setOnTouchListener(onTouchListener)

    }


    override fun onNext() {
        storyViewModel.counter++

        when (storyViewModel.counter) {
            1 -> {
                if (storyViewModel.tomorrowLoaded.value == true) {
                    setStory(storyViewModel.horoTomorrow, storyViewModel.counter)
                } else {
                    if (networking.isNetworkConnected()) {
                        storyViewModel.loadTomorrow(storyViewModel.horoToday?.sign!!)
                    } else {
                        //Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            2 -> {
                if (storyViewModel.yesterdayLoaded.value == true) {
                    setStory(storyViewModel.horoYesterday, storyViewModel.counter)
                } else {
                    if (networking.isNetworkConnected()) {
                        storyViewModel.loadYesterday(storyViewModel.horoToday?.sign!!)
                    } else {
                        //Toast.makeText(this, "Error loading story. Check internet connection.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onPrev() {
        //move to previous progress view of story.
        if (storyViewModel.counter - 1 < 0) {
            onComplete()
        }

        storyViewModel.counter--

        when (storyViewModel.counter) {
            0 -> {
                setStory(storyViewModel.horoToday, storyViewModel.counter)
            }
            1 -> {
                if (storyViewModel.tomorrowLoaded.value == true) {
                    setStory(storyViewModel.horoTomorrow, storyViewModel.counter)
                } else {
                    storyViewModel.loadTomorrow(storyViewModel.horoToday?.sign!!)
                }
            }
            2 -> {
                if (storyViewModel.yesterdayLoaded.value == true) {
                    setStory(storyViewModel.horoYesterday, storyViewModel.counter)
                } else {
                    storyViewModel.loadYesterday(storyViewModel.horoToday?.sign!!)
                }
            }
        }
    }

    override fun onComplete() {
        //moving back to initial home activity
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun onDetachedFromWindow() {
        storiesProgressView!!.destroy()
        super.onDetachedFromWindow()
    }

    fun setStory(horo: Horo?, counter: Int) {
        when (horo!!.sign) {
            "aries" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/M9138KP/aries-1.webp")
                    .into(storySignImage)
                storySignName.text = "Aries"
            }
            "virgo" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/cbdT0hb/virgo-1.webp")
                    .into(storySignImage)
                storySignName.text = "Virgo"
            }
            "taurus" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/2hW3fr7/taurus-1.webp")
                    .into(storySignImage)
                storySignName.text = "Taurus"
            }
            "cancer" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/wwHzRHS/cancer-1.webp")
                    .into(storySignImage)
                storySignName.text = "Cancer"
            }
            "gemini" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/YDQmKJz/gemini-1.webp")
                    .into(storySignImage)
                storySignName.text = "Gemini"
            }
            "libra" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/nRqLXgH/libra-1.webp")
                    .into(storySignImage)
                storySignName.text = "Libra"
            }
            "sagittarius" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/ccJK82F/sagittarius-1.webp")
                    .into(storySignImage)
                storySignName.text = "Sagittarius"
            }
            "aquarius" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/JzzVxyv/aquarius-1.webp")
                    .into(storySignImage)
                storySignName.text = "Aquarius"
            }
            "pisces" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/QFBcg2M/pisces-1.webp")
                    .into(storySignImage)
                storySignName.text = "Pisces"
            }
            "capricorn" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/chZHrFP/capricornus-1.webp")
                    .into(storySignImage)
                storySignName.text = "Capricorn"
            }
            "scorpio" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/DY9JvfB/scorpio-1.webp")
                    .into(storySignImage)
                storySignName.text = "Scorpio"
            }
            "leo" -> {
                Glide.with(this)
                    .load("https://i.ibb.co/ZdRxMt9/leo-1.webp")
                    .into(storySignImage)
                storySignName.text = "Leo"
            }
        }

        when (counter) {
            0 -> storyDay.text = "Today"
            1 -> storyDay.text = "Tomorrow"
            2 -> storyDay.text = "Yesterday"
        }

        storyDateRange.text = horo.dateRange
        storyDescription.text = horo.description
    }

    fun setProgressBarVisibility(isLoadingStory: Boolean) {
        if (isLoadingStory) {
            storyProgressBar.visibility = View.VISIBLE
        } else {
            storyProgressBar.visibility = View.INVISIBLE
        }
    }
}
