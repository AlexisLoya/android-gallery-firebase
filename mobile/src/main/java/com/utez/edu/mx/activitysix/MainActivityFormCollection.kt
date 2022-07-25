package com.utez.edu.mx.activitysix

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView.HitTestResult.IMAGE_TYPE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.storage.FirebaseStorage
import com.utez.edu.mx.activitysix.databinding.ActivityMainFormCollectionBinding
import java.util.*

class MainActivityFormCollection : AppCompatActivity() {
    companion object{
        const val  TAG = "firebase"

    }
    private lateinit var binding: ActivityMainFormCollectionBinding
    private lateinit var imageUrl: Uri
    private val mStorageReference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_form_collection)
        binding = ActivityMainFormCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Layout elements
        var title:String = ""
        val nameText:TextView = binding.editName
        val submitBtn:Button = binding.submitBtn
        val selectBtn:Button = binding.selectBtn
        val db = Firebase.firestore

        // InputText
        nameText.setOnClickListener{title = nameText.text.toString().lowercase().trim().replace(" ","")}
        // From gallery
        selectBtn.setOnClickListener { requestPermission() }
        submitBtn.setOnClickListener {
            // Save Image
            val url = FirebaseStoreManager().uploadImage(this,imageUrl)
                // Save OBJECT
                val collection = hashMapOf(
                    "collection" to nameText.text.toString().lowercase(),
                    "title" to nameText.text.toString(),
                    "description" to url
                )
                db.collection("options")
                    .add(collection)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG,"DocumentSnapshot added with ID: ${documentReference.id}")
                        db.collection(nameText.text.toString().lowercase()).add(collection).addOnSuccessListener {
                            Log.d(TAG,"DocumentSnapshot added with ID: ${it}")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

        }
    }
    private fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE

                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickUpFromGallery()
                }
                else ->  requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            pickUpFromGallery()
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->

        if (isGranted){
            pickUpFromGallery()
        }else{
            Toast.makeText(
                this,
                "Permisos denegados",
                Toast.LENGTH_SHORT).show()
        }
    }

    private val startForActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            imageUrl = data!!
            binding.viewImage.setImageURI(data)
        }
    }
    private fun pickUpFromGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityResult.launch(intent)
    }

}
