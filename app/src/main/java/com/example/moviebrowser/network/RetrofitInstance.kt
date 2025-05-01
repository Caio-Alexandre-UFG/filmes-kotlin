package com.example.moviebrowser.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNGQzM2ZiY2E4MzIzYjA5MWE0ODZmZDNjNmRiODlmMiIsIm5iZiI6MTc0MDAxNjA1OS4wODQsInN1YiI6IjY3YjY4OWJiOTk5OTg2MjNjMTE0N2M4MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LJJJjFD33pVA4v0A0KSwhG28f0UI8OkZC9Z8pqiGS5k"

    private val client = okhttp3.OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", BEARER_TOKEN)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MovieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }
}