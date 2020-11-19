package com.app.nytimesdemo.data.network.repository

import com.app.nytimesdemo.BuildConfig
import com.app.nytimesdemo.data.models.MostPopularNewsResult
import com.app.nytimesdemo.data.network.RetrofitClient
import com.app.nytimesdemo.data.network.api.NewYorkTimesAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NewsRepository : IRepository {

    private val service by lazy { RetrofitClient.getService(NewYorkTimesAPI::class.java, BuildConfig.BASE_URL) }

    override fun fetchNews(period: Int): Observable<MostPopularNewsResult> {
        return service.fetchMostPopularNews(period, BuildConfig.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}