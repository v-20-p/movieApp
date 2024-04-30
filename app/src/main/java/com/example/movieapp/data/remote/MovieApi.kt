package com.example.movieapp.data.remote

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.SearchResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale.LanguageRange

interface  MovieApi{
    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key")
        apiKey:String=BuildConfig.TMOB_API_KEY,
        @Query("language")
        language:String="en-US",
        @Query("page")
        page :Int= 1,

    ): Response<SearchResponse>
}