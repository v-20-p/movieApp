package com.example.movieapp.presention.navigation

import androidx.annotation.DrawableRes
import com.example.movieapp.R


interface Dest{
    val route:String
}


sealed class TabBarItem(val title: String, @DrawableRes val selectedIcon: Int) {
    data object favTap : TabBarItem(title = "fav", selectedIcon = R.drawable.fav)
    data object homeTap : TabBarItem(title = "home", selectedIcon = R.drawable.home)
    data object profileTap : TabBarItem(title = "profile", selectedIcon = R.drawable.profile)
    data object SearchTap : TabBarItem(title = "search", selectedIcon = R.drawable.baseline_search_24)

}

object Home:Dest{
    override val route: String="home"
}
object onBoarding:Dest{
    override val route: String="onBoarding"
}
object MovieRoute:Dest{
    override val route: String="movieDetails"
}

