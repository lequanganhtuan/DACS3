package com.example.dacs

import EpisodeAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs.Adapter.CommentAdapter
import com.example.dacs.Data.Comment
import com.example.dacs.Data.Episode
import com.example.dacs.databinding.ActivityWatchMovieBinding
import com.google.firebase.database.*
import com.google.gson.Gson
import java.net.URL

class WatchMovie : AppCompatActivity() {
    private lateinit var binding: ActivityWatchMovieBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView1: RecyclerView
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var db: DatabaseReference
    private lateinit var db2: DatabaseReference
    private val episodes= mutableListOf<Episode>()
    private val comments= mutableListOf<Comment>()
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
        recyclerView1 = binding.rcvComment
        recyclerView1.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        val webview = binding.WebView

        binding.movieName.text = intent.getStringExtra("movieTitle")
        binding.des.text = "Description:"
        binding.tvTapphim.text = "Episodes:"
        binding.tvBinhluan.text = "Comments:"
        binding.movieDescription.text = intent.getStringExtra("movieOverview")
        val id = intent.getIntExtra("movieId", 0).toString()
        val IDUser = intent.getStringExtra("id")
        val NameUser = intent.getStringExtra("name")
        if (IDUser != null) {
            getComment(id, IDUser)
        }
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

        binding.comment.setOnClickListener {
            db2 = FirebaseDatabase.getInstance().getReference("Comments")
            val cmt = binding.commentEt.text.toString()
            val comment = Comment(id, IDUser, NameUser, cmt)
            db2.push().setValue(comment)
                .addOnSuccessListener {
                    Toast.makeText(this, "Đã bình luận", Toast.LENGTH_SHORT).show()
                    binding.commentEt.setText("")
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Không thể bình luận", Toast.LENGTH_SHORT).show()
                }
            if (IDUser != null) {
                getComment(id, IDUser)
            }

        }

    }
    @SuppressLint("SetJavaScriptEnabled")
    fun playVideo(webview: WebView, id: String) {
        val html = id
        webview.settings.javaScriptEnabled = true
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.settings.builtInZoomControls = true
        webview.settings.displayZoomControls = false
        webview.webChromeClient = WebChromeClient()
        webview.loadUrl(html)

    }
    private fun getComment(id: String, idUser:String) {

        db2 = FirebaseDatabase.getInstance().getReference("Comments")
        db2.orderByChild("idphim").equalTo(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments.clear()
                if (snapshot.exists())
                {
                    for (CmtSnap in snapshot.children)
                    {
                        val cmt = CmtSnap.getValue(Comment::class.java)
                        comments.add(cmt!!)
                    }
                    commentAdapter = CommentAdapter(comments, idUser)
                    recyclerView1.adapter = commentAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}