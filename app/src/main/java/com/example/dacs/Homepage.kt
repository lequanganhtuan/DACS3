package com.example.dacs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.example.dacs.Adapter.PhimMoiAdapter
import com.example.dacs.Fragments.FavouriteFragment
import com.example.dacs.Fragments.HomeFragment
import com.example.dacs.Fragments.ProfileFragment
import com.example.dacs.databinding.ActivityHomepageBinding
import com.example.dacs.databinding.FragmentHomeBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

private lateinit var binding: FragmentHomeBinding
private lateinit var binding1: ActivityHomepageBinding
class Homepage : AppCompatActivity() {
    private lateinit var pmlist:ArrayList<DataModel>
    private lateinit var dbRef :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  FragmentHomeBinding.inflate(layoutInflater)
        binding1 = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val favouriteFragment = FavouriteFragment()
        val profileFragment = ProfileFragment()


        //Home page sẽ được vào đầu tiên
        makeCurrentFragment(homeFragment)


        //Chỉnh các nút và tới các fragment được tạo
        val bottomNavigationView = binding1.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_favourite -> makeCurrentFragment(favouriteFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }

        loadData()

    }

    private fun loadData() {
        loadFeatureData()

    }



    private fun loadFeatureData() {
        dbRef = FirebaseDatabase.getInstance().getReference("phim mới")
        val phimMoiRV = binding.recyclerViewPhimmoi
        pmlist = ArrayList<DataModel>()

        phimMoiRV.layoutManager = LinearLayoutManager (
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        val pmAdapter = PhimMoiAdapter(pmlist)

        phimMoiRV.adapter = pmAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (contentSnapShot in snapshot.children) {
                    val phimmoiData = contentSnapShot.getValue(DataModel::class.java)

                    pmlist.add(phimmoiData!!)
                }
                pmAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    //ham thay dổi fragment
     private fun makeCurrentFragment(fragment: Fragment) =
         supportFragmentManager.beginTransaction().apply {
             replace(R.id.fl_wrapper,fragment)
             commit()
         }


}