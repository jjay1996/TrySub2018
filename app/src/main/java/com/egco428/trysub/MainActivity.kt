package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.egco428.trysub.Create_Login.choose
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var LockId :DataSourse? = null //User Now
    var Thread :Thread? = java.lang.Thread.currentThread()

   @Synchronized override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LockId=test()


        setTitle("                               Try-Sub")
        startBtn.setOnClickListener {
            //val t = Intent(this,PlayActivity::class.java)
            val t = Intent(this,choose::class.java)
            startActivity(t)
        }

//            val unlock = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/unlock_level")
//            unlock.setValue("1");

    }

    @Synchronized fun test(): DataSourse{
        var LockIdTest:DataSourse? = null
        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if (LockIdTest == null){
                    for (i in p0!!.children) {
                        Log.d("adsf", " !!:  ${i}")
                        Log.d("adsf", " !!!: ${p0} ")
                        val message = i.getValue(DataSourse::class.java)
                        if ("poryou12" == message!!.username.toString()) {
                            LockIdTest = message
                            var check=1
                            var databasetest = FirebaseDatabase.getInstance().getReference("User/${LockIdTest!!.id}/user_mission/mission")
                             databasetest.addValueEventListener(object : ValueEventListener {
                                 override fun onCancelled(p0: DatabaseError?) {}

                                 override fun onDataChange(p0: DataSnapshot?) {
                                            if(check <=10){
                                            for (i in p0!!.children) {
                                                /*Log.d("adsf", " !!:  ${i}")
                                                Log.d("adsf", " !!!: ${p0} ")*/
                                                val message2: DataSourceSupport? = i.getValue(DataSourceSupport::class.java)

                                                if ("poryou12" == LockIdTest!!.username.toString()) {
                                                    LockIdTest!!.score[message2!!.level.toInt() - 1] = message2!!.score.toInt()
                                                    LockIdTest!!.score[message2!!.level.toInt() - 1] = message2!!.mini_score.toInt()
                                                    LockIdTest!!.score[message2!!.level.toInt() - 1] = message2!!.total.toInt()
                                                    LockIdTest!!.mission_id[message2!!.level.toInt() - 1] = message2!!.id
                                                    Log.d("adsf", " !!:  ${LockIdTest!!.score[message2!!.level.toInt() - 1]}")
                                                    Log.d("adsf", " !!!: ${ LockIdTest!!.score[message2!!.level.toInt() - 1]} ")
                                                    Log.d("adsf", " !!:  ${LockIdTest!!.score[message2!!.level.toInt() - 1]}")
                                                    Log.d("adsf", " !!!: ${ LockIdTest!!.mission_id[message2!!.level.toInt() - 1] } ")
                                                    Log.d("adsf", " !!!: ${ LockIdTest!!.unlock_level } ")

                                                   check++
                                                }
                                            }
                                            }
                                     LockId = LockIdTest


                                        }

                             })

                            break
                        }

                    }
                }

            }
        })
        //End User Test
    return LockIdTest!!
    }

    fun sendToFirebase(){

    }

    override fun onResume() {
        super.onResume()



    }


}
