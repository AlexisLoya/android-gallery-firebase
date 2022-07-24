package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
import com.utez.edu.mx.activitysix.databinding.ActivityMainNoteDetailsBinding

class MainActivityNoteDetails : Activity() {
    companion object{
        const val TITLE = "title"
        const val DESCRIPTION = "description"
    }
    private lateinit var binding: ActivityMainNoteDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data
        val bundle = intent.extras
        val title = bundle?.getString("title")
        val description = bundle?.getString("description")

        binding.title.text = title
        if(description.toString().startsWith("https")){

        }else{
            binding.description.text = description

        }
    }
}