package weerasinghe.deneth.movies.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import weerasinghe.deneth.movies.ActorListScreen
import weerasinghe.deneth.movies.CastScreen
import weerasinghe.deneth.movies.FilmographyScreen
import weerasinghe.deneth.movies.MovieListScreen
import weerasinghe.deneth.movies.MovieViewModel
import weerasinghe.deneth.movies.RatingListScreen
import weerasinghe.deneth.movies.RatingScreen

@Composable
fun UI(
    viewModel: MovieViewModel,
    onExit: () -> Unit
) {

    BackHandler {
        // Handles what to do when back button is pressed (see .popScreen() for detail)
        viewModel.popScreen()
    }

    // Collecting flows
    val ratings by viewModel.ratingsFlow.collectAsState(initial = emptyList())
    val movies by viewModel.moviesFlow.collectAsState(initial = emptyList())
    val actors by viewModel.actorsFlow.collectAsState(initial = emptyList())

    when(val screen = viewModel.screen) {

        null -> onExit()  // calls what onExit() is when screen is empty, see MainActivity.kt for detail

        RatingListScreen -> RatingList(
            ratings = ratings,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onRatingClick = { id ->
                viewModel.pushScreen(RatingScreen(id))
            },
            selectedItemIds = viewModel.selectedItemIds,
            onClearSelection = viewModel::clearSelection,
            onToggleSelection = viewModel::toggleSelection,
            onDeleteSelectedItems = viewModel::deleteSelectedRatings
        )
        MovieListScreen -> MovieList(
            movies = movies,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieClick = { id ->
                viewModel.pushScreen(CastScreen(id))
            },
            selectedItemIds = viewModel.selectedItemIds,
            onClearSelection = viewModel::clearSelection,
            onToggleSelection = viewModel::toggleSelection,
            onDeleteSelectedItems = viewModel::deleteSelectedMovies
        )
        ActorListScreen -> ActorList(
            actors = actors,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onActorClick = { id ->
                viewModel.pushScreen(FilmographyScreen(id))
            },
            selectedItemIds = viewModel.selectedItemIds,
            onClearSelection = viewModel::clearSelection,
            onToggleSelection = viewModel::toggleSelection,
            onDeleteSelectedItems = viewModel::deleteSelectedActors
        )

        is RatingScreen -> RatingsDisplay(
            ratingId = screen.id,
            fetchRatingWithMovies = { id ->
                viewModel.getRatingWithMovies(id)
            },
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieClick = { id ->
                viewModel.pushScreen(CastScreen(id))
            }
        )
        is CastScreen -> CastDisplay(
            movieId = screen.id,
            fetchMovieWithCast = viewModel::getMovieWithCast,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onActorClick = { id ->
                viewModel.pushScreen(FilmographyScreen(id))
            }
        )
        is FilmographyScreen -> FilmographyDisplay(
            actorId = screen.id,
            fetchActorWithFilmography = viewModel::getActorWithFilmography,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieClick = { id ->
                viewModel.pushScreen(CastScreen(id))
            }
        )
    }
}