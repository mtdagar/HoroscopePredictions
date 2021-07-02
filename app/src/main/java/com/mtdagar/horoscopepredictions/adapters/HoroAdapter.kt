package com.mtdagar.horoscopepredictions.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mtdagar.horoscopepredictions.HomeActivityInterface
import com.mtdagar.horoscopepredictions.R
import com.mtdagar.horoscopepredictions.model.HoroCardItem


class HoroAdapter(private val horoCardList: List<HoroCardItem>, private val homeActivityInterface: HomeActivityInterface) : RecyclerView.Adapter<HoroAdapter.HoroViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent, false)

        return HoroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HoroViewHolder, position: Int) {
        val currentItem = horoCardList[position]

        val currentSign = currentItem.text.lowercase()

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentSign

        holder.cardView.setOnClickListener{

            holder.progressBar.visibility = View.VISIBLE

            homeActivityInterface.showStory(currentSign)

            holder.progressBar.visibility = View.INVISIBLE



        //            CoroutineScope(IO).launch {
//                Networking().getStories(currentSign, object : Networking.NetworkingInterface{
//                    override fun onResponse(sign: String, list: ArrayList<MyStory>, horoObject: Horo) {
//                        Log.i("Response from interface", list.toString())
//                        homeActivityInterface.popStory(sign, list, horoObject)
//                    }
//
//                    override fun onError(message: String) {
//                        Log.i("error", message)
//                    }
//                })
//                holder.progressBar.visibility = View.INVISIBLE
//            }


        }

    }

    override fun getItemCount() = horoCardList.size

    class HoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.card_image)
        val textView: TextView = itemView.findViewById(R.id.card_text)
        val cardView: CardView = itemView.findViewById(R.id.horo_card)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }


}