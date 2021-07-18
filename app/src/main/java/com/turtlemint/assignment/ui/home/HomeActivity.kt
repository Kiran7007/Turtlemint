package com.turtlemint.assignment.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.turtlemint.assignment.R

/**
 * This is the launcher activity of the application
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}