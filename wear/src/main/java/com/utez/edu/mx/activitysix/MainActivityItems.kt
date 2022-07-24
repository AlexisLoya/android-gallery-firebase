package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.databinding.ActivityMainItemsBinding
import com.utez.edu.mx.activitysix.models.note.Note
import com.utez.edu.mx.activitysix.models.note.NoteAdapter

class MainActivityItems : Activity() {
    companion object{
        const val COLLECTION = "collection"
    }
    private lateinit var binding: ActivityMainItemsBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val collection:String? = intent.extras?.getString(COLLECTION)
        val title:String? = intent.extras?.getString(MainActivityItemDetails.TITLE)
        val img:String? = intent.extras?.getString(MainActivityItemDetails.IMAGE)

        val db: FirebaseFirestore = Firebase.firestore
        val list: ArrayList<Note> = ArrayList();
        if (collection != null && img != null) {
            db.collection(collection).get().addOnSuccessListener {
                if (it != null) {
                    for (doc in it){
                        list.add(
                            Note(
                                doc.id, doc.data["title"].toString(),
                                doc.data["description"].toString(),
                                img))
                    }
                    adapter = NoteAdapter(list, this)
                    binding.items.isEdgeItemsCenteringEnabled = true
                    binding.items.layoutManager = WearableLinearLayoutManager(this)
                    binding.items.isCircularScrollingGestureEnabled = true
                    binding.items.adapter = adapter
                    binding.text.text = title
                    }
                }.addOnFailureListener { e ->
                    Log.d("TAG", "get failed with ", e)
            }
        }
    }
}