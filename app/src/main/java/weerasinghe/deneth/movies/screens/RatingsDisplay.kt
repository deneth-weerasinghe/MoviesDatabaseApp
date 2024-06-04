package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import weerasinghe.deneth.movies.R
import weerasinghe.deneth.movies.Screen
import weerasinghe.deneth.movies.components.MovieScaffold
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.RatingWithMovieDto

@Composable
fun RatingsDisplay(  // UI element, which are stored as trees joined together by JetpackCompose
    ratingId: String,
    fetchRatingWithMovies: suspend (String) -> RatingWithMovieDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieClick: (String) -> Unit,
    ) {
    var ratingWithMovieDto by remember { mutableStateOf<RatingWithMovieDto?>(null) }
    // we need a bucket (mutableStateOf) to hold ratingWithMovieDto to prevent flickering when fetching
    // remember stores the bucket in sub-tree

    LaunchedEffect(key1 = ratingId) {  // actual fetching
        // Starts coroutine off-of UI thread to fetch rating
        // Everytime parameter changes, it cancels this and re-runs the following:
        // Assumes you can't delete movies from UI
        ratingWithMovieDto = fetchRatingWithMovies(ratingId)
    }

    MovieScaffold(
        title = ratingWithMovieDto?.rating?.name ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase
    ) { paddingValues ->
        // using let, takeIf and run is functional way of handling case of empty list, there's also imperative (see FilmographyDisplay
        ratingWithMovieDto?.let {ratingWithMovieDto ->
            ratingWithMovieDto
                .takeIf { it.movies.isNotEmpty() }
                ?.run {
                    LazyColumn(modifier = Modifier.padding(paddingValues)) {
                        items(
                            items = ratingWithMovieDto.movies
                        ) { movie ->
                            Card(
                                elevation = CardDefaults.cardElevation(),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                SimpleText(text = movie.title) {
                                    onMovieClick(movie.id)
                                }
                            }
                        }
                    }
                }
                ?: run {
                    SimpleText(text = stringResource(id = R.string.no_movies_found_for_this_rating),
                        modifier = Modifier.padding(paddingValues)
                    )
                }
        }
    }
}