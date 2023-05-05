package com.example.dacs

import EpisodeAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
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
    private lateinit var webview: WebView
    private var isFullScreen = false
    data class EpisodeResponse(
        val id : Int,
        val results : List<Episode>
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webview = findViewById(R.id.WebView)
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

//        val webview = binding.WebView

        binding.movieName.text = intent.getStringExtra("movieTitle")
        binding.des.text = "Description:"
        binding.tvTapphim.text = "Episodes:"
        binding.tvBinhluan.text = "Comments:"
        binding.movieDescription.text = intent.getStringExtra("movieOverview")

        val id = intent.getIntExtra("movieId", 0).toString()
        val IDUser = intent.getStringExtra("id")
        val NameUser = intent.getStringExtra("name")
        getComment(id)
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
            getComment(id)

        }

    }
    @SuppressLint("SetJavaScriptEnabled")
    fun playVideo(webview: WebView, id: String) {
        val html = id

//        webview.settings.javaScriptEnabled = true
//        webview.settings.loadWithOverviewMode = true
//        webview.settings.useWideViewPort = true
//        webview.settings.builtInZoomControls = true
//        webview.settings.displayZoomControls = false
//        webview.webChromeClient = WebChromeClient()
//        webview.loadUrl(html)

        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.setSupportMultipleWindows(true) // Cho phép chế độ toàn màn hình
        webSettings.builtInZoomControls = true
        webSettings.setSupportZoom(true)
        webSettings.displayZoomControls = false
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.setGeolocationEnabled(true)

        webview.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                // Chuyển đổi video sang chế độ toàn màn hình
                val videoView = view as FrameLayout
                val decorView = window.decorView as FrameLayout
                decorView.addView(
                    videoView,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                webview.visibility = View.GONE
                isFullScreen = true
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                // Thoát chế độ toàn màn hình
                val decorView = window.decorView as FrameLayout
                decorView.removeViewAt(decorView.childCount - 1)
                webview.visibility = View.VISIBLE
                isFullScreen = false
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            }
        }

        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Cập nhật hướng hiển thị hiện tại của màn hình
                val display =
                    (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                val orientation = when (display.rotation) {
                    Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    else ->
                        ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                }
                requestedOrientation = orientation
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                // Bắt sự kiện khi click vào link
                val url = request?.url.toString()
                if (url.endsWith(".mp4")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(Uri.parse(url), "video/*")
                    startActivity(intent)
                    return true
                }
                return false
            }
        }
        webview.loadUrl(html)
    }

    private fun getComment(id: String) {

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
                    commentAdapter = CommentAdapter(comments)
                    recyclerView1.adapter = commentAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onBackPressed() {
        // Thoát chế độ toàn màn hình khi bấm nút back
        if (isFullScreen) {
            webview.stopLoading()
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

}