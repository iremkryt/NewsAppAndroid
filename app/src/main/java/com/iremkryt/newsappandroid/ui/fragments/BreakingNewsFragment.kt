package com.iremkryt.newsappandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iremkryt.newsappandroid.R
import com.iremkryt.newsappandroid.adapters.NewsAdapter
import com.iremkryt.newsappandroid.ui.NewsActivity
import com.iremkryt.newsappandroid.ui.NewsViewModel
import com.iremkryt.newsappandroid.util.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    val newsViewModel by viewModels<NewsViewModel>()
//    lateinit var newsAdapter: NewsAdapter
//    lateinit var rvBreakingNews: RecyclerView
//    lateinit var paginationProgressBar: ProgressBar
    var paginationProgressBar: ProgressBar? = null
    var rvBreakingNews: RecyclerView? = null
    var newsAdapter: NewsAdapter? = null
    val TAG = "BreakingNewsFragment"



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var newsViewModel = (activity as NewsActivity).newsViewModel
        setupRecyclerView()

        newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter?.differ?.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An Error Occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar(){
        paginationProgressBar?.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar?.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews?.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}