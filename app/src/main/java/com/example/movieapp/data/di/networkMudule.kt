package com.example.movieapp.data.di




import Constrant.MOVIE_BASE_URL
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.movieapp.data.remote.MovieApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context:Context
    ): OkHttpClient {
        val httpclient = OkHttpClient.Builder()
            .connectTimeout(120L, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(context))
            .readTimeout(120L, TimeUnit.SECONDS)
            .writeTimeout(120L, TimeUnit.SECONDS)
        return httpclient.build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(
        retrofit: Retrofit
    ): MovieApi = retrofit.create(
        MovieApi::class.java
    )
}
