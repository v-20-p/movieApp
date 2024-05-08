package com.example.movieapp.data.remote

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.CreditsRepo
import com.example.movieapp.model.MovieDetailsRepo
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UserAccount
import com.example.movieapp.model.UserTokenResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("3/movie/{movie_id}")
    suspend fun getDetailsMovie(
        @Path("movie_id")
        id:Int? ,
        @Query("api_key") apiKey: String = BuildConfig.TMOB_API_KEY,
        @Query("language") language: String = "en-US"
    ):Response<MovieDetailsRepo>

    @GET("3/movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
        @Path("movie_id")
        id:Int? ,
        @Query("api_key") apiKey: String = BuildConfig.TMOB_API_KEY,
        @Query("language") language: String = "en-US"
    ):Response<CreditsRepo>

    @GET("/3/search/multi")
    suspend fun getSearch(
        @Query("api_key") apiKey: String = BuildConfig.TMOB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("include_adult") includeAdult:Boolean=false,
        @Query("page") page: Int = 1
    ):Response<SearchResponse>

    @GET("3/authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String = BuildConfig.TMOB_API_KEY,

    ):Response<UserTokenResponse>
    @POST("3/authentication/session/new")
    suspend fun getSession(
        @Query("api_key") apiKey: String = BuildConfig.TMOB_API_KEY,
        @Query("request_token") requestToken: String
    ):Response<UserTokenResponse>

    @GET("3/account")
    suspend fun getAccount(
        @Query("api_key") apiKey: String = BuildConfig.TMOB_API_KEY,
        @Query("session_id") sessionId: String
    ):Response<UserAccount>




}