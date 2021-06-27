package com.mtdagar.horoscopepredictions

import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.mtdagar.horoscopepredictions.model.Horo
import jp.shts.android.storiesprogressview.StoriesProgressView


class StoryView : AppCompatActivity(), StoriesProgressView.StoriesListener {

    private var storiesProgressView: StoriesProgressView? = null
    private var storyImageView: ImageView? = null
    private lateinit var storyDescription: TextView

    private var counter = 0
    private var storiesCount = 3

    var firstHoro: Horo? = null

    private val resources = intArrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

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
        setContentView(R.layout.activity_story_view)


        firstHoro = intent.extras?.getParcelable("firstStory") as Horo?


        storiesProgressView = findViewById<View>(R.id.stories) as StoriesProgressView

        storiesProgressView!!.setStoriesCount(storiesCount)
        storiesProgressView!!.setStoryDuration(3000L)

        storiesProgressView!!.setStoriesListener(this)

        storiesProgressView!!.startStories(counter)


        storyImageView = findViewById<View>(R.id.image) as ImageView
        storyDescription = findViewById(R.id.storyDescription)

        //storyImageView!!.setImageResource(resources[counter])
        storyDescription.text = firstHoro!!.description


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
        //storyImageView!!.setImageResource(resources[++counter])
        counter++
    }

    override fun onPrev() {
        //move to previous progress view of story.
        if (counter - 1 < 0) return

        //storyImageView!!.setImageResource(resources[--counter])
        counter--
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