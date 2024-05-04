package com.example.movieapp.presention.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.presention.screens.HomeScreen.HomeScreen
import com.example.movieapp.presention.screens.Popular.PopularMoviesViewModel
//import com.example.movieapp.presention.preferencesdatastore.TaskViewModel
import com.example.movieapp.presention.screens.onBoardingScreen.onBoardingScreen

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieapp.presention.preferencesdatastore.TaskViewModel
import com.example.movieapp.presention.screens.HomeScreen.FavTapScreen
import com.example.movieapp.presention.screens.HomeScreen.ProfileTapScreen
import com.example.movieapp.presention.screens.MovieDetailsScreen.MovieDetailsScreen
import com.example.movieapp.presention.screens.MovieDetailsScreen.MovieDetailsViewModel

//taskViewModel: TaskViewModel = viewModel()
@Composable

fun Nav(navController: NavHostController){



    val onBoardingViewModel: TaskViewModel = hiltViewModel()
    NavHost(navController = navController,startDestination = onBoardingViewModel.StartDestination){
        composable(onBoarding.route) {

            onBoardingScreen(onBoardingViewModel=onBoardingViewModel,navController = navController)
        }
        composable(MovieRoute.route){
            val parentViewModel = hiltViewModel<MovieDetailsViewModel>()
            MovieDetailsScreen(parentViewModel,1041613)
        }


        composable(TabBarItem.homeTap.title) {
            val parentViewModel = hiltViewModel<PopularMoviesViewModel>()
            bottonNav(navController){

            HomeScreen(parentViewModel,navController)
            }
        }
        composable(TabBarItem.favTap.title){
            bottonNav(navController){

            FavTapScreen()
            }

        }

        composable(TabBarItem.profileTap.title){
            bottonNav(navController){
            ProfileTapScreen()

            }

        }

    }

}


@Composable
fun bottonNav(navController:NavHostController,function: @Composable () -> Unit){

        val tabBarItems = listOf(TabBarItem.favTap,TabBarItem.homeTap,TabBarItem.profileTap)


            Scaffold(bottomBar = {
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
    }

}


@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color(0xff374951),tonalElevation=1.dp) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = currentRoute == tabBarItem.title,
                onClick = {
                    if (currentRoute != tabBarItem.title) {
                        navController.navigate(tabBarItem.title) {
                            popUpToTop(navController)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = currentRoute == tabBarItem.title,
                        selectedIcon = tabBarItem.selectedIcon,
                        title = tabBarItem.title,
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

    title: String,

    ) {
    Column {
        Icon(painter = painterResource(id = selectedIcon), contentDescription = null ,
            tint =if (isSelected) Color(0xFFF6D776) else Color.White,
            modifier = Modifier.size(30.dp))

        

    }
}