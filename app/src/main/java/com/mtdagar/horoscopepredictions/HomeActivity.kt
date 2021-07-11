package com.mtdagar.horoscopepredictions


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
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
import java.util.*
import kotlin.collections.ArrayList


/**
 * created by Meet Dagar
 * on 01/06/21
 **/

class HomeActivity : AppCompatActivity(), HomeActivityInterface {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var repository: HoroRepository
    private val networking: Networking = Networking()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setContentView(R.layout.activity_home)

        repository = HoroRepository()

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


        lifecycleScope.launch(Dispatchers.IO) {

            //clear older data
            repository.validateStoredData()

            //read pre loaded first stories
            data = homeViewModel.readData()

            if(data.isNotEmpty()){
                Log.i("showStory", "Loading first story from Database")

                for(i in data){
                    if(i.sign == sign) {
                        storyIndex = data.indexOf(i)

                        val intent = Intent(this@HomeActivity, com.mtdagar.horoscopepredictions.StoryView::class.java)
                        intent.putExtra("firstStory", data[storyIndex])
                        startActivity(intent)

                        break
                    }
                }
            }else{
                Log.i("showStory", "Database empty, fetching first story from API.")

                if(networking.isNetworkConnected()){
                    val horo = repository.getStory(sign, "today")
                    val intent = Intent(this@HomeActivity, com.mtdagar.horoscopepredictions.StoryView::class.java)
                    intent.putExtra("firstStory", horo)
                    startActivity(intent)
                }else{
                    lifecycleScope.launch{
                        Toast.makeText(HoroApplication.applicationContext(), "Error loading story!\nNo internet connection found.", Toast.LENGTH_SHORT).show()
                    }

                }
            }


        }
    }

}


interface HomeActivityInterface {
    fun showStory(sign: String)
}