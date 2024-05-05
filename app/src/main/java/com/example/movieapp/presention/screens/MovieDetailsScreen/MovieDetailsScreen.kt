package com.example.movieapp.presention.screens.MovieDetailsScreen

import Constrant.MOVIE_IMAGE_BASE_URL
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.model.UIState


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsScreen(viewModels: MovieDetailsViewModel = viewModel(), receivedArgument: NavBackStackEntry){
    val id = receivedArgument.arguments?.getInt("movieId")
        LaunchedEffect(key1 = Unit) {

                viewModels.getMovieDetails(id)

        }
    Column {


        val viewModel = viewModels
        when (val result = viewModel.movieDetailsState.value){
            is UIState.Success -> {
                val data=result.data

                Column{
                    GlideImage(
                        model = MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + data?.backdropPath,
                        contentDescription = data?.title
                    )
                    Text(text = data?.title.orEmpty())
                }

            }
            is UIState.Empty -> Text(
                text = "Empty $id",
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


}