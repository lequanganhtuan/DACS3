package com.example.dacs

import EpisodeAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs.Adapter.PhimMoiAdapter
import com.example.dacs.Adapter.TVShowAdapter
import com.example.dacs.Data.Episode
import com.example.dacs.Data.MovieData
import com.example.dacs.Data.TVShowData
import com.example.dacs.Fragments.HomeFragment
import com.example.dacs.R
import com.example.dacs.databinding.ActivityWatchMovieBinding
import com.google.gson.Gson
import java.net.URL

class WatchMovie : AppCompatActivity() {
    private lateinit var binding: ActivityWatchMovieBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodeAdapter: EpisodeAdapter
    private val episodes= mutableListOf<Episode>()
    data class EpisodeResponse(
        val id : Int,
        val results : List<Episode>
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.rvmovieEpisode
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        episodeAdapter = EpisodeAdapter(episodes)
        binding.movieName.text = intent.getStringExtra("movieTitle")
        binding.des.text = "Description:"
        binding.tvTapphim.text = "Episodes:"
        binding.tvBinhluan.text = "Comments:"
        binding.movieDescription.text = intent.getStringExtra("movieOverview")
        val media = intent.getStringExtra("mediaType")
        val id = intent.getIntExtra("movieId", 0)
        if (media == "tv") {
            Thread {
                val url = "https://api.themoviedb.org/3/tv/$id/videos?api_key=22d0c8eabff8cf2f48bbeae8314045f7"
                val response = URL(url).readText()
                val gson = Gson()
                val movieResponse = gson.fromJson(response, EpisodeResponse::class.java)
                episodes.addAll(movieResponse.results)
                runOnUiThread {
                    episodeAdapter = EpisodeAdapter(episodes)
                    recyclerView.adapter = episodeAdapter
                }
            }.start()
        }
        else {
            Thread {
                val url =
                    "https://api.themoviedb.org/3/movie/$id/videos?api_key=22d0c8eabff8cf2f48bbeae8314045f7"
                val response = URL(url).readText()
                val gson = Gson()
                val movieResponse = gson.fromJson(response, EpisodeResponse::class.java)
                episodes.addAll(movieResponse.results)
                runOnUiThread {
                    episodeAdapter = EpisodeAdapter(episodes)
                    recyclerView.adapter = episodeAdapter
                }


            }
                .start()
        }}
}