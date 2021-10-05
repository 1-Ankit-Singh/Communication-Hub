package com.project.communicationhub.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitAPI {

    @GET
    fun getAllNews(@Url URL: String): Call<NewsModel>

    @GET
    fun getNewsByCategory(@Url URL: String): Call<NewsModel>

}