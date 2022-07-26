package com.utez.edu.mx.activitysix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.adapter.Menu
import com.utez.edu.mx.activitysix.adapter.MenuAdapter
import com.utez.edu.mx.activitysix.databinding.ActivityMainBinding

const val EXTRA_MESSAGE = "com.utez.edu.mx.activitysix.MESSAGE"

class MainActivity : AppCompatActivity() {
    companion object{
        const val  TAG = "firebase"
        const val  COLLECTION = "collection"
        const val  IMAGE = "imagen"
        const val  OPTIONS = "options"

    }
    private lateinit var binding: ActivityMainBinding

    private lateinit var db: FirebaseFirestore
    lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Variables
        db = Firebase.firestore
        val listOption = ArrayList<Menu>()

        // Add Collection
        binding.addCollection.setOnClickListener {
            val intent = Intent(this, MainActivityFormCollection::class.java).apply {}
            startActivity(intent)

        }
        // Refresh data
        binding.refresh.setOnClickListener{
            val docRef = db.collection(OPTIONS)
            var refreshData= ArrayList<Menu>()
            docRef.get()
                .addOnSuccessListener { documents ->
                    if (documents != null) {
                        for (doc in documents){
                            refreshData.add(
                                Menu(
                                    doc.id, doc.data["title"].toString(),
                                    doc.data["description"].toString(),
                                    doc.data["collection"].toString(),
                                ))
                        }


                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
            adapter = MenuAdapter(refreshData, this@MainActivity)
            binding.recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.recycleView.adapter = adapter
        }
        // Fill adapter
        val docRef = db.collection(OPTIONS)
        var refreshData:ArrayList<Menu> = arrayListOf()
        docRef.get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (doc in documents){
                        refreshData.add(
                            Menu(
                                doc.id, doc.data["title"].toString(),
                                doc.data["description"].toString(),
                                doc.data["collection"].toString(),
                            ))
                    }


                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        Log.d(TAG, "Trae")
        adapter = MenuAdapter(refreshData, this)
        binding.recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recycleView.adapter = adapter

    }


}