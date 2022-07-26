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
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.adapter.Menu
import com.utez.edu.mx.activitysix.adapter.MenuAdapter
import com.utez.edu.mx.activitysix.databinding.ActivityMainFormCollectionBinding

class MainActivityFormCollection : AppCompatActivity() {
    companion object{
        const val  TAG = "firebase"


    }
    private lateinit var binding: ActivityMainFormCollectionBinding
    private lateinit var imageUrl: Uri
    private var isNotEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_form_collection)
        binding = ActivityMainFormCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Layout elements
        val nameText:TextView = binding.editName
        val submitBtn:Button = binding.submitBtn
        val selectBtn:Button = binding.selectBtn

        // InputText
        nameText.setOnClickListener{title = nameText.text.toString().lowercase().trim().replace(" ","")}
        // From gallery
        selectBtn.setOnClickListener { requestPermission() }
        // Save Image
        submitBtn.setOnClickListener {
            if(isNotEmpty && nameText.text.toString().isNotEmpty() && !nameText.text.toString().equals(MainActivity.OPTIONS)){
                FirebaseStoreManager().uploadImage(this,imageUrl,nameText.text.toString())
                // Move
                val intent = Intent(this, MainActivity::class.java).apply {}
                startActivity(intent)
            }else{
                val toast = Toast.makeText(this, "Es necesario llenar todos los campos", Toast.LENGTH_LONG)
                toast.show()
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
            isNotEmpty = true
            binding.viewImage.setImageURI(data)
        }
    }
    private fun pickUpFromGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityResult.launch(intent)
    }

}
