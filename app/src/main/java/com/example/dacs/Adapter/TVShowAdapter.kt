package com.example.dacs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs.Data.MovieData
import com.example.dacs.Data.TVShowData
import com.example.dacs.R

class TVShowAdapter(private val pmlist: List<TVShowData>) : RecyclerView.Adapter<TVShowAdapter.ViewHolder>() {
    private lateinit var ClickListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        ClickListener = onItemClickListener
    }
    inner  class  ViewHolder (itemView: View, clickListener: OnItemClickListener) : RecyclerView.ViewHolder (itemView) {
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val poster: ImageView = itemView.findViewById(R.id.imageView1)
        fun bind(tvShowData: TVShowData) {
            title.text = tvShowData.name
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${tvShowData.poster_path}").into(poster)
        }
    }

    // co the view cua firebase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        return ViewHolder(view, ClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.textView.setText(pmlist.get(position).Ntitle)
//        Glide.with(holder.itemView.context).load(pmlist.get(position).Nthumb).into(holder.imageView)
        val movie = pmlist[position]
        holder.bind(movie)

    }

    override fun getItemCount(): Int = pmlist.size
}

