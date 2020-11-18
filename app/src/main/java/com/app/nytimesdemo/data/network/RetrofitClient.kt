package com.app.nytimesdemo.data.network

import okhttp3.CertificatePinner
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        val builder = OkHttpClient.Builder()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(serviceClass)
    }
}
