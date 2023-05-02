package com.iremkryt.newsappandroid.models

import com.iremkryt.newsappandroid.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)