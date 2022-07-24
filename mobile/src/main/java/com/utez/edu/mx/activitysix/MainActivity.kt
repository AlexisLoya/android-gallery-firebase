package com.utez.edu.mx.activitysix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

const val EXTRA_MESSAGE = "com.utez.edu.mx.activitysix.MESSAGE"

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val button:Button = findViewById(R.id.addCollection)
    }

    fun addCollection(view: View) {
        val editText = "Hello"
        val message = editText
        val intent = Intent(this, MainActivityFormCollection::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}