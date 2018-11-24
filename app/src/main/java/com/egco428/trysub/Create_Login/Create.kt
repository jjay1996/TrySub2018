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
import com.egco428.trysub.R
import kotlinx.android.synthetic.main.activity_create.*
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class Create : AppCompatActivity() {
    private var gender = 0
    private var T_gender = ""
    private val REQUEST_CODE = 1
    private var bitmap: Bitmap?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)






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
