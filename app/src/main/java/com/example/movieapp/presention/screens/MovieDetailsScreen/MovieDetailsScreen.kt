package com.example.movieapp.presention.screens.MovieDetailsScreen

import Constrant.MOVIE_IMAGE_BASE_URL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
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
                viewModels.getCredits(id)

        }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)) {



        val result = viewModels.movieDetailsState.value
        val result2 = viewModels.creditsState.value

        when (result ){

            is UIState.Success -> {
                val data=result.data
                Box{
                    GlideImage(
                        model = MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + data?.backdropPath,
                        contentDescription = data?.title,
                        Modifier.blur(50.dp)

                    )
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(10.dp),Arrangement.Bottom) {
                        Row(
                            Modifier
                                .fillMaxWidth(1f)
                                .background(Color.White, RoundedCornerShape(10.dp)),Arrangement.spacedBy(20.dp),Alignment.CenterVertically){
                            GlideImage(
                                model = MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + data?.backdropPath,
                                contentDescription = data?.title,
                                Modifier
                                    .size(150.dp, 200.dp)
                                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)),
                                contentScale = ContentScale.Crop

                            )

                            Column{
                                Text(
                                    text = data?.title.orEmpty(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(text = "${data?.voteAverage}")
                                Text(text = "${data?.tagline}")

                            }

                        }
                    }
                }
                Row(Modifier.fillMaxWidth(),Arrangement.SpaceAround,Alignment.CenterVertically){
                    for (i in data?.genres!!) {
                        Text(text = i.name,
                            Modifier
                                .padding(2.dp)
                                .background(
                                    Color.DarkGray,
                                    RoundedCornerShape(.1f)
                                ))
                    }
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

        when (result2){
            is UIState.Success -> {
                val data=result2.data
                LazyRow{
                    items(data?.crew?.size ?:5){
                        val crew = data?.crew?.get(it)

                        Column(Modifier,Arrangement.Center,Alignment.CenterHorizontally){


                            Box(modifier = Modifier
                                .size(120.dp)
                                ){
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w1280" + crew?.profilePath,
                                    contentDescription = "crew",
                                    modifier = Modifier
                                        .padding(0.dp)
                                        .size(400.dp, 300.dp)
                                        .fillMaxWidth()
                                        .clip(
                                            RoundedCornerShape(150.dp)
                                        ),
                                    contentScale= ContentScale.Crop


                                )
                            }
                            Text(text = " ${crew?.name}")
                        }
//                        Text(text = "${MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + crew?.profilePath}")


                    }

//                    GlideImage(
//                        model = MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + data?.,
//                        contentDescription = data?.title
//                    )
//                    Text(text = data?.title.orEmpty())
//                    Text(text = "${data?.id}")
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