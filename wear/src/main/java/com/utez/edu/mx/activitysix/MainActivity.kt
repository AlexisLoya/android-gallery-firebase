package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.databinding.ActivityMainBinding
import com.utez.edu.mx.activitysix.models.menu.Menu
import com.utez.edu.mx.activitysix.models.menu.MenuAdapter
import com.utez.edu.mx.activitysix.models.note.Note
import com.utez.edu.mx.activitysix.models.note.NoteAdapter

class MainActivity : Activity() {
    companion object{
        const val  TAG = "firebase"
    }
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: MenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Firebase
        val db = Firebase.firestore
        val listOption = ArrayList<Menu>()
        val docRef = db.collection("options")
        docRef.get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (doc in documents){
                        listOption.add(
                            Menu(
                                doc.id, doc.data["title"].toString(),
                                doc.data["description"].toString(),
                                doc.data["collection"].toString(),
                            ))
                    }
                    adapter = MenuAdapter(listOption, this)
                    binding.recycleView.isEdgeItemsCenteringEnabled = true
                    binding.recycleView.layoutManager = WearableLinearLayoutManager(this)
                    binding.recycleView.isCircularScrollingGestureEnabled = true
                    binding.recycleView.adapter = adapter
                } else {
                    Log.d(MainActivity.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(MainActivity.TAG, "get failed with ", exception)
            }
    }
}