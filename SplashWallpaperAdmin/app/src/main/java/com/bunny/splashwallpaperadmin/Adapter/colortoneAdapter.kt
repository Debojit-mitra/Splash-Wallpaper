package com.bunny.splashwallpaperadmin.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bunny.splashwallpaper.Model.colortoneModel
import com.bunny.splashwallpaperadmin.R
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty


class colortoneAdapter(
    val requireContext: Context,
    val listTheColorTone: ArrayList<colortoneModel>
) :
    RecyclerView.Adapter<colortoneAdapter.bomViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardBack = itemView.findViewById<CardView>(R.id.item_Card)
        val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_colortone, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        val color = listTheColorTone[position].color
        holder.cardBack.setCardBackgroundColor(Color.parseColor(color!!))

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
                    db.collection("thecolortone").document(listTheColorTone[position].id).delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toasty.success(
                                    requireContext,
                                    "Color Tone Deleted Successfully!",
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

    override fun getItemCount() = listTheColorTone.size
}