package com.example.movieapp.data.di

import android.content.Context
import com.example.movieapp.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
//    @Named("movie_database")
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: MovieDatabase) = database.movieDao()

}