package com.utez.edu.mx.activitysix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.utez.edu.mx.activitysix.databinding.ActivityMainBinding
import com.utez.edu.mx.activitysix.databinding.ActivityMainFormCollectionBinding
import java.util.*

class MainActivityFormCollection : AppCompatActivity() {
    companion object{
        const val  TAG = "firebase"
    }
    private lateinit var binding: ActivityMainFormCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_form_collection)
        binding = ActivityMainFormCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameText:TextView = binding.editName
        val submitBtn:Button = binding.submitBtn
        val selectBtn:Button = binding.selectBtn
        val db = Firebase.firestore
        val collection = hashMapOf(
            "collection" to nameText.text.toString().lowercase(Locale.ROOT),
            "title" to nameText.text.toString(),
            "description" to 1815
        )

        submitBtn.setOnClickListener {
            // Create a new user with a first and last name
            db.collection("options")
                .add(collection)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        MainActivityFormCollection.TAG,
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(MainActivityFormCollection.TAG, "Error adding document", e)
                }
        }
    }
}
