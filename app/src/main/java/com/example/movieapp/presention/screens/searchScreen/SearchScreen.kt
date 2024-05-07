package com.example.movieapp.presention.screens.searchScreen

import Constrant.MOVIE_IMAGE_BASE_URL
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.movieapp.model.BackdropSize
import com.example.movieapp.model.Results
import com.example.movieapp.presention.navigation.MovieRoute
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen( searchViewModel: SearchViewModel, navController: NavHostController){
    val searchText = searchViewModel.searchState.collectAsLazyPagingItems()


    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        SearchBar( modifier = Modifier
            .align(Alignment.TopCenter)
            .semantics { traversalIndex = -1f },
            query = text,
            onQueryChange = {
                text = it
                searchViewModel.search(it)
                            },
            onSearch = { active = false },
            active = false,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Hinted search text") }) {
            Row (Modifier.background(Color.DarkGray)){

            }

        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            items(searchText.itemCount) { index ->
                searchText[index]?.let {
                    Column(
                        Modifier
                            .fillMaxSize(.8f)
                            .padding(0.dp, 0.dp)
                            .background(
                                Color.White,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { navController.navigate(MovieRoute.route + "/${searchText[index]?.id}") },


                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//
                        AsyncImage(
                            model = MOVIE_IMAGE_BASE_URL + BackdropSize.w1280 + searchText[index]?.backdropPath,
                            contentDescription = searchText[index]?.title.orEmpty(),
                            modifier = Modifier
                                .padding(0.dp)
                                .size(250.dp)
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )


                        Text(
                            text = searchText[index]?.title.orEmpty(),
                            Modifier
                                .padding(10.dp)
                                .height(30.dp)
                        )
                    }
                    searchText.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {

                                Row(
                                    Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val error = searchText.loadState.refresh as LoadState.Error
                                Row(
                                    Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = error.error.localizedMessage.orEmpty(),
                                        modifier = Modifier,
                                    )
                                }
                            }




                        }

                }
            }
        }

    }



}
}