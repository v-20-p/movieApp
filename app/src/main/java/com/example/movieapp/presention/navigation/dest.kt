package com.example.movieapp.presention.navigation

interface Dest{
    val route:String
}
object Home:Dest{
    override val route: String="home"
}
object onBoarding:Dest{
    override val route: String="onBoarding"
}
