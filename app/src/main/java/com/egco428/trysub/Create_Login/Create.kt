package com.egco428.trysub.Create_Login

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.egco428.trysub.DataSourse
import com.egco428.trysub.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create.*
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class Create : AppCompatActivity() {
    private var gender = 0
    private var T_gender = ""
    private val REQUEST_CODE = 1
    private var bitmap: Bitmap?= null
    lateinit var database: DatabaseReference
    var check = true
    var name = ""
    var username = ""
    var pass =""
    var pass2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)


       var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (i in p0!!.children){
                    Log.d("data","I'm Here !!: ${i} !! : ${p0.children}")
                    val message = i.getValue(DataSourse::class.java)
                    Log.d("data","I'm Here  2 !!: ${message}")

                    if (username == message!!.username.toString() ){
                        Log.d("data","I'm Here  3 !!: ${message.username}")
                        check = false
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

        confirmBtn.setOnClickListener {
            Toast.makeText(applicationContext,"gender : $T_gender ",Toast.LENGTH_SHORT).show()
            name = editText.text.toString()
            username = editText2.text.toString()
            pass =passCreTextpla.text.toString()
            pass2 = passCreCheckTextpla.text.toString()

            val messageId = database.push().key

            //check
            // Pohibit " ' , . (เว้นวรรค)
            if (check==false){ Toast.makeText(applicationContext,"Pleases Change Username",Toast.LENGTH_SHORT).show() }
            if (pass != pass2){Toast.makeText(applicationContext,"Password Mismatch",Toast.LENGTH_SHORT).show() ; check=false}


            //End check

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
                picture.setValue("${username}");

            }

        }

        CancelBtn.setOnClickListener {

            finish()
        }

    }

    // Take Photo
    fun onClick(view: View){
        val intent = Intent()
        intent.type = "image/*" //image not name directory
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var stream : InputStream? = null
        if(requestCode==REQUEST_CODE && resultCode== Activity.RESULT_OK){
            try {
                if(bitmap != null){
                    bitmap!!.recycle() // recycle similar refreshing
                }
                stream = contentResolver.openInputStream(data!!.data)
                bitmap = BitmapFactory.decodeStream(stream)
                Log.d("test","$bitmap")
                imageView.setImageBitmap(bitmap)
            }catch (e : FileNotFoundException){
                e.printStackTrace()
            }finally {
                if(stream != null){
                    try {
                        stream.close()
                    }catch (e : IOException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    //End Take Photo


}
