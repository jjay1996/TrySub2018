package com.egco428.trysub

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private var userId :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setTitle("                               Try-Sub")
        //imageView.setImageResource(R.drawable.vatar)

        userId = intent.getStringExtra("keyPath")

        //Do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)

        //top
        createText!!.animation = animate3
        //right
        textView5!!.animation = animate2
        imageView!!.animation = animate2
        textView7!!.animation = animate2
        editText!!.animation = animate2
        textView8!!.animation = animate2
        imageView2!!.animation = animate2
        EditBtn!!.animation = animate2
        //left
        backFromProfileBtn!!.animation = animate1
        //End do animation

        //go to edit profile page
        EditBtn.setOnClickListener {
            var intentToEditProfile = Intent(this,editProfileActivity::class.java)
            intentToEditProfile.putExtra("keyPath",userId)
            startActivity(intentToEditProfile)
            finish()
        }

        //go to home page
        backFromProfileBtn.setOnClickListener {
            finish()
        }

        //call firebase
        var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                var picture = p0!!.child("picture").value.toString()
                var name = p0!!.child("name").value.toString()
                var gender = p0!!.child("gender").value.toString()

                //set picture to ImageView
                if(picture!="null"){
                    var storage = FirebaseStorage.getInstance()
                    var storageReference = storage!!.getReferenceFromUrl("gs://trysub-7d847.appspot.com/${picture}")
                    var localFile = File.createTempFile("images", "jpg");
                    storageReference!!.getFile(localFile)
                            .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                imageView.setImageBitmap(bitmap)
                            }).addOnFailureListener(OnFailureListener {

                            })
                }

                //set name to TextView
                editText.text = name

                //set gender to ImgaeView
                if(gender == "1") imageView2.setImageResource(R.drawable.c_male)
                else if(gender == "0") imageView2.setImageResource(R.drawable.c_female)
            }
        })

    }
}
