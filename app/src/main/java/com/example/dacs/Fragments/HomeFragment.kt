package com.example.dacs.Fragments

import PhimMoiAdapter
import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.dacs.Adapter.TVShowAdapter
import com.example.dacs.Data.MovieData
import com.example.dacs.Data.TVShowData
import com.example.dacs.DataModel
import com.example.dacs.MyAsyncTask
import com.example.dacs.R
import com.example.dacs.databinding.FragmentHomeBinding
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var binding: FragmentHomeBinding

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var imageSlider: ImageSlider
    private val movies = mutableListOf<MovieData>()
    private val tvShows = mutableListOf<TVShowData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView1 = view.findViewById(R.id.recyclerViewPhimmoi)
        recyclerView2 = view.findViewById(R.id.recyclerViewPhimbo)
        imageSlider = view.findViewById(R.id.imageSlider)

        recyclerView1.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView2.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView1.adapter = PhimMoiAdapter(movies)
        recyclerView2.adapter = TVShowAdapter(tvShows)
        Thread {
            val url = "https://api.themoviedb.org/3/list/8248497?api_key=eb82a323e426d30d552550d47bc83e2b"
            val response = URL(url).readText()
            val gson = Gson()
            val movieResponse = gson.fromJson(response, MovieResponse::class.java)
            val tvShowResponse = gson.fromJson(response, TVShowResponse::class.java)
            movies.addAll(movieResponse.items)
            tvShows.addAll(tvShowResponse.items)
            val movieList = movies.filter { movies -> movies.media_type == "movie" }
            val tvShowList = tvShows.filter { tvShows -> tvShows.media_type == "tv" }
            activity?.runOnUiThread {
                recyclerView1.adapter = PhimMoiAdapter(movieList)
                recyclerView2.adapter = TVShowAdapter(tvShowList)
            }
        }.start()
//        val apiKey = "eb82a323e426d30d552550d47bc83e2b"
//        val url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey"
//        MyAsyncTask().execute(url)
//        val jsonString = URL(url).readText()
//        val gson = Gson()
//        val movieList = gson.fromJson(jsonString, Array<DataModel>::class.java).toList()


//        val dbRef = FirebaseDatabase.getInstance().getReference("phim mới")
//        val pmList = mutableListOf<DataModel>()
//        pmList.add(DataModel(R.drawable.suzume, "Suzume"))
//        pmList.add(DataModel(R.drawable.taktop, "Đứa con của thời tiết"))
//        dbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                pmList.clear()
//                for (contentSnapShot in snapshot.children) {
//                    val phimmoiData = contentSnapShot.getValue(DataModel::class.java)
//                    if (phimmoiData != null) {
//                        pmList.add(phimmoiData)
//                    }
//                }
//                vadapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e(ContentValues.TAG, "Failed to read value.", error.toException())
//            }
//        })
//        vadapter = PhimMoiAdapter(pmList)
//        recyclerView.adapter = vadapter
        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                "https://static.lag.vn/upload/news/22/12/12/anime-kimetsu-no-yaiba-season-3-cong-bo-them-movie-3_SQQW.jpg?w=800&encoder=wic&subsampling=444",
                "Demon Slayer: Kimetsu No Yaiba - To the Swordsmith Village"
            )
        )
        imageList.add(
            SlideModel(
                "https://s199.imacdn.com/vg/2022/11/24/fca8120309bfea7b_f8267129988ae630_11397616692764385118684.jpg",
                "MASHLE "
            )
        )
        imageList.add(
            SlideModel(
                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjTQ17Ms06jTiVtoqFEhYr13ruNuLCgQZTib45vHFstb0LKqoOckPtqyvDu2IhSsMShUMqGe0T6Qyxuq0OXbrLDbA-90FMzrZjwznpzo_4WVVhSB_2VDAJqNYwhDCVpHexT1EhCFKyriKJ6yxzFLB0J3DHoOa9WDfk3WsSe070aRTECrXjRKh3tShVsiA/s700/oshi-no-ko-anime-banner.jpg",
                "Oshi no Ko"
            )
        )
        imageList.add(
            SlideModel(
                "https://www.transcontinentaltimes.com/wp-content/uploads/2022/11/Attack-on-Titan-1068x601.jpg",
                "Attack on Titan Final Season"
            )
        )

        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        // Inflate the layout for this fragment
        return view
    }
    data class MovieResponse(
        val create_by : String,
        val description : String,
        val favorite_count : Int,
        val id : Int,
        val items : List<MovieData>
    )
    data class TVShowResponse(
        val create_by : String,
        val description : String,
        val favorite_count : Int,
        val id : Int,
        val items : List<TVShowData>
    )


    companion object {

    }

}