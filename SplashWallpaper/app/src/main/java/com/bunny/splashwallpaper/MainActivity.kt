package com.bunny.splashwallpaper

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bunny.splashwallpaper.Fragments.DownloadFragment
import com.bunny.splashwallpaper.Fragments.HomeFragment
import com.bunny.splashwallpaper.databinding.ActivityMainBinding
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import es.dmoral.toasty.Toasty
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.icHome.setOnClickListener{
        replaceFragment(HomeFragment())
        }

        binding.icDownload.setOnClickListener {
        replaceFragment(DownloadFragment())

        }

        binding.icExtra.setOnClickListener {
            Toasty.error(this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();

        }
        val permissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        val rationale = "Please provide Storage permission so that you can ..."
        val options: Permissions.Options = Permissions.Options()
            .setRationaleDialogTitle("Info")
            .setSettingsDialogTitle("Warning")

        Permissions.check(
            this ,
            permissions,
            rationale,
            options,
            object : PermissionHandler() {
                override fun onGranted() {
                    // do your task.
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
                    // permission denied, block the feature.
                }
            })

        //no internet
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()


    }
    fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentReplace, fragment)
        transaction.commit()


    }
  /*code for back button*/
    private var backPressedTime : Long = 0
    private var backToast : Toast?= null
    override fun onBackPressed() {
        if(backPressedTime + 2000 >System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        }
        else{
            /*backToast = Toast.makeText(baseContext,"Press again to exit", Toast.LENGTH_SHORT)
            backToast?.show()*/

            Toasty.warning(this, "Press or pull again to exit", Toast.LENGTH_SHORT, true).show();
        }
        backPressedTime = System.currentTimeMillis()
    }


}


