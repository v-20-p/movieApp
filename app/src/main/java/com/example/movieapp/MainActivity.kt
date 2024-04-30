package com.example.movieapp

import Constrant.MOVIE_BASE_URL
import Constrant.MOVIE_IMAGE_BASE_URL
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.model.UIState
import com.example.movieapp.presention.screens.Popular.PopularMoviesViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.res.painterResource

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.model.BackdropSize

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {


                Box(modifier = Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.tertiary)) {
                    Image(painter = painterResource(R.drawable.background), contentDescription = "")
                    Image(painter = painterResource(R.drawable.background), contentDescription = "")

                    // A surface container using the 'background' color from the theme
                    val viewModel by viewModels<PopularMoviesViewModel>()
                    when (val result = viewModel.popularMoviesState.value) {
                        is UIState.Success -> {


                            LazyColumn(
                                modifier = Modifier

                                    .background(MaterialTheme.colorScheme.tertiary)
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
            }
        }
    }
}



