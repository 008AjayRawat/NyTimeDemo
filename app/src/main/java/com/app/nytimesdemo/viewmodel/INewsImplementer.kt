package com.app.nytimesdemo.viewmodel

import com.app.nytimesdemo.data.models.NewsResult

interface INewsImplementer {

    fun onStartLoading()

    fun onStopLoading()

    fun onError(error: String)

    fun onNewsFeedResponse(newsResults: MutableList<NewsResult>?)

}