package com.example.dacs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.denzcoskun.imageslider.ImageSlider
import com.example.dacs.Fragments.FavouriteFragment
import com.example.dacs.Fragments.HomeFragment
import com.example.dacs.Fragments.ProfileFragment

import com.google.android.material.bottomnavigation.BottomNavigationView


class Homepage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_homepage)

        val homeFragment = HomeFragment()
        val favouriteFragment = FavouriteFragment()
        val profileFragment = ProfileFragment()


        //Home page sẽ được vào đầu tiên
        makeCurrentFragment(homeFragment)


        //Chỉnh các nút và tới các fragment được tạo
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_favourite -> makeCurrentFragment(favouriteFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }

    }

    //ham thay dổi fragment
     private fun makeCurrentFragment(fragment: Fragment) =
         supportFragmentManager.beginTransaction().apply {
             replace(R.id.fl_wrapper,fragment)
             commit()
         }


}