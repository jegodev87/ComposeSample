package com.sample.test.ui.theme.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sample.test.ui.theme.datasource.StudentViewModel
import java.util.Locale

@Composable
fun MovieList(viewModel: StudentViewModel = viewModel()) {

    LaunchedEffect(key1 = 1) {
        viewModel.getMovieList()
    }

    val movieResponse by viewModel.movieList.collectAsStateWithLifecycle()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / 3
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)) {
        movieResponse.forEach { movieDetail ->

            item {
                Text(text = movieDetail.key.capitalize(Locale.ROOT).replace("_", " "),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    )
            }
            item {
                if (movieDetail.key == "oscar_nominated_movies" || movieDetail.key == "recent_movies") {
                    // 🔹 Horizontally scrollable layout
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(movieDetail.movies) { movie ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(itemWidth - 16.dp) // subtract padding to prevent clipping
                                    .padding(vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(.75f)
                                ) {
                                    AsyncImage(
                                        model = movie.image_url,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = movie.name,
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = TextAlign.Center,
                                    minLines = 2,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }else {

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            maxItemsInEachRow = 3,
                            itemVerticalAlignment = Alignment.CenterVertically
                        ) {
                            movieDetail.movies.forEach { movie ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .width(itemWidth - 16.dp) // subtract padding to prevent clipping
                                        .weight(1f / 3f)  // For 3 columns, each takes ~1/3 width
                                        .padding(4.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Card(
                                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(.75f)
                                                .padding(bottom = 8.dp),
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier.fillMaxSize(),
                                            model = movie.image_url,
                                            contentDescription = movie.image_url + System.currentTimeMillis(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Text(
                                        text = movie.name,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.labelMedium,
                                        maxLines = 2,
                                        minLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }
                            }

                        }
                }
            }
        }

            /*item(movieDetail.movies){
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .height(200.dp),
                    contentPadding = PaddingValues(4.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(movieDetail.movies){

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Card(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .aspectRatio(ratio = .75f),
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = it.image_url,
                                contentDescription = it.image_url + System.currentTimeMillis(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))
                        Text(
                            text = it.name,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelMedium
                        )

                    }
                }
                }
            }
*/




        }

        /* LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentPadding = PaddingValues(4.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        movieResponse.forEach { movieDetail ->



            item {
                Text(text = movieDetail.key)
            }
            items(movieDetail.movies) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .aspectRatio(ratio = .75f),
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = it.image_url,
                            contentDescription = it.image_url + System.currentTimeMillis(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp))
                    Text(
                        text = it.name,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium
                    )

                }
            }

        }

    }*/

}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun getGridItemWidth(columns: Int): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return screenWidth / columns
}