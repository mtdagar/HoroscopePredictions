package com.mtdagar.horoscopepredictions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.horoscopepredictions.R
import com.mtdagar.horoscopepredictions.models.HoroItem
import java.util.*


class HoroAdapter(private val horoList: List<HoroItem>) : RecyclerView.Adapter<HoroAdapter.HoroViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent, false)

        return HoroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HoroViewHolder, position: Int) {
        val currentItem = horoList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.text

        holder.cardView.setOnClickListener{
            //val myStories: ArrayList<MyStory> = ArrayList<MyStory>()
        }

    }

    override fun getItemCount() = horoList.size

    class HoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.card_image)
        val textView: TextView = itemView.findViewById(R.id.card_text)
        val cardView: CardView = itemView.findViewById(R.id.horo_card)
    }

}