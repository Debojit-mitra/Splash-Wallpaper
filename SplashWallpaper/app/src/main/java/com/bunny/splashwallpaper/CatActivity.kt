package com.bunny.splashwallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bunny.splashwallpaper.Adapter.CatImagesAdapter
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaper.Model.catModel
import com.bunny.splashwallpaper.databinding.ActivityCatBinding
import com.google.firebase.firestore.FirebaseFirestore

class CatActivity : AppCompatActivity() {

    lateinit var binding: ActivityCatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")


        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->

            val listOfCatWallpaper = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listOfCatWallpaper.addAll(data!!)

                binding.catTitle.text=name.toString()
                binding.catCount.text="${listOfCatWallpaper.size} Wallpaper Available"

            binding.catRcv.layoutManager =
                StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            binding.catRcv.adapter = CatImagesAdapter(this, listOfCatWallpaper)

        }


    }
}