package com.iremkryt.newsappandroid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iremkryt.newsappandroid.R
import com.iremkryt.newsappandroid.ui.NewsActivity
import com.iremkryt.newsappandroid.ui.NewsViewModel

class ArticleNewsFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel : NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel
    }
}