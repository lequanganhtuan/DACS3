package com.example.dacs.Fragments

import android.content.ContentValues
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView.ScaleType
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.dacs.Adapter.PhimMoiAdapter
import com.example.dacs.DataModel
import com.example.dacs.R
import com.example.dacs.databinding.ActivityMainBinding
import com.example.dacs.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var binding: FragmentHomeBinding

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageSlider: ImageSlider
    private lateinit var vadapter: PhimMoiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPhimmoi)
        imageSlider = view.findViewById(R.id.imageSlider)

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val dbRef = FirebaseDatabase.getInstance().getReference("phim mới")
        val pmList = mutableListOf<DataModel>()
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pmList.clear()
                for (contentSnapShot in snapshot.children) {
                    val phimmoiData = contentSnapShot.getValue(DataModel::class.java)
                    if (phimmoiData != null) {
                        pmList.add(phimmoiData)
                    }
                }
                vadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
        vadapter = PhimMoiAdapter(pmList)
        recyclerView.adapter = vadapter
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://images.immediate.co.uk/production/volatile/sites/3/2019/04/Avengers-Endgame-Banner-2-de7cf60.jpg?quality=90&resize=620,413","Avengers Endgame"))
        imageList.add(SlideModel("https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600","Jumanji"))
        imageList.add(SlideModel("https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg","Spider Man"))
        imageList.add(SlideModel("https://live.staticflickr.com/1980/29996141587_7886795726_b.jpg","Venom"))

        imageSlider.setImageList(imageList,ScaleTypes.FIT)
        // Inflate the layout for this fragment
        return view
    }

    companion object {

    }

}