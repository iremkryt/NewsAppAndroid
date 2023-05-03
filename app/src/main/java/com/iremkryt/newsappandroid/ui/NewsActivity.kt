package com.iremkryt.newsappandroid.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iremkryt.newsappandroid.R
import com.iremkryt.newsappandroid.adapters.NewsAdapter
import com.iremkryt.newsappandroid.databinding.FragmentBreakingNewsBinding


class NewsActivity : AppCompatActivity() {

    val newsViewModel by viewModels<NewsViewModel>()
    //private var navController = findNavController(R.id.newsNavHostFragment)
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: FragmentBreakingNewsBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        val view = binding.root

        val recyclerView = findViewById<RecyclerView>(R.id.rvBreakingNews)
        recyclerView.layoutManager = GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false)

        val adapter = NewsAdapter()
        recyclerView.adapter= adapter

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