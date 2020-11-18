package com.app.nytimesdemo.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.app.nytimesdemo.data.enums.Period
import com.app.nytimesdemo.data.network.repository.NewsRepository

class NewsViewModel : BaseViewModel(), LifecycleObserver {

    var listener: INewsImplementer? = null

    fun fetchNews(period: Period) {
        listener?.onStartLoading()
        val subscription = NewsRepository.fetchNews(period.getPeriod())
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