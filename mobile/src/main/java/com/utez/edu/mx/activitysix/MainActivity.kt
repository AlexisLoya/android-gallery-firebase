package com.utez.edu.mx.activitysix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.databinding.ActivityMainBinding

const val EXTRA_MESSAGE = "com.utez.edu.mx.activitysix.MESSAGE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        binding.addCollection.setOnClickListener {
            val editText = "Hello"
            val message = editText
            val intent = Intent(this, MainActivityFormCollection::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)

        }
    }
}