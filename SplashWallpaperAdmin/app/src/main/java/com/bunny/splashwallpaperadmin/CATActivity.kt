package com.bunny.splashwallpaperadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bunny.splashwallpaper.Model.catModel
import com.bunny.splashwallpaperadmin.Adapter.CatAdapter
import com.bunny.splashwallpaperadmin.databinding.ActivityCatactivityBinding
import com.bunny.splashwallpaperadmin.databinding.ActivityTctactivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class CATActivity : AppCompatActivity() {

    lateinit var binding: ActivityCatactivityBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        db.collection("categories").addSnapshotListener { value, error ->
            val listOfCategory = arrayListOf<catModel>()
            val data = value?.toObjects(catModel::class.java)
            listOfCategory.addAll(data!!)

            binding.rcvCAT.layoutManager= GridLayoutManager(this@CATActivity, 2)
            binding.rcvCAT.adapter= CatAdapter(this@CATActivity, listOfCategory)
        }

        binding.btnDone.setOnClickListener {
            if (binding.edtName.text.toString().isEmpty())
            {
                Toasty.warning(this, "Add Name For The Category", Toast.LENGTH_SHORT, true).show();
            } else if (binding.edtLink.text.toString().isEmpty())
            {
                Toasty.warning(this, "Paste Your Link", Toast.LENGTH_SHORT, true).show();
            } else if (binding.edtName.text.toString().isEmpty() && binding.edtLink.text.toString().isEmpty())
            {
                Toasty.warning(this, "Add Name For The Category & Paste Your Link", Toast.LENGTH_SHORT, true).show();
            } else
            {
                addDataToDB(binding.edtLink.text.toString(), binding.edtName.text.toString())
            }
        }

    }

    private fun addDataToDB(link: String, name: String) {

        val uid = db.collection("categories").id

        val finalData = catModel(name= name, id = uid, link = link)

        db.collection("categories").document(uid).set(finalData).addOnCompleteListener {
            if (it.isSuccessful) {
                Toasty.success(this@CATActivity, "Color added Successfully!", Toast.LENGTH_SHORT, true)
                    .show();
                binding.edtLink.setText("")
                binding.edtLink.clearFocus()
                binding.edtName.setText("")
                binding.edtName.clearFocus()
            } else {
                Toasty.error(
                    this@CATActivity,
                    "" + it.exception?.localizedMessage,
                    Toast.LENGTH_SHORT,
                    true
                ).show();
                binding.edtLink.setText("")
                binding.edtLink.clearFocus()
                binding.edtName.setText("")
                binding.edtName.clearFocus()
            }
        }
    }
}