package com.app.nytimesdemo.data.network.repository

import com.app.nytimesdemo.data.models.MostPopularNewsResult
import io.reactivex.Observable

interface IRepository {

    fun fetchNews(period: Int): Observable<MostPopularNewsResult>

}