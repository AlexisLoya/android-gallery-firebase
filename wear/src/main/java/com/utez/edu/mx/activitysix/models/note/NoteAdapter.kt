package com.utez.edu.mx.activitysix.models.note

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.utez.edu.mx.activitysix.*
import com.utez.edu.mx.activitysix.databinding.ActivityPreviewRecycleBinding

class NoteAdapter(private val listNote: MutableList<Note>, private val context: Context): RecyclerView.Adapter<NoteAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.Holder {
        val binding = DataBindingUtil
            .inflate<ActivityPreviewRecycleBinding>(
                LayoutInflater.from(context),
                R.layout.activity_preview_recycle, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.Holder, position: Int) {
        val note = listNote[position]
        holder.binding.titleNote.text =  note.title
        holder.binding.root.setOnClickListener {
            //Log.w("click", "en: ${note.title}")
            context.apply {
                var intent = Intent(context, MainActivityItemDetails::class.java)
                intent.putExtra(MainActivityItemDetails.TITLE, note.title)
                intent.putExtra(MainActivityItemDetails.DESCRIPTION, note.description)
                intent.putExtra(MainActivityItemDetails.IMAGE, note.img)
                startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    class Holder(val binding: ActivityPreviewRecycleBinding) : RecyclerView.ViewHolder(binding.root){}

}