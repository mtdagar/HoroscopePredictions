package com.mtdagar.horoscopepredictions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.horoscopepredictions.adapters.HoroAdapter
import com.mtdagar.horoscopepredictions.model.Horo
import com.mtdagar.horoscopepredictions.network.Networking
import com.mtdagar.horoscopepredictions.repository.HoroRepository
import com.mtdagar.horoscopepredictions.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import java.util.*
import kotlin.collections.ArrayList


/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class HomeActivity : AppCompatActivity(), HomeActivityInterface {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HoroscopePredictions)    //discard splash screen
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        homeViewModel.horoCards().observe(this, androidx.lifecycle.Observer {
            recyclerView.adapter = HoroAdapter(it, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
        })

        //recyclerView.setHasFixedSize(true)  //for optimization
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_developer -> {
                startActivity(Intent(this@HomeActivity, DeveloperActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showStory(sign: String) {

        var data: List<Horo>
        var storyIndex: Int

        lifecycleScope.launch {
            //read pre loaded first stories
            data = homeViewModel.readData()

            for(i in data){
                if(i.sign == sign) {
                    storyIndex = data.indexOf(i)

                    val intent = Intent(this@HomeActivity, com.mtdagar.horoscopepredictions.StoryView::class.java)
                    intent.putExtra("firstStory", data[storyIndex])
                    startActivity(intent)

                    break
                }
            }
        }
    }

}

interface HomeActivityInterface {
    fun showStory(sign: String)
}