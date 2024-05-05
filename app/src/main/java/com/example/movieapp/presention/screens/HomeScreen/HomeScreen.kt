package com.example.movieapp.presention.screens.HomeScreen

import Constrant.MOVIE_IMAGE_BASE_URL
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.movieapp.model.Results
import com.example.movieapp.presention.navigation.MovieRoute
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(popularMoviesState: MutableStateFlow<PagingData<Results>>, navController: NavHostController) {

    val moviePagingItems =popularMoviesState.collectAsLazyPagingItems()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.tertiary),

        ) {

        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()

                .scale(1.5f, 2f)
                .rotate(90f),


        )
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier
                .fillMaxWidth(.95f)
                .height(150.dp)
                .background(Color(0xfff6d776), RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)))
            {
                Column(Modifier.fillMaxSize(),Arrangement.Center,Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription ="logo",Modifier.size(80.dp) )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (Modifier.fillMaxWidth(),Arrangement.SpaceAround,Alignment.CenterVertically){
                        Text(text = "Recommended")
                        Text(text = "Schedule")
                        Text(text = "Coming Soon")
                    }

                }

            }







                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(10.dp)
                    ) {


                        items(moviePagingItems.itemCount) { index->

                            if (moviePagingItems[index]?.adult==false){

                                Column(
                                    Modifier
                                        .fillMaxSize(.8f)
                                        .padding(0.dp, 0.dp)
                                        .background(
                                            Color.White,
                                            RoundedCornerShape(20.dp)
                                        )
                                        .clickable { navController.navigate(MovieRoute.route + "/${moviePagingItems[index]?.id}") },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
//
                                    AsyncImage(
                                        model = MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + moviePagingItems[index]?.backdropPath,
                                        contentDescription = moviePagingItems[index]?.title.orEmpty(),
                                        modifier = Modifier
                                            .padding(0.dp).
                                                size(250.dp)
                                            .fillMaxWidth()
                                            .clip(
                                                RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
                                            ),
                                        contentScale = ContentScale.Crop
                                    )


                                    Text(
                                        text = moviePagingItems[index]?.title.orEmpty(),
                                        Modifier
                                            .padding(10.dp)
                                            .height(30.dp)
                                    )
                                }
                            }
                        }

                    }
                }

            }


    }








