package com.egco428.trysub.Create_Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.egco428.trysub.R

class login : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       /* database = FirebaseDatabase.getInstance().getReference("dataMsg")
        database.addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (i in p0.children){
                    Log.d("data","I'm Here !!: ${i} !! : ${p0.children}")
                    val message : com.egco427.ex15_firebasewrite.Message? = i.getValue(com.egco427.ex15_firebasewrite.Message::class.java)
                    Log.d("data","I'm Here  2 !!: ${message}")

                }

            }
        })*/
    }
}
