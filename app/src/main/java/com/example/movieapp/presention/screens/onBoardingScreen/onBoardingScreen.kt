package com.example.movieapp.presention.screens.onBoardingScreen


import MyMultiWaveScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movieapp.R
import com.example.movieapp.presention.navigation.Home
import com.example.movieapp.presention.navigation.popUpToTop


import com.example.movieapp.presention.preferencesdatastore.TaskViewModel
import kotlinx.coroutines.delay


@Composable
fun onBoardingScreen(navController: NavHostController, onBoardingViewModel: TaskViewModel) {

    val onBoardingCompletete by onBoardingViewModel.onBoardingCompleted.collectAsState()
//    val scope = rememberCoroutineScope()
if (onBoardingCompletete){
    navController.navigate(Home.route){
        popUpToTop(navController)
    }
} else{
    LaunchedEffect(Unit) {
        delay(1000)

    }
    a(navController,onBoardingViewModel)

}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun a(navController: NavHostController, onBoardingViewModel: TaskViewModel){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            val pagerState = rememberPagerState(pageCount = {
                4
            })

            Row(
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)

                            .background(color, RoundedCornerShape(50.dp))
                            .size(16.dp)
                    )
                }
            }


            HorizontalPager(
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.6f), contentAlignment = Alignment.Center
                        ) {
                            Column(
                                Modifier.fillMaxWidth(.9f),
                                Arrangement.Center,
                                Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.movie_night_amico),
                                    contentDescription = null,
                                    modifier = Modifier.size(300.dp)
                                )
                                Text(
                                    text = "Welcome to CineSpectra",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Explore the latest movies, reserve the perfect seats, and experience the cinema in a whole new way.")
                            }
                        }
                    }

                    1 -> {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.6f), contentAlignment = Alignment.Center
                        ) {
                            Column(
                                Modifier.fillMaxWidth(.9f),
                                Arrangement.Center,
                                Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.home_cinema_amico__1___1_),
                                    contentDescription = null,
                                    modifier = Modifier.size(300.dp)
                                )
                                Text(
                                    text = "Quick and Easy Booking",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Reserve your favorite seat in just a few steps. No waiting, no hassle!")
                            }
                        }
                    }

                    2 -> {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.6f), contentAlignment = Alignment.Center
                        ) {
                            Column(
                                Modifier.fillMaxWidth(.9f),
                                Arrangement.Center,
                                Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.horror_movie_amico1212),
                                    contentDescription = null,
                                    modifier = Modifier.size(300.dp)
                                )
                                Text(
                                    text = "Tailored Just for You",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Personalize your experience! With movie recommendations and exclusive offers, enjoy the cinema your way.")
                            }
                        }
                    }

                    3 -> {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.6f), contentAlignment = Alignment.Center
                        ) {
                            Column(
                                Modifier.fillMaxWidth(.9f),
                                Arrangement.Center,
                                Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.videotape_amico123),
                                    contentDescription = null,
                                    modifier = Modifier.size(300.dp)
                                )
                                Text(
                                    text = "Do you have account?",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Personalize your experience! With movie recommendations and exclusive offers, enjoy the cinema your way.")
                            }
                        }
                    }
                }
            }



            MyMultiWaveScreen(pagerState.currentPage, onClick = {
                navController.navigate(Home.route)

                onBoardingViewModel.saveBoardingState(true)
            })
        }
    }
}