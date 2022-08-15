package com.bunny.splashwallpaperadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaperadmin.Adapter.CatImagesAdapter
import com.bunny.splashwallpaperadmin.databinding.ActivityFinalCatBinding
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class FinalCatActivity : AppCompatActivity() {

    lateinit var binding: ActivityFinalCatBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")

        // binding.catTitle.text = name.toString()
        binding.catTitle.text = "${name.toString()} Wallpaper"

        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->

                val listOfCatWallpaper = arrayListOf<BomModel>()
                val data = value?.toObjects(BomModel::class.java)
                listOfCatWallpaper.addAll(data!!)

                binding.catTitle.text = name.toString()

                val countwall = listOfCatWallpaper.size
                if (countwall == 1) {
                    binding.catCount.text = "${listOfCatWallpaper.size} Wallpaper Available"
                } else {
                    binding.catCount.text = "${listOfCatWallpaper.size} Wallpapers Available"
                }

                binding.rcvFinalCat.layoutManager =
                    GridLayoutManager(this@FinalCatActivity, 2)
                binding.rcvFinalCat.adapter = CatImagesAdapter(this, listOfCatWallpaper, uid)


            }

        binding.btnDone.setOnClickListener {
            if (binding.edtLink.text.toString().isEmpty()) {
                Toasty.warning(this, "Paste Your Link", Toast.LENGTH_SHORT, true).show();
            } else {

                val finalUniqID = db.collection("categories").document().id
                val finalData = BomModel(id = finalUniqID, link = binding.edtLink.text.toString())
                db.collection("categories").document(uid).collection("wallpaper")
                    .document(finalUniqID).set(finalData).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toasty.success(this@FinalCatActivity, "Wallpaper added Successfully!", Toast.LENGTH_SHORT, true)
                                .show();
                            binding.edtLink.setText("")
                            binding.edtLink.clearFocus()
                        } else {
                            Toasty.error(
                                this@FinalCatActivity,
                                "" + it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT,
                                true
                            ).show();
                            binding.edtLink.setText("")
                            binding.edtLink.clearFocus()
                        }
                }


            }
        }

    }
}