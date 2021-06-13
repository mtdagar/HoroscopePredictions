package com.mtdagar.horoscopepredictions.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.horoscopepredictions.MainActivityInterface
import com.mtdagar.horoscopepredictions.Networking
import com.mtdagar.horoscopepredictions.R
import com.mtdagar.horoscopepredictions.models.HoroItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.*
import omari.hamza.storyview.model.MyStory
import java.util.*


class HoroAdapter(private val horoList: List<HoroItem>, private val mainActivityInterface: MainActivityInterface) : RecyclerView.Adapter<HoroAdapter.HoroViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent, false)

        return HoroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HoroViewHolder, position: Int) {
        val currentItem = horoList[position]

        val currentSign = currentItem.text

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentSign

        holder.cardView.setOnClickListener{

            holder.progressBar.visibility = View.VISIBLE

            CoroutineScope(IO).launch {
                Networking(holder.progressBar).getStories(currentSign, object : Networking.NetworkingInterface{
                    override fun onResponse(sign: String, list: ArrayList<MyStory>) {
                        Log.i("Response from interface", list.toString())
                        mainActivityInterface.popStory(sign, list)
                    }

                    override fun onError(message: String) {
                        Log.i("error", message)
                    }
                })
                holder.progressBar.visibility = View.INVISIBLE
            }


        }

    }

    override fun getItemCount() = horoList.size

    class HoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.card_image)
        val textView: TextView = itemView.findViewById(R.id.card_text)
        val cardView: CardView = itemView.findViewById(R.id.horo_card)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }


}