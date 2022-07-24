package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utez.edu.mx.activitysix.databinding.ActivityMainNoteBinding
import com.utez.edu.mx.activitysix.models.note.Note
import com.utez.edu.mx.activitysix.models.note.NoteAdapter

class MainActivityNote : Activity() {
    private lateinit var binding: ActivityMainNoteBinding
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase
        val db = Firebase.firestore
        val listNotes = ArrayList<Note>()
        val docRef = db.collection("notes")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document){
                        val note: Note = Note(
                            doc.id,
                            doc.data["title"].toString(),
                            doc.data["description"].toString(),
                            doc.data["imagen"].toString())
                        listNotes.add(note)
                    }
                    // Fill adapter
                    adapter = NoteAdapter(listNotes, context = this)
                    // Config and set adapter
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