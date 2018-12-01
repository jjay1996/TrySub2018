package com.egco428.trysub.Create_Login

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.Toast
import com.egco428.trysub.DataSourse
import com.egco428.trysub.Manifest
import com.egco428.trysub.PlayActivity
import com.egco428.trysub.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create.*
import java.io.*

class Create : AppCompatActivity() {
    private var gender = 0
    private var T_gender = ""
    private val REQUEST_CODE = 1
    private var bitmap: Bitmap?= null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val IMAGE_REQUEST = 1234
    private var filePath: Uri? = null
    lateinit var database: DatabaseReference
    private  var storage:FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var LockId :DataSourse? = null //User Now
    var checkUser = ""
    var check = true
    var name = ""
    var username = ""
    var pass =""
    var pass2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //Cheke vesion


        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

       var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (i in p0!!.children){
                   // Log.d("data","I'm Here !!: ${i} !! : ${p0.children}")
                    val message = i.getValue(DataSourse::class.java)
                   // Log.d("data","I'm Here  2 !!: ${message}")

                    if (username == message!!.username.toString() ){
                        //Log.d("data","I'm Here  3 !!: ${message.username}")
                        checkUser=message!!.username.toString()

                    }

                }

            }
        })








            //Choose gender
        imageButton.setOnClickListener {
            gender = 0 // Female
            imageButton.setImageResource(R.drawable.c_female)
            imageButton2.setImageResource(R.drawable.male)
            T_gender = "female"
        }
        imageButton2.setOnClickListener {
            gender = 1 // male
            imageButton2.setImageResource(R.drawable.c_male)
            imageButton.setImageResource(R.drawable.female)
            T_gender = "male"
        }
            //end Choose gender

        // Confirm BTN
        confirmBtn.setOnClickListener {
            name = editText.text.toString().trim()
            username = editText2.text.toString().trim()
            pass =passCreTextpla.text.toString().trim()
            pass2 = passCreCheckTextpla.text.toString().trim()


            val messageId = database.push().key

            //check
            // Pohibit " ' , . (เว้นวรรค)
            if (username == "" || pass == "" || name == ""){check = false}
            else if (username.length < 8){Toast.makeText(applicationContext,"Length want more than 8",Toast.LENGTH_SHORT).show() ; check=false}
            else if (username == checkUser){ Toast.makeText(applicationContext,"Pleases Change Username",Toast.LENGTH_SHORT).show() ; check=false }
            else if (pass != pass2){Toast.makeText(applicationContext,"Password Mismatch",Toast.LENGTH_SHORT).show() ; check=false}
            else {check = true }

            //End check

            //set init id
            if (check==true) {

                val user = FirebaseDatabase.getInstance().getReference("User/$messageId/username")
                user.setValue("${username}");

                val namee = FirebaseDatabase.getInstance().getReference("User/$messageId/name")
                namee.setValue("${name}");

                val password = FirebaseDatabase.getInstance().getReference("User/$messageId/password")
                password.setValue("${pass}");

                val sex = FirebaseDatabase.getInstance().getReference("User/$messageId/gender")
                sex.setValue("${gender}");

                val picture = FirebaseDatabase.getInstance().getReference("User/$messageId/picture")
                picture.setValue("${username}.jpg");

                val id = FirebaseDatabase.getInstance().getReference("User/$messageId/id")
                id.setValue("${messageId}");

                val unlock = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/unlock_level")
                unlock.setValue("1");

                val total_score = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/total_score")
                total_score.setValue("0");

                for (i in 1 .. 10) {
                    var test =  FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission")
                    val testId = test.push().key
                    var level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/${testId}/score")
                    level.setValue("0");
                    level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/${testId}/id")
                    level.setValue("$testId");
                    level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/${testId}/level")
                    level.setValue("$i");
                    level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/${testId}/mini_score")
                    level.setValue("0");
                    level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/${testId}/total")
                    level.setValue("0");
                }

                //Upload photo
                if (filePath!=null){
                    Toast.makeText(applicationContext,"Uploading",Toast.LENGTH_SHORT).show()
                    val imageRef = storageReference!!.child("")
                    imageRef.putFile(filePath!!)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext,"File Upload...",Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(applicationContext,"Failed...",Toast.LENGTH_SHORT).show()
                            }
                            .addOnProgressListener {
                                val progress = 100 * it.bytesTransferred/ it.totalByteCount
                                Toast.makeText(applicationContext,"Uploading"+progress.toInt()+"%",Toast.LENGTH_SHORT).show()
                            }
                }
                //End Upload

                //go home page
                val t = Intent(this,PlayActivity::class.java)
                startActivity(t)
                finish()
            }
            //end set

        } // End Comfirm BTN

        //Cancel Btn
        CancelBtn.setOnClickListener {
            finish()
        }
        //end Cancel Btn

        //take & choose pic
        takePicBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
        }
        chooseBtn.setOnClickListener {
            val intent=Intent()
            intent.type = "*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Select Pic"),IMAGE_REQUEST)
        }
        //end take & choose pic

    }

    // Take Photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras
            val photo = extras.get("data") as Bitmap
            var phot =MediaStore.Images.Media.getContentUri(photo.toString())
            Log.d("Qa","$phot")

            //photoImageView.setImageBitmap(photo)

            imageView.setImageBitmap(photo)
        }
        if(requestCode == IMAGE_REQUEST && data != null
                && resultCode == Activity.RESULT_OK && data.data !=null){
            filePath=data.data
            Log.d("Path","[]")
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                imageView.setImageBitmap(bitmap)
            }catch (e:IOError){
                e.printStackTrace()
            }


        }


    }

/*    fun createPhotoFile():File{
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image:File? = null

        try {
            image = File.createTempFile("image${System.currentTimeMillis()/1000}",".jpg",storageDir)
        }catch (e:IOError){e.printStackTrace()}
        return image!!
    }*/





}
