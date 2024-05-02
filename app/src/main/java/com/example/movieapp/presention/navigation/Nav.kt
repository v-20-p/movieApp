package com.example.movieapp.presention.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.presention.screens.HomeScreen
import com.example.movieapp.presention.screens.Popular.PopularMoviesViewModel
//import com.example.movieapp.presention.screens.Popular.TaskViewModel
import com.example.movieapp.presention.screens.onBoardingScreen.onBoardingScreen

import androidx.navigation.NavHostController
import com.example.movieapp.presention.screens.Popular.TaskViewModel

//taskViewModel: TaskViewModel = viewModel()
@Composable

fun Nav(navController: NavHostController){



    val onBoardingViewModel:TaskViewModel = hiltViewModel()
    NavHost(navController = navController,startDestination = onBoardingViewModel.StartDestination){
        composable(Home.route) {
            val parentViewModel = hiltViewModel<PopularMoviesViewModel>()
            HomeScreen(parentViewModel,navController = navController)
        }
        composable(onBoarding.route) {

            onBoardingScreen(onBoardingViewModel=onBoardingViewModel,navController = navController)
        }
    }

}

