package com.iremkryt.newsappandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iremkryt.newsappandroid.adapters.NewsAdapter
import com.iremkryt.newsappandroid.databinding.FragmentBreakingNewsBinding
import com.iremkryt.newsappandroid.ui.NewsActivity
import com.iremkryt.newsappandroid.ui.NewsViewModel
import com.iremkryt.newsappandroid.util.Resource

class BreakingNewsFragment : Fragment() {
    val newsViewModel by viewModels<NewsViewModel>()
    private var paginationProgressBar: ProgressBar? = null
    private var newsAdapter = NewsAdapter()
    private val TAG = "BreakingNewsFragment"
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding : FragmentBreakingNewsBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsViewModel = (activity as NewsActivity).newsViewModel
        setupRecyclerView()

        newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                        println("response ${newsResponse.articles}")
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An Error Occurred: $message")
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
        binding.rvBreakingNews.adapter = newsAdapter
    }
}