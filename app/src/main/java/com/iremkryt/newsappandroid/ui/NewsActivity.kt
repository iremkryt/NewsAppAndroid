package com.iremkryt.newsappandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iremkryt.newsappandroid.R


class NewsActivity : AppCompatActivity() {

    val newsViewModel by viewModels<NewsViewModel>()
    //private var navController = findNavController(R.id.newsNavHostFragment)

    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val navController = findNavController(R.id.newsNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setupWithNavController(supportFragmentManager.findFragmentById(R.id.newsNavHostFragment)!!.findNavController())

    }

    override fun onSupportNavigateUp(): Boolean {
        return true
        //return NavigationUI.navigateUp(navController,null)
    }
}