package com.example.movieapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.Modifier

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.movieapp.presention.screens.Popular.PopularMoviesViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.presention.navigation.Nav

import com.example.movieapp.presention.screens.HomeScreen

import com.example.movieapp.presention.screens.onBoardingScreen.onBoardingScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigateor = rememberNavController()
            MovieAppTheme {

                  Nav(navigateor)






                }
        }
    }
}




