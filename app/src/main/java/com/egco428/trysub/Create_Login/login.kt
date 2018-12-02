package com.egco428.trysub.Create_Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.egco428.trysub.PlayActivity
import com.egco428.trysub.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    private var username:String? = null
    private var password:String? = null
    private var pass:Boolean=false
    var dataSnapshot:DataSnapshot? = null
    var keyPath:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
              dataSnapshot=p0
            }
        })

        confBtn.setOnClickListener {
            for (i in dataSnapshot!!.children) {
                username = i.child("username").value.toString()
                password = i.child("password").value.toString()
                keyPath = i.key.toString()
                if (userLogin.text.toString() == username){
                    break
                }
            }

            if (userLogin.text.toString().trim() == "" || passLogin.text.toString().trim() == ""){
                pass = false
                Toast.makeText(applicationContext,"Input is Emphty",Toast.LENGTH_SHORT).show()
            }
            if ( userLogin.text.toString() == username && passLogin.text.toString() == password){
                pass = true
            }else{
                pass = false
                Toast.makeText(applicationContext,"Username or Password failed",Toast.LENGTH_SHORT).show()
            }

            if (pass==true) {
                val t = Intent(this,PlayActivity::class.java)
                t.putExtra("keyPath",keyPath)
                Log.d(this.toString(),"keyPath : $keyPath")
                startActivity(t)
                finish()
            }
        }

        canBtn.setOnClickListener {
            val intent = Intent(this,choose::class.java)
            startActivity(intent)
            finish()
        }
    }
}
