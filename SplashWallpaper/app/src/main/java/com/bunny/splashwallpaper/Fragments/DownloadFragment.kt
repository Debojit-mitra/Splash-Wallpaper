package com.bunny.splashwallpaper.Fragments

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bunny.splashwallpaper.Adapter.CollectionAdapter
import com.bunny.splashwallpaper.databinding.ActivityMainBinding
import com.bunny.splashwallpaper.databinding.FragmentDownloadBinding
import java.io.File

class DownloadFragment : Fragment() {

    lateinit var binding: FragmentDownloadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentDownloadBinding.inflate(layoutInflater, container, false)

        val allFiles:Array<File>
        val imageList = arrayListOf<String>()

        val targetPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/Splash Wallpaper"

        val targetFile = File(targetPath)
        allFiles = targetFile.listFiles()!!

        for(data in allFiles){
            imageList.add(data.absolutePath)
        }

        val downcount = imageList.size
        if(downcount == 1)
        {
            binding.downloadCount.text="${imageList.size} Downloaded Wallpaper"
        }
        else
        {
            binding.downloadCount.text="${imageList.size} Downloaded Wallpapers"
        }

        binding.rcvCollection.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rcvCollection.adapter = CollectionAdapter(requireContext(),imageList)

        return binding.root

    }


}