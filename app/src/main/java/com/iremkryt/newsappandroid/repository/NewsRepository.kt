package com.iremkryt.newsappandroid.repository

import com.iremkryt.newsappandroid.api.RetrofitInstance
import com.iremkryt.newsappandroid.db.ArticleDatabase
import android.content.Context
import androidx.lifecycle.LiveData
import com.iremkryt.newsappandroid.models.Article
import com.iremkryt.newsappandroid.models.NewsResponse
import retrofit2.Response


class NewsRepository(var context: Context) {
    val db: ArticleDatabase = ArticleDatabase.getInstance(context)
    val articleDao = db.getArticleDao()


    suspend fun getBreakingNews(country: String, page: Int): Response<NewsResponse> {
        return  RetrofitInstance.api.getBreakingNews(country,page)
    }

    suspend fun searchForNews(searchQuery: String, page: Int): Response<NewsResponse>{
        return RetrofitInstance.api.searchForNews(searchQuery, page)
    }

    suspend fun upsert(article: Article ): Long{
        return articleDao.upsert(article)
    }

    fun getAllArticle():LiveData<List<Article>>{
        return articleDao.getAllArticles()
    }

}