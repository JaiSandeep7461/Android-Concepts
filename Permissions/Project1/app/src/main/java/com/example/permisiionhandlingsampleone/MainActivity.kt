package com.example.permisiionhandlingsampleone

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val permissionRequestedId = 1
    private val permissionName= Manifest.permission.READ_CONTACTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val singlePermission = findViewById<Button>(R.id.singlePermissionBtn)
        singlePermission.setOnClickListener {
            if(checkSinglePermission(
                this,
                permissionName,
                permissionRequestedId
            )){
                doOperation()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==permissionRequestedId){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                doOperation()
            }else{
                if(!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permissionName
                )){
                    appSettingOpen(this)
                }else{
                    warningPermissionDialog(this){_:DialogInterface?,which:Int->

                        when(which){
                            DialogInterface.BUTTON_POSITIVE ->{
                                if(checkSinglePermission(this,
                                    permissionName,
                                    permissionRequestedId)){
                                    doOperation()
                                }
                            }
                        }

                    }

                }
            }
        }
    }

    private fun doOperation() {
        Toast.makeText(
            this,
            "Permission Grant Successfully",
            Toast.LENGTH_LONG
        ).show()
    }
}