package com.utez.edu.mx.activitysix

import android.app.ProgressDialog
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

    lateinit var adapter: MenuAdapter

    override fun onResume() {
        super.onResume()
        fillAdapter()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        // Fill adapter
        fillAdapter()




        // Add Collection
        binding.addCollection.setOnClickListener {
            val intent = Intent(this, MainActivityFormCollection::class.java).apply {}
            startActivity(intent)

        }
        // Refresh Adapter
        binding.refresh.setOnClickListener{
            fillAdapter()
        }


    }

private fun fillAdapter(){
    // Firebase
    var mProcessDialog = ProgressDialog(this)
    mProcessDialog.setMessage("Cargando...")
    mProcessDialog.show()
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
                binding.recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.recycleView.adapter = adapter
                mProcessDialog.dismiss()
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }
}