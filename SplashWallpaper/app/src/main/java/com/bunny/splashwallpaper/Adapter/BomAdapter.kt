package com.bunny.splashwallpaper.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bunny.splashwallpaper.FinalWallpaper
import com.bunny.splashwallpaper.Model.BomModel
import com.bunny.splashwallpaper.R

class BomAdapter(val requireContext: Context,val listBestOfTheMonth: ArrayList<BomModel>) :
    RecyclerView.Adapter<BomAdapter.bomViewHolder>(){

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageview = itemView.findViewById<ImageView>(R.id.bom_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_bom, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        Glide.with(requireContext).load(listBestOfTheMonth[position].link).into(holder.imageview);

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, FinalWallpaper::class.java)
            intent.putExtra("Link",listBestOfTheMonth[position].link)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount() = listBestOfTheMonth.size
}