package com.bunny.splashwallpaperadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaperadmin.Adapter.BomAdapter
import com.bunny.splashwallpaperadmin.databinding.ActivityBomactivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class BOMActivity : AppCompatActivity() {

    lateinit var binding: ActivityBomactivityBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBomactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        db.collection("bestofmonth").addSnapshotListener { value, error ->
            val listBestOfTheMonth = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listBestOfTheMonth.addAll(data!!)

            binding.rcvBOM.layoutManager =
                GridLayoutManager(this@BOMActivity, 2)
            binding.rcvBOM.adapter = BomAdapter(this@BOMActivity, listBestOfTheMonth)

        }

        binding.btnDone.setOnClickListener {
            if (binding.edtLink.text.toString().isEmpty()) {
                Toasty.warning(this, "Paste Your Link", Toast.LENGTH_SHORT, true).show();
            } else {
                addLinkToDatabase(binding.edtLink.text.toString())
            }
        }

    }

    private fun addLinkToDatabase(wallpaperLink: String) {
        val uid = db.collection("bestofmonth").document().id
        val finalData = BomModel(uid, wallpaperLink)

        db.collection("bestofmonth").document(uid).set(finalData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toasty.success(this@BOMActivity, "Wallpaper added Successfully!", Toast.LENGTH_SHORT, true)
                        .show();
                    binding.edtLink.setText("")
                    binding.edtLink.clearFocus()
                } else {
                    Toasty.error(
                        this@BOMActivity,
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