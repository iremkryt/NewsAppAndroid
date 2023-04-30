package com.iremkryt.newsappandroid.repository

import com.iremkryt.newsappandroid.api.RetrofitInstance
import com.iremkryt.newsappandroid.db.ArticleDatabase


class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}