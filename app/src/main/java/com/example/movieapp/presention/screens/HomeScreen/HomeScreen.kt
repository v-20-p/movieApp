package com.example.movieapp.presention.screens.HomeScreen

import Constrant.MOVIE_IMAGE_BASE_URL
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.R
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.model.UIState
import com.example.movieapp.presention.screens.Popular.PopularMoviesViewModel
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(viewModels: PopularMoviesViewModel = viewModel()) {



    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.tertiary)) {

        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()

                .scale(1.5f)

        )



        val viewModel =viewModels
        when (val result = viewModel.popularMoviesState.value) {
            is UIState.Success -> {


                LazyColumn(
                    modifier = Modifier


                ) {


                    items(result.data?.results.orEmpty()) {


                        GlideImage(
                            model = MOVIE_IMAGE_BASE_URL + BackdropSize.w300 + it.backdropPath,
                            contentDescription = " ",
                            modifier = Modifier.padding(10.dp),
                        )


                        Text(
                            text = it.title.orEmpty(),
                            Modifier.padding(10.dp)
                        )

                    }

                }
            }

            is UIState.Empty -> Text(
                text = "Empty",
                Modifier.padding(10.dp)
            )

            is UIState.Error -> Text(
                text = "Error",
                Modifier.padding(10.dp)
            )

            is UIState.Loading -> Text(
                text = "Loading",
                Modifier.padding(10.dp)
            )
        }

    }

//        val tabBarItems = listOf(homeTap,favTap,profileTap)
//
//            Scaffold(bottomBar = {
//                TabView(
//                    tabBarItems = tabBarItems,
//                    navController = navController
//                )
//
//
//        }){
//                it.calculateTopPadding()
//
//            }


}




