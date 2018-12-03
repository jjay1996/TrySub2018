package com.egco428.trysub


import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import com.egco428.trysub.Mini_Game.minigame
import android.util.Log
import android.widget.Toast
import com.egco428.trysub.Create_Login.choose
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*
import java.io.File
import android.support.annotation.NonNull
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FileDownloadTask
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_minigame.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        textView2!!.animation = animate3
        imageView4!!.animation = animate2
        startBtn!!.animation = animate4

        startBtn.setOnClickListener {
            val intent = Intent(this, choose::class.java)
            startActivity(intent)
            finish()
        }
/*
        var storageReference = FirebaseStorage.getInstance().getReference().child("myimage");


        var image = findViewById<ImageView>(R.id.imageView);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(image );*/
    }




}




