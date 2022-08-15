package com.bunny.splashwallpaperadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunny.splashwallpaper.Model.colortoneModel
import com.bunny.splashwallpaperadmin.Adapter.colortoneAdapter
import com.bunny.splashwallpaperadmin.databinding.ActivityTctactivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class TCTActivity : AppCompatActivity() {

    lateinit var binding: ActivityTctactivityBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTctactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        db.collection("thecolortone").addSnapshotListener { value, error ->

            val listTheColorTone = arrayListOf<colortoneModel>()
            val data = value?.toObjects(colortoneModel::class.java)
            listTheColorTone.addAll(data!!)

            binding.rcvTCT.layoutManager =
                GridLayoutManager(this@TCTActivity, 2)
            binding.rcvTCT.adapter= colortoneAdapter(this@TCTActivity, listTheColorTone)

        }

        binding.btnDone.setOnClickListener {
            if (binding.edtColor.text.toString().isEmpty())
            {
                Toasty.warning(this, "Add Your Color", Toast.LENGTH_SHORT, true).show();
            } else if (binding.edtLink.text.toString().isEmpty())
            {
                Toasty.warning(this, "Paste Your Link", Toast.LENGTH_SHORT, true).show();
            } else if (binding.edtColor.text.toString().isEmpty() && binding.edtLink.text.toString().isEmpty())
            {
                Toasty.warning(this, "Add Your Color & Paste Your Link", Toast.LENGTH_SHORT, true).show();
            } else
            {
                addWallpaperToDB(binding.edtColor.text.toString(), binding.edtLink.text.toString())
            }
        }
    }

    private fun addWallpaperToDB(color: String, link: String) {
       val uid = db.collection("thecolortone").document().id

        val finalData = colortoneModel(id = uid,link = link, color = color)
        db.collection("thecolortone").document(uid).set(finalData).addOnCompleteListener {
            if (it.isSuccessful) {
                Toasty.success(this@TCTActivity, "Color added Successfully!", Toast.LENGTH_SHORT, true)
                    .show();
                binding.edtLink.setText("")
                binding.edtLink.clearFocus()
                binding.edtColor.setText("")
                binding.edtColor.clearFocus()
            } else {
                Toasty.error(
                    this@TCTActivity,
                    "" + it.exception?.localizedMessage,
                    Toast.LENGTH_SHORT,
                    true
                ).show();
                binding.edtLink.setText("")
                binding.edtLink.clearFocus()
                binding.edtColor.setText("")
                binding.edtColor.clearFocus()
            }
        }
    }
}