package com.egco428.trysub


import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FileDownloadTask
import com.google.android.gms.tasks.OnSuccessListener



class MainActivity : AppCompatActivity() {

    var fileUri: Uri? = null
    lateinit var database: DatabaseReference
    private  var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("                               Try-Sub")
        startBtn.setOnClickListener {
            val intent = Intent(this,choose::class.java)
            startActivity(intent)
            //finish()
        }
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com/poryou00.jpg")
        var localFile = File.createTempFile("images", "jpg");
        //storageReference!!.getFile(localFile)

        storageReference!!.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                    // Successfully downloaded data to local file
                    Log.d("sda","success")
                   // imageView4.setImageResource(image.jpg)
                    // ...
                }).addOnFailureListener(OnFailureListener {
                    // Handle failed download
                    Log.d("sda","failed")
                    // ...
                })

        Log.d("sda","${storageReference!!.getFile(localFile)}")

        }


        //Log.d("check",objects.MyQuestion[0][0])

    }




