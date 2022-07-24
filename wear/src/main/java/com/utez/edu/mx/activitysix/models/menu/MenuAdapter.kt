package com.utez.edu.mx.activitysix.models.menu

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.utez.edu.mx.activitysix.*
import com.utez.edu.mx.activitysix.databinding.ActivityPreviewRecycleBinding

class MenuAdapter (private val listNote: MutableList<Menu>,private val context: Context): RecyclerView.Adapter<MenuAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.Holder {
        val binding = DataBindingUtil
            .inflate<ActivityPreviewRecycleBinding>(
                LayoutInflater.from(context),
                R.layout.activity_preview_recycle, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.Holder, position: Int) {
        val menu:Menu = listNote[position]
        holder.binding.titleNote.text =  menu.title
        holder.binding.root.setOnClickListener {
            Log.w("click", "en: ${menu.img}")
            context.apply {
                val intent = Intent(context, MainActivityItems::class.java)
                intent.putExtra(MainActivityItems.COLLECTION, menu.collection)
                intent.putExtra(MainActivityItemDetails.TITLE, menu.title)
                intent.putExtra(MainActivityItemDetails.IMAGE, menu.img)
                startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    class Holder(val binding: ActivityPreviewRecycleBinding) : RecyclerView.ViewHolder(binding.root){}

}