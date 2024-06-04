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
import weerasinghe.deneth.repository.dto.MovieWithCastDto

@Composable
fun  CastDisplay(
    movieId: String,
    fetchMovieWithCast: suspend (String) -> MovieWithCastDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onActorClick: (String) -> Unit,
    ) {
    var movieWithCastDto by remember { mutableStateOf<MovieWithCastDto?>(null) }

    LaunchedEffect(key1 = movieId) {
        movieWithCastDto = fetchMovieWithCast(movieId)
    }

    MovieScaffold(
        title = movieWithCastDto?.movie?.title ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase
    ) { paddingValues ->
        val movie = movieWithCastDto  // captures value since getter and setter delegated to mutableState bucket i.e. allows smart cast

        // Imperative way of handling case of empty list (using if instead of scope functions
        if (movie == null || movie.cast.isEmpty()) {
            SimpleText(
                text = stringResource(id = R.string.no_actors_found_for_this_movie),
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(
                    items = movieWithCastDto?.cast ?: emptyList()
                ) { cast ->
                    Card(
                        elevation = CardDefaults.cardElevation(),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        SimpleText(text = "${cast.character}: ${cast.actor.name}") {
                            onActorClick(cast.actor.id)
                        }
                    }
                }
            }
        }
    }
}