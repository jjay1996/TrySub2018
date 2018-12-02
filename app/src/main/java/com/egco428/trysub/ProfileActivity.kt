package com.egco428.trysub

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private var userId :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setTitle("                               Try-Sub")
        //imageView.setImageResource(R.drawable.vatar)

        userId = intent.getStringExtra("kayPath")

        EditBtn.setOnClickListener {
            val intentToEditProfile = Intent(this,editProfileActivity::class.java)
            startActivity(intentToEditProfile)
            finish()
        }

        backFromProfileBtn.setOnClickListener {
            finish()
        }

        var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                textView5.text = p0!!.child("picture").value.toString()
                var picture = p0!!.child("picture").value.toString()
                var name = p0!!.child("name").value.toString()
                var gender = p0!!.child("gender").value.toString()

                if(picture!="null"){
                    var storage = FirebaseStorage.getInstance()
                    var storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com/${picture}")
                    var localFile = File.createTempFile("images", "jpg");
                    storageReference!!.getFile(localFile)
                            .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                imageView.setImageBitmap(bitmap)
                            }).addOnFailureListener(OnFailureListener {

                            })
                }

                editText.text = name

                if(gender == "1") imageView2.setImageResource(R.drawable.c_male)
                else if(gender == "2") imageView2.setImageResource(R.drawable.c_female)
            }
        })

    }
}
