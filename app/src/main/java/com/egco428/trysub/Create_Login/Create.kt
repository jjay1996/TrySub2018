package com.egco428.trysub.Create_Login

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.egco428.trysub.PlayActivity
import com.egco428.trysub.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import java.util.regex.Pattern

class Create : AppCompatActivity() {
    private var gender = 0
    private var T_gender = ""
    private val REQUEST_CODE = 1
    private var bitmap: Bitmap?= null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val IMAGE_REQUEST = 1234
    var fileUri: Uri? = null
    lateinit var database: DatabaseReference
    private  var storage:FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var dataSnapshot:DataSnapshot? = null
    var checkUser = ""
    var check = false
    var name = ""
    var username = ""
    var pass =""
    var pass2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)



        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com")
       var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(p0: DataSnapshot?) {
                dataSnapshot = p0

            }
        })








            //Choose gender
        imageView2.setOnClickListener {
            gender = 1 // male
            imageView2.setImageResource(R.drawable.c_male)
            imageView3.setImageResource(R.drawable.female)
            T_gender = "male"
        }
        imageView3.setOnClickListener {
            gender = 0 // Female
            imageView3.setImageResource(R.drawable.c_female)
            imageView2.setImageResource(R.drawable.male)
            T_gender = "female"
        }
            //end Choose gender

        // Confirm BTN
        confirmBtn.setOnClickListener {
            name = editText.text.toString().trim()
            username = userCreate.text.toString().trim()
            pass =passCreTextpla.text.toString().trim()
            pass2 = passCreCheckTextpla.text.toString().trim()
            var checkName : String = ""

            val messageId = database.push().key

            //check validate
            // Pohibit " ' , . (เว้นวรรค)
            for (i in dataSnapshot!!.children){
                var user = i.child("username").value.toString()
                if (userCreate.text.toString().trim()== user ){
                    checkUser= user
                    checkName = i.child("name").value.toString()
                    break
                }

            }

            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            var exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            var pattern = Pattern.compile(exp)
            var matcher1 = pattern.matcher(username)
            var matcher2 = pattern.matcher(pass)


            if (username == "" || pass == "" || name == ""){Toast.makeText(applicationContext,"username or name or password is empthy",Toast.LENGTH_SHORT).show() ;  check = false}
            else if (username.length < 5 || username.length >13 ){Toast.makeText(applicationContext,"Length(Username) want more than 5 but not more than 13",Toast.LENGTH_SHORT).show() ; check=false}
            else if (username == checkUser){ Toast.makeText(applicationContext,"Pleases Change Username",Toast.LENGTH_SHORT).show() ; check=false }
            else if (pass.length < 8 || pass.length >13){ Toast.makeText(applicationContext,"Length(Password) want more than 8 than 5 but not more than 13",Toast.LENGTH_SHORT).show() ; check=false }
            else if (pass != pass2){Toast.makeText(applicationContext,"Password Mismatch",Toast.LENGTH_SHORT).show() ; check=false}
            else if (name == checkName ){Toast.makeText(applicationContext,"Pleases Change name",Toast.LENGTH_SHORT).show() ; check=false }
            else if (name.length >15 ){Toast.makeText(applicationContext,"Length(Name) not more than 15",Toast.LENGTH_SHORT).show() ; check=false }
            else if (matcher1.matches()) {
                Toast.makeText(applicationContext,"(Username) No [.*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*]",Toast.LENGTH_SHORT).show() ; check = false
                Log.d("gg","$matcher1")
            }
            else if (matcher2.matches()) {
                Toast.makeText(applicationContext,"(Password) No [.*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*]",Toast.LENGTH_SHORT).show() ; check = false
                Log.d("gg","$matcher2")
            }
            else if (T_gender == ""){Toast.makeText(applicationContext,"Pleases Choose gender",Toast.LENGTH_SHORT).show() ; check=false }
            else {check = true }
            Log.d("test","check : $checkUser  !! username : $username")

            //End check validate

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
                    var level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/level${i}/score")
                    level.setValue("0");
                    level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/level${i}/mini_score")
                    level.setValue("0");
                    level = FirebaseDatabase.getInstance().getReference("User/$messageId/user_mission/mission/level${i}/total")
                    level.setValue("0");
                }

                //Upload photo
                if (fileUri!=null){
                    Toast.makeText(applicationContext,"Uploading",Toast.LENGTH_SHORT).show()
                    val imageRef = storageReference!!.child("${username}.jpg")
                    imageRef.putFile(fileUri!!)
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

            val intent = Intent(this,choose::class.java)
            startActivity(intent)
            finish()
        }
        //end Cancel Btn

        //take & choose pic
        takePicBtn.setOnClickListener {
            askCameraPermission()
        }
        chooseBtn.setOnClickListener {
            pickPhotoFromGallery()
        }
        //end take & choose pic

    }

    //launch the camera to take photo via intent
    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        fileUri = contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
        }
    }

    //ask for permission to take photo
    fun askCameraPermission(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                        if(report.areAllPermissionsGranted()){
                            //once permissions are granted, launch the camera
                            launchCamera()
                        }else{
                            Toast.makeText(this@Create, "All permissions need to be granted to take photo", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {/* ... */
                        //show alert dialog with permission options
                        AlertDialog.Builder(this@Create)
                                .setTitle(
                                        "Permissions Error!")
                                .setMessage(
                                        "Please allow permissions to take photo with camera")
                                .setNegativeButton(
                                        android.R.string.cancel,
                                        { dialog, _ ->
                                            dialog.dismiss()
                                            token?.cancelPermissionRequest()
                                        })
                                .setPositiveButton(android.R.string.ok,
                                        { dialog, _ ->
                                            dialog.dismiss()
                                            token?.continuePermissionRequest()
                                        })
                                .setOnDismissListener({
                                    token?.cancelPermissionRequest() })
                                .show()
                    }

                }).check()

    }

    private fun pickPhotoFromGallery() {

        val pickImageIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(pickImageIntent,IMAGE_REQUEST)
    }

    // Take Photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK
                && requestCode == REQUEST_IMAGE_CAPTURE) {
            //photo from camera
            //display the photo on the imageview
            imageView.setImageURI(fileUri)
        }else if(resultCode == Activity.RESULT_OK
                && requestCode == IMAGE_REQUEST){
            //photo from gallery
            fileUri = data?.data
            imageView.setImageURI(fileUri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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







