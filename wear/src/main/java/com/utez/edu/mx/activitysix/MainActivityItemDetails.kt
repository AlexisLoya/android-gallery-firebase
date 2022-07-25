package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.utez.edu.mx.activitysix.MainActivityItemDetails.Companion.TITLE
import com.utez.edu.mx.activitysix.databinding.ActivityMainItemDetailsBinding

class MainActivityItemDetails : Activity() {
    companion object{
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
    }
    private lateinit var binding: ActivityMainItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.text = intent.extras?.getString(TITLE)
        binding.description.text = intent.extras?.getString(DESCRIPTION)
        Picasso.get()
            .load(intent.extras?.getString(IMAGE))
            .resize(50, 50)
            .centerCrop()
            .into(binding.iconNote)
    }
}