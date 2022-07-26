package com.utez.edu.mx.activitysix.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.utez.edu.mx.activitysix.MainActivity
import com.utez.edu.mx.activitysix.MainActivityFormNote
import com.utez.edu.mx.activitysix.R
import com.utez.edu.mx.activitysix.databinding.CollectionAdapterBinding

class MenuAdapter (private val menuList: MutableList<Menu>, private val context: Context): RecyclerView.Adapter<MenuAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.Holder {
        val binding = DataBindingUtil
            .inflate<CollectionAdapterBinding>(
                LayoutInflater.from(context),
                R.layout.collection_adapter, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.Holder, position: Int) {
        val menu:Menu = menuList[position]
        holder.binding.txtTitle.text =  menu.title
        Picasso.get()
            .load(menu.img)
            .resize(50, 50)
            .centerCrop()
            .into( holder.binding.imgIcon)
        holder.binding.root.setOnClickListener {
            context.apply {
                val intent = Intent(context, MainActivityFormNote::class.java)
                intent.putExtra(MainActivity.COLLECTION, menu.collection)
                intent.putExtra(MainActivity.IMAGE, menu.img)
                startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class Holder(val binding: CollectionAdapterBinding) : RecyclerView.ViewHolder(binding.root){}

}