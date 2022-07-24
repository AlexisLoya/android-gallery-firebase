package com.utez.edu.mx.activitysix

import android.app.Activity
import android.os.Bundle
import com.utez.edu.mx.activitysix.databinding.ActivityMainGlobalOptionsBinding

class MainActivityGlobalOptions : Activity() {

    private lateinit var binding: ActivityMainGlobalOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainGlobalOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}