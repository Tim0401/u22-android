package com.akeno0810.u22.api

import com.akeno0810.u22.AppState
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

open class ApiUserClientManager {
    companion object {
        private const val ENDPOINT = "http://35.200.62.62:1992/api/v1/"

        val apiUserClient: ApiUserClient
            get() = Retrofit.Builder()
                .client(getClient())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(ApiUserClient::class.java)

        private fun getClient(): OkHttpClient {
            var httpClient =  OkHttpClient
                .Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            httpClient.addInterceptor { chain ->
                val original = chain.request()

                //header設定
                val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())

                chain.proceed(requestBuilder.build())
            }
            return httpClient.build()
        }
    }
}