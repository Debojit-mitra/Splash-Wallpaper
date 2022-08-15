package com.bunny.splashwallpaperadmin.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaperadmin.R
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty


class CatImagesAdapter(
    val requireContext: Context,
    val listBestOfTheMonth: ArrayList<BomModel>,
    val uid: String
) :
    RecyclerView.Adapter<CatImagesAdapter.bomViewHolder>(){

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageview = itemView.findViewById<ImageView>(R.id.catImage)
        val btndelete = itemView.findViewById<ImageView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        Glide.with(requireContext).load(listBestOfTheMonth[position].link).into(holder.imageview)

        holder.btndelete.setOnClickListener {
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
                    db.collection("categories").document(uid).collection("wallpaper").document(listBestOfTheMonth[position].id).delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toasty.success(
                                    requireContext,
                                    "Wallaper Deleted Successfully!",
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

    override fun getItemCount() = listBestOfTheMonth.size
}