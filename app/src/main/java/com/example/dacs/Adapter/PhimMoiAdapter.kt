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
import com.example.dacs.databinding.MovieCardBinding

class PhimMoiAdapter(private val pmlist: List<DataModel>) : RecyclerView.Adapter<PhimMoiAdapter.ViewHolder>() {
    inner  class  ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val poster: ImageView = itemView.findViewById(R.id.imageView1)
        fun bind(dataModel: DataModel) {
            title.text = dataModel.title
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${dataModel.poster_path}").into(poster)
        }
    }

    // co the view cua firebase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.textView.setText(pmlist.get(position).Ntitle)
//        Glide.with(holder.itemView.context).load(pmlist.get(position).Nthumb).into(holder.imageView)
        val movie = pmlist[position]
        holder.bind(movie)

    }

    override fun getItemCount(): Int = pmlist.size
}

