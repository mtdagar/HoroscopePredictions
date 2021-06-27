package com.mtdagar.horoscopepredictions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import jp.shts.android.storiesprogressview.StoriesProgressView


class StoryView : AppCompatActivity(), StoriesProgressView.StoriesListener {

    private var storiesProgressView: StoriesProgressView? = null
    private var image: ImageView? = null

    private var counter = 0

    private val resources = intArrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    var pressTime = 0L
    var limit = 500L

    // on below line we are creating a new method for adding touch listener
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

        // inside in create method below line is use to make a full screen.
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_story_view)

        // on below line we are initializing our variables.
        storiesProgressView = findViewById<View>(R.id.stories) as StoriesProgressView

        // on below line we are setting the total count for our stories.
        storiesProgressView!!.setStoriesCount(resources.size)

        // on below line we are setting story duration for each story.
        storiesProgressView!!.setStoryDuration(3000L)

        // on below line we are calling a method for set
        // on story listener and passing context to it.
        storiesProgressView!!.setStoriesListener(this)

        // below line is use to start stories progress bar.
        storiesProgressView!!.startStories(counter)

        // initializing our image view.
        image = findViewById<View>(R.id.image) as ImageView

        // on below line we are setting image to our image view.
        image!!.setImageResource(resources[counter])

        // below is the view for going to the previous story.
        // initializing our previous view.
        val reverse: View = findViewById(R.id.reverse)

        // adding on click listener for our reverse view.
        reverse.setOnClickListener{
            storiesProgressView!!.reverse()
        }

        // on below line we are calling a set on touch
        // listener method to move towards previous image.
        reverse.setOnTouchListener(onTouchListener)

        // on below line we are initializing
        // view to skip a specific story.
        val skip: View = findViewById(R.id.skip)
        skip.setOnClickListener{
            // inside on click we are
            // skipping the story progress view.
            storiesProgressView!!.skip()
        }
        // on below line we are calling a set on touch
        // listener method to move to next story.
        skip.setOnTouchListener(onTouchListener)
    }

    override fun onNext() {
        //move to next progress view of story.
        image!!.setImageResource(resources[++counter])
    }

    override fun onPrev() {
        //move to previous progress view of story.
        if (counter - 1 < 0) return

        image!!.setImageResource(resources[--counter])
    }

    override fun onComplete() {
        //moving back to initial home activity
        val intent = Intent(this@StoryView, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        // Very important !
        storiesProgressView!!.destroy()
        super.onDestroy()
    }
}