package com.example.dacs.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dacs.HistoryActivity
import com.example.dacs.R
import com.example.dacs.databinding.FragmentProfileBinding
import com.google.firebase.database.DatabaseReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var binding: FragmentProfileBinding
class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val bundle = arguments
        val name = bundle?.getString("name")
        val id = bundle?.getString("id")
        binding.tvUser.text = name
        binding.btnLichsu.setOnClickListener {
            val intent = Intent(context, HistoryActivity::class.java)
            intent.putExtra("idUser", id)
            startActivity(intent)
        }
        return binding.root
    }



}