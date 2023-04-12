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

class PhimMoiAdapter (var pmlist : List<DataModel> ) : RecyclerView.Adapter<PhimMoiAdapter.PhimMoiViewHolder>() {
    inner  class  PhimMoiViewHolder (val binding: MovieCardBinding) : RecyclerView.ViewHolder (binding.root) {
        val imageView = binding.imageView1
        val textView = binding.movieTitle
    }

    // co the view cua firebase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhimMoiViewHolder {
        val binding = MovieCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhimMoiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhimMoiViewHolder, position: Int) {
//        holder.textView.setText(pmlist.get(position).Ntitle)
//        Glide.with(holder.itemView.context).load(pmlist.get(position).Nthumb).into(holder.imageView)
        holder.itemView.apply {
            holder.textView.text = pmlist[position].Ntitle

            Glide.with(holder.itemView.context)
                .load(pmlist[position].Nthumb)
                .into(holder.imageView)
        }

    }

    override fun getItemCount(): Int {
        return pmlist.size
    }
}

