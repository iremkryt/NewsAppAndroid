package com.iremkryt.newsappandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iremkryt.newsappandroid.models.NewsResponse
import com.iremkryt.newsappandroid.repository.NewsRepository
import com.iremkryt.newsappandroid.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.iremkryt.newsappandroid.api.RetrofitInstance
import com.iremkryt.newsappandroid.models.Article
import kotlinx.coroutines.Job

class NewsViewModel( application:Application ): AndroidViewModel(application){

    private var articles: LiveData<List<Article>>? = null

    private val newsRepo:NewsRepository = NewsRepository(getApplication())

    var breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()



    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null


    var searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var searchNewsPage = 1
    val searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("tr")
    }

    fun getBreakingNews(countryCode: String): Job {

        val job =  viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            val response = RetrofitInstance.api.getBreakingNews(countryCode,breakingNewsPage)

            breakingNews.postValue(handleBreakingNewsResponse(response))
        }

        return job
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if(response.isSuccessful){
            breakingNewsPage++
            response.body()?.let{ result ->
                if(breakingNewsResponse == null){
                    breakingNewsResponse = result
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = result.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse?:result)
            }
        }

        return Resource.Error(response.message())

    }


    fun getSearchNews(searchQuery: String): Job {

        val job =  viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            val response = newsRepo.searchForNews(searchQuery,searchNewsPage)

            searchNews.postValue(handleSearchNewsResponse(response))
        }

        return job
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let{ result ->
                return Resource.Success(result)
            }
        }

        return Resource.Error(response.message())

    }

    fun upsert(article: Article) = viewModelScope.launch {
        newsRepo.upsert(article)
    }

    fun getSavedNews() = newsRepo.getAllArticle()
}