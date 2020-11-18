package com.app.nytimesdemo.data.network.api

import com.app.nytimesdemo.data.models.MostPopularNewsResult
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewYorkTimesAPI {
    @GET("/svc/mostpopular/v2/viewed/{period}.json")
    fun fetchMostPopularNews(@Path("period") period: Int, @Query("api-key") apiKey: String?): Observable<MostPopularNewsResult>
}