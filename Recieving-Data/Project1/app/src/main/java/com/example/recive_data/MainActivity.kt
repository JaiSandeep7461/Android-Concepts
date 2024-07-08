package com.example.recive_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receivingText = findViewById<TextView>(R.id.recieveText)

        when(intent.action){
            Intent.ACTION_SEND ->{
                if(intent.type =="text/plain"){
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                        receivingText.text = it
                    }
                }
            }
        }

    }
}