package com.example.dacs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs.DataModel
import com.example.dacs.R

class PhimMoiAdapter (var pmlist : List<DataModel> ) : RecyclerView.Adapter<PhimMoiAdapter.PhimMoiViewHolder>() {
    inner  class  PhimMoiViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textView = itemView.findViewById<TextView>(R.id.movie_title)
    }

    // co the view cua firebase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhimMoiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card,parent,false)
        return PhimMoiViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhimMoiViewHolder, position: Int) {
        holder.textView.setText(pmlist.get(position).Ntitle)
        Glide.with(holder.itemView.context).load(pmlist.get(position).Nthumb).into(holder.imageView)
    }

    override fun getItemCount(): Int {
       return pmlist.size
    }
}


