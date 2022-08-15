package com.bunny.splashwallpaperadmin.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaper.Model.catModel
import com.bunny.splashwallpaperadmin.FinalCatActivity
import com.bunny.splashwallpaperadmin.R
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class CatAdapter(val requireContext: Context, val listOfCat: ArrayList<catModel>) :
    RecyclerView.Adapter<CatAdapter.bomViewHolder>(){

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageview = itemView.findViewById<ImageView>(R.id.cat_image)
        val name = itemView.findViewById<TextView>(R.id.cat_name)
        val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_cat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        holder.name.text = listOfCat[position].name
        Glide.with(requireContext).load(listOfCat[position].link).into(holder.imageview);
        holder.itemView.setOnClickListener{

            val intent = Intent(requireContext, FinalCatActivity::class.java)
            intent.putExtra("uid", listOfCat[position].id)
            intent.putExtra("name", listOfCat[position].name)
            requireContext.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            val flatDialog = FlatDialog(requireContext)
            flatDialog.setTitle("               Are you sure?")
                //.setSubtitle("write your profile info here")
                .setBackgroundColor(Color.parseColor("#2C2C2C"))
                //.setFirstButtonTextColor(Color.parseColor("#FF0000"))
                .setFirstButtonColor(Color.parseColor("#FF0000"))
                .setSecondButtonColor(Color.parseColor("#AB4BFF"))
                .setIcon(R.drawable.delete_btn)
                //.setFirstTextFieldHint("email")
                //.setSecondTextFieldHint("password")
                .setFirstButtonText("DELETE")
                .setSecondButtonText("CANCEL")
                .withFirstButtonListner {
                    flatDialog.dismiss()
                    db.collection("categories").document(listOfCat[position].id).delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toasty.success(
                                    requireContext,
                                    "Category Deleted Successfully!",
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show()

                            } else {
                                Toasty.error(
                                    requireContext,
                                    "" + it.exception?.localizedMessage,
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show();
                            }
                        }
                }
                .withSecondButtonListner {
                    flatDialog.dismiss()
                }
                .show()
        }

    }

    override fun getItemCount() = listOfCat.size
}