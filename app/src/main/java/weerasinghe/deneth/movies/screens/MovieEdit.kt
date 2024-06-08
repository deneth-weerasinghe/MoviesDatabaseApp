package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import weerasinghe.deneth.movies.R
import weerasinghe.deneth.movies.Screen
import weerasinghe.deneth.movies.components.MovieScaffold
import weerasinghe.deneth.movies.components.TextEntry
import weerasinghe.deneth.repository.dto.MovieDto

@Composable
fun MovieEdit(
    movieId: String,
    fetchMovie: suspend (String) -> MovieDto,
    onSelectScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieUpdate: (MovieDto) -> Unit,
) {
    var movie by remember { mutableStateOf<MovieDto?>(null) }
    
    LaunchedEffect(key1 = movieId) {
        // starts coroutine to fetch rating
        movie = fetchMovie(movieId)
    }
    
    MovieScaffold(
        title = movie?.title ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectScreen,
        onResetDatabase = onResetDatabase
    ) { paddingValues ->  
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            TextEntry(
                labelId = R.string.title,
                placeholderId = R.string.movie_title_placeholder,
                value = movie?.title ?: "",
                onValueChange = {
                    movie = movie?.copy(title = it)?.apply {  // copy comes from data class; local copy!
                        onMovieUpdate(this)  // updates database (to change title) then makes local copy that updates bucket, faster than re-fetching
                    }
                }
            )
            TextEntry(
                labelId = R.string.description,
                placeholderId = R.string.movie_description_placeholder,
                value = movie?.description ?: "",
                onValueChange = {
                    movie = movie?.copy(description = it)?.apply {
                        onMovieUpdate(this)  // updates database to change description
                    }
                }
            )
        }
    }                
}