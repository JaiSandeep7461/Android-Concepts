package com.example.recive_data

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ViewFlipper

class MainActivity : AppCompatActivity() {

    private lateinit var singleImageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receivingText = findViewById<TextView>(R.id.recieveText)
        singleImageView = findViewById(R.id.singleImageView)


        when(intent.action){
            Intent.ACTION_SEND ->{
                if(intent.type =="text/plain"){
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                        receivingText.text = it
                    }
                }else if(intent.type?.startsWith("image/")==true){
                    handleSendImage(intent)
                }
            }
            Intent.ACTION_SEND_MULTIPLE ->{
                if(intent.type?.startsWith("image/")==true){
                    handleSendMultipleImages(intent)
                }
            }
        }
    }

    private fun handleSendImage(intent: Intent){
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri )?.let {
            singleImageView.setImageURI(it)
        }
    }

    private fun handleSendMultipleImages(intent: Intent){
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            val viewFlipper = findViewById<ViewFlipper>(R.id.viewFlipper)

            for (image in it){
                val imageView = ImageView(this)
                imageView.setImageURI(image as Uri?)
                imageView.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                viewFlipper.addView(imageView)
            }

            val previousButton = findViewById<Button>(R.id.btnPrev)
            previousButton.setOnClickListener {
                viewFlipper.showPrevious()
            }
            val nextButton = findViewById<Button>(R.id.btnNext)
            nextButton.setOnClickListener {
                viewFlipper.showNext()
            }

        }
    }
}