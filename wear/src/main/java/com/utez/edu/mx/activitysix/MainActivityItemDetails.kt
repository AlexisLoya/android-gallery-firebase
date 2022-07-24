package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
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
    }
}