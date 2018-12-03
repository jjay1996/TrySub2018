package com.egco428.trysub

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
class editProfileActivity : AppCompatActivity() {

    private var userId :String? = null
    private var picture :String? = null
    private var name :String? = null
    private var gender :String? = null
    var dataSnapshot:DataSnapshot? = null
    lateinit var database: DatabaseReference
    private  var storage:FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var fileUri: Uri? = null
    private val REQUEST_CODE = 1
    private var bitmap: Bitmap?= null
    private val IMAGE_REQUEST = 1234
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        userId = intent.getStringExtra("keyPath")

        //Do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        //right
        editProfileTitle!!.animation = animate2
        imageProfileEdit!!.animation = animate2
        takePicBtnEdit!!.animation = animate2
        chooseBtnEdit!!.animation = animate2
        textView71!!.animation = animate2
        editName!!.animation = animate2
        textView81!!.animation = animate2
        femaleSelect!!.animation = animate2
        //left
        maleSelect!!.animation = animate1
        //buttom
        saveBtn!!.animation = animate4
        CancelBtnEdit!!.animation = animate4
        //End do animation

        var checkOnce = false
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com")
        database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(p0: DataSnapshot?) {
                if(!checkOnce){
                    checkOnce = true
                    dataSnapshot = p0
                }
            }
        })

        maleSelect.setOnClickListener {
            gender = "1" // male
            maleSelect.setImageResource(R.drawable.c_male)
            femaleSelect.setImageResource(R.drawable.female)
        }
        femaleSelect.setOnClickListener {
            gender = "0" // Female
            femaleSelect.setImageResource(R.drawable.c_female)
            maleSelect.setImageResource(R.drawable.male)
        }

        takePicBtnEdit.setOnClickListener {
            askCameraPermission()
        }
        chooseBtnEdit.setOnClickListener {
            pickPhotoFromGallery()
        }

        saveBtn.setOnClickListener {
            var check = true

            name = editName.text.toString()
            var checkName:String? = null
            for (i in dataSnapshot!!.children){
                if (editName.text.toString().trim() == i.child("name").value.toString()){
                    if(i.key.toString() != userId) checkName = i.child("name").value.toString()
                }
            }
            if (name == checkName ){Toast.makeText(applicationContext,"Pleases Change name",Toast.LENGTH_SHORT).show() ; check=false }
            else if (name!!.length >15 ){Toast.makeText(applicationContext,"Length(Name) not more than 15",Toast.LENGTH_SHORT).show() ; check=false }

            picture = "${dataSnapshot!!.child(userId).child("username").value.toString()}.jpg"

            if(check) {
                val genderr = FirebaseDatabase.getInstance().getReference("User/$userId/gender")
                genderr.setValue("${gender}");

                val namee = FirebaseDatabase.getInstance().getReference("User/$userId/name")
                namee.setValue("${name}");

                val picturee = FirebaseDatabase.getInstance().getReference("User/$userId/picture")
                picturee.setValue("${picture}");

                //Upload photo
                if (fileUri != null) {
                    Toast.makeText(applicationContext, "Uploading", Toast.LENGTH_SHORT).show()
                    val imageRef = storageReference!!.child(picture!!)
                    imageRef.putFile(fileUri!!)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext, "File Upload...", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_SHORT).show()
                            }
                            .addOnProgressListener {
                                val progress = 100 * it.bytesTransferred / it.totalByteCount
                                Toast.makeText(applicationContext, "Uploading" + progress.toInt() + "%", Toast.LENGTH_SHORT).show()
                            }
                }


                var intentToProfile = Intent(this, ProfileActivity::class.java)
                intentToProfile.putExtra("keyPath", userId)
                startActivity(intentToProfile)
                finish()
            }
        }

        CancelBtnEdit.setOnClickListener {
            var intentToProfile = Intent(this,ProfileActivity::class.java)
            intentToProfile.putExtra("keyPath",userId)
            startActivity(intentToProfile)
            finish()
        }


        var database2 = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database2.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                picture = p0!!.child("picture").value.toString()
                name = p0!!.child("name").value.toString()
                gender = p0!!.child("gender").value.toString()

                if(picture!="null"){
                    var storage = FirebaseStorage.getInstance()
                    var storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com/${picture}")
                    var localFile = File.createTempFile("images", "jpg");
                    storageReference!!.getFile(localFile)
                            .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                imageProfileEdit.setImageBitmap(bitmap)
                            }).addOnFailureListener(OnFailureListener {

                            })
                }

                editName.setText(name)

                if(gender == "1") maleSelect.setImageResource(R.drawable.c_male)
                else if(gender == "0") femaleSelect.setImageResource(R.drawable.c_female)
            }
        })
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
                            Toast.makeText(this@editProfileActivity, "All permissions need to be granted to take photo", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {/* ... */
                        //show alert dialog with permission options
                        AlertDialog.Builder(this@editProfileActivity)
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
            imageProfileEdit.setImageURI(fileUri)
        }else if(resultCode == Activity.RESULT_OK
                && requestCode == IMAGE_REQUEST){
            //photo from gallery
            fileUri = data?.data
            imageProfileEdit.setImageURI(fileUri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
