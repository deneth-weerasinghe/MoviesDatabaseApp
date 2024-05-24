package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import weerasinghe.deneth.movies.ActorListScreen
import weerasinghe.deneth.movies.CastScreen
import weerasinghe.deneth.movies.MovieListScreen
import weerasinghe.deneth.movies.MovieViewModel
import weerasinghe.deneth.movies.RatingListScreen
import weerasinghe.deneth.movies.RatingScreen
import weerasinghe.deneth.movies.components.SimpleButton

@Composable
fun TestUI(
    viewModel: MovieViewModel
) {
    Column {
        Row {
            SimpleButton(text = "Reset") {  // remember final lambda argument can be put outside braces
                viewModel.resetDatabase()
            }
            SimpleButton(text = "Ratings") {
                viewModel.switchTo(RatingListScreen)
            }
        }
        Row {
            SimpleButton(text = "Movies") {
                viewModel.switchTo(MovieListScreen)
            }
            SimpleButton(text = "Actors") {
                viewModel.switchTo(ActorListScreen)
            }
        }
        // Collecting flows
        val ratings by viewModel.ratingsFlow.collectAsState(initial = emptyList())
        val movies by viewModel.moviesFlow.collectAsState(initial = emptyList())
        val actors by viewModel.actorsFlow.collectAsState(initial = emptyList())

        when(val screen = viewModel.screen) {
            RatingListScreen -> RatingList(ratings = ratings) { id ->
                viewModel.switchTo(RatingScreen(id))
            }
            MovieListScreen -> MovieList(movies = movies) { id ->
                viewModel.switchTo(CastScreen(id))
            }
            ActorListScreen -> ActorList(actors = actors)
            is RatingScreen -> RatingsDisplay(
                ratingId = screen.id,
                fetchRatingWithMovies = { id ->
                    viewModel.getRatingWithMovies(id)
                }
            )
            is CastScreen -> CastDisplay(
                movieId = screen.id,
                fetchMovieWithCast = { id ->
                    viewModel.getMovieWithCast(id)
                }
            )
        }
    }
}