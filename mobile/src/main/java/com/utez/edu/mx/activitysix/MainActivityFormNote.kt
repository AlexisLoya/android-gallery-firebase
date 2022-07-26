package com.utez.edu.mx.activitysix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.databinding.ActivityMainFormNoteBinding

class MainActivityFormNote : AppCompatActivity() {
    private lateinit var binding: ActivityMainFormNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_form_note)
        binding = ActivityMainFormNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Layout elements
        val nameText: TextView = binding.editName
        val nameDescription: TextView = binding.editName
        val submitBtn: Button = binding.submitBtn
        // Get data
        val bundle = intent.extras
        val collection = bundle?.getString(MainActivity.COLLECTION)
        val image = bundle?.getString(MainActivity.IMAGE)

        binding.textSubTitle.text = title
        submitBtn.setOnClickListener {
            val db = Firebase.firestore
            // Save OBJECT
            val note = hashMapOf(
                "title" to  nameText.text.toString(),
                "description" to nameDescription.text.toString()
            )
            db.collection(collection.toString())
                .add(note)
                .addOnSuccessListener { documentReference ->
                    Log.d(MainActivityFormCollection.TAG,"DocumentSnapshot added with ID: ${documentReference.id}")
                    val intent = Intent(this, MainActivity::class.java).apply {}
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(MainActivityFormCollection.TAG, "Error adding document", e)
                }
        }
    }
}