package com.bunny.splashwallpaper.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunny.splashwallpaper.Adapter.BomAdapter
import com.bunny.splashwallpaper.Adapter.CatAdapter
import com.bunny.splashwallpaper.Adapter.colortoneAdapter
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaper.Model.catModel
import com.bunny.splashwallpaper.Model.colortoneModel
import com.bunny.splashwallpaper.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var db:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()

        db.collection("bestofmonth").addSnapshotListener { value, error ->
            val listBestOfTheMonth = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listBestOfTheMonth.addAll(data!!)

            binding.rcvBom.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvBom.adapter=BomAdapter(requireContext(), listBestOfTheMonth)

        }

        db.collection("thecolortone").addSnapshotListener { value, error ->

            val listTheColorTone = arrayListOf<colortoneModel>()
            val data = value?.toObjects(colortoneModel::class.java)
            listTheColorTone.addAll(data!!)

            binding.rcvTct.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvTct.adapter=colortoneAdapter(requireContext(), listTheColorTone)

        }

        db.collection("categories").addSnapshotListener { value, error ->
        val listOfCategory = arrayListOf<catModel>()
            val data = value?.toObjects(catModel::class.java)
            listOfCategory.addAll(data!!)

            binding.rcvCat.layoutManager=GridLayoutManager(requireContext(), 2)
            binding.rcvCat.adapter=CatAdapter(requireContext(), listOfCategory)
        }



        return binding.root
    }

}