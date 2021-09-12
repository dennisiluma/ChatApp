package com.dennisiluma.chatapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dennisiluma.chatapp.R

class StartActivity : AppCompatActivity() {
//    private lateinit var _binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.hide() //hides the action bar
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}