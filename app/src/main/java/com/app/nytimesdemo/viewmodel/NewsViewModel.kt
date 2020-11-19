package com.app.nytimesdemo.viewmodel

import com.app.nytimesdemo.data.enums.Period
import com.app.nytimesdemo.data.network.repository.IRepository

class NewsViewModel : BaseViewModel() {

    var listener: INewsImplementer? = null
    var newsRepository: IRepository? = null

    fun provideRepository(repo: IRepository) {
        newsRepository = repo;
    }

    fun fetchNews(period: Period) {
        newsRepository?.let {
            listener?.onStartLoading()
            val subscription = it.fetchNews(period.getPeriod())
                .subscribe({ response ->
                    listener?.onStopLoading()
                    listener?.onNewsFeedResponse(response.results)
                }, { error ->
                    listener?.onStopLoading()
                    error.message?.let { listener?.onError(it) }
                })

            add(subscription)
        }

    }
}