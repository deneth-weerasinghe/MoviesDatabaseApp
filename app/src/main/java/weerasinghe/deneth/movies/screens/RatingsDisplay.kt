package weerasinghe.deneth.movies.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.RatingWithMovieDto

@Composable
fun RatingsDisplay(  // UI element, which are stored as trees joined together by JetpackCompose
    ratingId: String,
    fetchRatingWithMovies: suspend (String) -> RatingWithMovieDto
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

    SimpleText(text = "Rating")
    ratingWithMovieDto?.let { ratingWithMovies ->
        SimpleText(text = ratingWithMovies.rating.name)
        ratingWithMovies.movies.forEach { movie ->
            SimpleText(text = "Movie: ${movie.title}")
        }
    }
}