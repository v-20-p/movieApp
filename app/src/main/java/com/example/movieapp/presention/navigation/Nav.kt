package com.example.movieapp.presention.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.presention.screens.Popular.HomeScreen
import com.example.movieapp.presention.screens.Popular.PopularMoviesViewModel
//import com.example.movieapp.presention.preferencesdatastore.TaskViewModel
import com.example.movieapp.presention.screens.onBoardingScreen.onBoardingScreen

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.movieapp.presention.preferencesdatastore.TaskViewModel
import com.example.movieapp.presention.screens.HomeScreen.AccountViewModel
import com.example.movieapp.presention.screens.HomeScreen.FavTapScreen
import com.example.movieapp.presention.screens.HomeScreen.ProfileTapScreen
import com.example.movieapp.presention.screens.MovieDetailsScreen.MovieDetailsScreen
import com.example.movieapp.presention.screens.MovieDetailsScreen.MovieDetailsViewModel
import com.example.movieapp.presention.screens.searchScreen.SearchScreen
import com.example.movieapp.presention.screens.searchScreen.SearchViewModel

//taskViewModel: TaskViewModel = viewModel()
@Composable

fun Nav(navController: NavHostController){
var showBottonBar by rememberSaveable {
    mutableStateOf(false)
}

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    showBottonBar  = when(navBackStackEntry?.destination?.route){
        onBoarding.route ->false
        MovieRoute.route +"/{movieId}"-> false

        else ->true
    }



    val onBoardingViewModel: TaskViewModel = hiltViewModel()
    bottonNav(navController,showBottonBar) {
        NavHost(
            navController = navController,
            startDestination = onBoardingViewModel.StartDestination
        ) {
            composable(onBoarding.route) {

                onBoardingScreen(
                    onBoardingViewModel = onBoardingViewModel,
                    navController = navController
                )
            }
            composable(MovieRoute.route + "/{movieId}", arguments =
            listOf(navArgument("movieId") {
                type = NavType.IntType
            })
            ,

                enterTransition  = {
                    fadeIn() + slideInVertically(animationSpec = tween(1400),
                        initialOffsetY = { fullHeight -> fullHeight })
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(1000)
                    )
                }

            ) {
                val parentViewModel = hiltViewModel<MovieDetailsViewModel>()
                MovieDetailsScreen(parentViewModel, it)
            }
            composable(TabBarItem.homeTap.title) {
                val parentViewModel = hiltViewModel<PopularMoviesViewModel>()
                HomeScreen(parentViewModel.popularMoviesState, navController)
            }
            composable(TabBarItem.favTap.title) {
                val parentViewModel = hiltViewModel<PopularMoviesViewModel>()
                    FavTapScreen(parentViewModel)

            }

            composable(TabBarItem.profileTap.title) {
                val parentViewModel = hiltViewModel<AccountViewModel>()
                    ProfileTapScreen(parentViewModel,navController)

            }
            composable(TabBarItem.SearchTap.title) {
                val parentViewModel = hiltViewModel<SearchViewModel>()
                SearchScreen(parentViewModel,navController)

            }



        }
    }

}



@Composable
fun bottonNav(
    navController: NavHostController,
    showBottonBar: Boolean,
    function: @Composable () -> Unit
){


        val tabBarItems = listOf(TabBarItem.favTap,TabBarItem.homeTap,TabBarItem.SearchTap,TabBarItem.profileTap)

    Scaffold(bottomBar = {
                if (showBottonBar)
                TabView(
                    tabBarItems = tabBarItems,
                    navController = navController
                )


        }){innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    function()
                }


            }

}
fun NavOptionsBuilder.popUpToTop(navController: NavController){
    popUpTo(navController.currentBackStackEntry?.destination?.route ?:return){
        inclusive=true
        saveState=true
    }

}


@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color(0xff374951),tonalElevation=1.dp, modifier = Modifier.fillMaxWidth()

    ) {
        tabBarItems.forEachIndexed { _, tabBarItem ->
            NavigationBarItem(
                selected = currentRoute == tabBarItem.title,
                onClick = {
                    if (currentRoute != tabBarItem.title) {
                        navController.navigate(tabBarItem.title) {
                            popUpToTop(navController)
                            restoreState =true
                            launchSingleTop = true

                        }
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = currentRoute == tabBarItem.title,
                        selectedIcon = tabBarItem.selectedIcon,

                    )
                },
                label = { Text(text = tabBarItem.title, color = Color.White) },
                alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Transparent,
                    unselectedIconColor = Color.Transparent,
                    selectedTextColor = Color(0xFFF6D776),
                    indicatorColor = Color.Transparent
                )



            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    @DrawableRes
    selectedIcon: Int,



    ) {
    Column {
        Icon(painter = painterResource(id = selectedIcon), contentDescription = null ,
            tint =if (isSelected) Color(0xFFF6D776) else Color.White,
            modifier = Modifier.size(30.dp))

        

    }
}