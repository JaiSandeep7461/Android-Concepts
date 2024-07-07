package com.example.newpermissionsandroid13

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val multiplePermissionId = 14
    private val multiplePermissionNameList =if(Build.VERSION.SDK_INT>=33){
        arrayListOf(Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIOz)
    } else {
        arrayListOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val multiplePermissionBtn = findViewById<Button>(R.id.multiplePermissions)
        multiplePermissionBtn.setOnClickListener {
            if(checkMultiplePermission()){
                doOperation()
            }
        }

    }

    private fun doOperation() {
        Toast.makeText(
            this,
            "All Permission Granted Successfully",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkMultiplePermission(): Boolean {

        val listPermissionNeeded = arrayListOf<String>()
        for (permission in multiplePermissionNameList){
            if(ContextCompat.checkSelfPermission(
                    this,
                    permission
                )!= PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(permission)
            }
        }

        if(listPermissionNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(
                this,
                listPermissionNeeded.toTypedArray(),
                multiplePermissionId
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==multiplePermissionId){
            if(grantResults.isNotEmpty()){
                var isGrant = true
                for (element in grantResults){
                    if(element == PackageManager.PERMISSION_DENIED){
                        isGrant= false
                    }
                }
                if(isGrant){
                    //here all Permissions granted Successfully
                    doOperation()
                }else{
                    var someDenied = false
                    for (permission in permissions){
                        if(!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permission
                            )){
                            if(ActivityCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_DENIED){
                                someDenied = true
                            }
                        }
                    }

                    if(someDenied){
                        //here app Setting open because all permission is not granted
                        // and permanent denied
                        appSettingOpen(this)
                    }else{
                        //here warning permission show
                        warningPermissionDialog(this){ _: DialogInterface, which:Int ->
                            when(which){
                                DialogInterface.BUTTON_POSITIVE ->checkMultiplePermission()
                            }

                        }
                    }
                }
            }
        }
    }

}