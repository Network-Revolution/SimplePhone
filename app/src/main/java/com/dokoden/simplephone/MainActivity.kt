package com.dokoden.simplephone

import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    companion object {
        const val REQUEST_PERMISSION = 100
        const val REQUEST_ROLE = 200
        const val REQUEST_TELECOM = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        offerReplacingDefaultDialer()
//        requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PERMISSION)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val action = intent?.action ?: return
        when (action) {
            "android.intent.action.MAIN" -> return
            Constants.Actions.New.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.Dialing.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.Ringing.name -> navController.navigate(R.id.ThirdFragment)
            Constants.Actions.Holding.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.Active.name -> navController.navigate(R.id.ThirdFragment)
            Constants.Actions.Disconnected.name -> navController.navigate(R.id.FirstFragment)
            Constants.Actions.SelectPhoneAccount.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.Connecting.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.Disconnecting.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.PullingCall.name -> navController.navigate(R.id.ThirdFragment)
            Constants.Actions.AudioProcessing.name -> navController.navigate(R.id.SecondFragment)
            Constants.Actions.SimulatedRinging.name -> navController.navigate(R.id.ThirdFragment)
            Constants.Actions.DeclineIncomingCall.name -> navController.navigate(R.id.FirstFragment)
            Constants.Actions.Kill.name -> navController.navigate(R.id.FirstFragment, null)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION && PermissionChecker.PERMISSION_GRANTED in grantResults) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ROLE -> when (resultCode) {
                Activity.RESULT_CANCELED -> {
                    //the user didn't set you as the default screening app...
                }
                Activity.RESULT_OK -> {
                    //The user set you as the default screening app!
                }
                Activity.RESULT_FIRST_USER -> {

                }
            }
            REQUEST_TELECOM -> when (resultCode) {
                Activity.RESULT_CANCELED -> {
                    //the user didn't set you as the default screening app...
                }
                Activity.RESULT_OK -> {
                    //The user set you as the default screening app!
                }
                Activity.RESULT_FIRST_USER -> {

                }
            }
            else -> {
            }
        }
    }

    private fun offerReplacingDefaultDialer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            val isHeld = roleManager.isRoleHeld(RoleManager.ROLE_DIALER)
            if (!isHeld) {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                startActivityForResult(intent, REQUEST_ROLE)
            }
        } else {
            val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            if (telecomManager.defaultDialerPackage != packageName) {
                val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
                startActivityForResult(intent, REQUEST_TELECOM)
            }
        }
    }
}