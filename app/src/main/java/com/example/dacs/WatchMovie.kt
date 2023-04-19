package com.example.dacs

import EpisodeAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs.Data.Episode
import com.example.dacs.databinding.ActivityWatchMovieBinding
import com.google.firebase.database.*
import com.google.gson.Gson
import java.net.URL

class WatchMovie : AppCompatActivity() {
    private lateinit var binding: ActivityWatchMovieBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var db: DatabaseReference
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
        val webview = binding.WebView

        binding.movieName.text = intent.getStringExtra("movieTitle")
        binding.des.text = "Description:"
        binding.tvTapphim.text = "Episodes:"
        binding.tvBinhluan.text = "Comments:"
        binding.movieDescription.text = intent.getStringExtra("movieOverview")
        val media = intent.getStringExtra("mediaType")
        val id = intent.getIntExtra("movieId", 0).toString()
        db = FirebaseDatabase.getInstance().getReference("Episodes")
        db.orderByChild("IDPhim").equalTo(id).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    episodes.clear()
                    if (snapshot.exists()) {
                        for (ds in snapshot.children) {
                            val episode = ds.getValue(Episode::class.java)
                            episodes.add(episode!!)

                        }
                        episodes[0].Video?.let { playVideo(webview, it) }
                    }
                    episodeAdapter = EpisodeAdapter(episodes)
                    episodeAdapter.setOnItemClickListener(
                        object : EpisodeAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                episodes[position].Video?.let { playVideo(webview, it) }
                            }

                        }
                    )
                    recyclerView.adapter = episodeAdapter

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
    @SuppressLint("SetJavaScriptEnabled")
    fun playVideo(webview: WebView, id: String) {
        val html = id
        webview.settings.javaScriptEnabled = true
        webview.loadData(html, "text/html", "UTF-8")

    }
}