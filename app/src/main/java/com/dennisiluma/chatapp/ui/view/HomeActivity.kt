package com.dennisiluma.chatapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dennisiluma.chatapp.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
