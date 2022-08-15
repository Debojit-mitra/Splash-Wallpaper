package com.bunny.splashwallpaperadmin

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bunny.splashwallpaperadmin.databinding.ActivityMainBinding
import es.dmoral.toasty.Toasty
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.cardBOM.setOnClickListener {
            startActivity(Intent(this@MainActivity,BOMActivity::class.java))
        }
        binding.cardTCT.setOnClickListener {
            startActivity(Intent(this@MainActivity,TCTActivity::class.java))
        }
        binding.cardCAT.setOnClickListener {
            startActivity(Intent(this@MainActivity,CATActivity::class.java))
        }
        binding.cardGOTO.setOnClickListener {
            var intent = packageManager.getLaunchIntentForPackage("com.bunny.splashwallpaper")
            //var intent = this.packageManager.getLaunchIntentForPackage("com.bankid.bus")
            if (intent != null) {
                // We found the activity now start the activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                /* Bring user to the market or let them choose an app?
                intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.data = Uri.parse("market://details?id=" + "com.bunny.splashwallpapers")*/
                Toasty.error(
                    this,
                    "First Install Splash Wallpaper App\uD83D\uDE42",
                    Toast.LENGTH_SHORT,
                    true
                ).show();
            }
        }
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