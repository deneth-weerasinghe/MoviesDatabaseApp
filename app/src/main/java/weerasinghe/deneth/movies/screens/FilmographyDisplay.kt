package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
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
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.ActorWithFilmographyDto

@Composable
fun FilmographyDisplay(
    actorId: String,
    fetchActorWithFilmography: suspend (String) -> ActorWithFilmographyDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieClick: (String) -> Unit,
    ) {
    var actorWithFilmographyDto by remember { mutableStateOf<ActorWithFilmographyDto?>(null) }

    LaunchedEffect(key1 = actorId) {
        actorWithFilmographyDto = fetchActorWithFilmography(actorId)
    }

    MovieScaffold(
        title = actorWithFilmographyDto?.actor?.name ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            actorWithFilmographyDto?.let { actorWithFilmography ->
                actorWithFilmography.filmography.forEach { filmography ->
                    SimpleText(text = "${filmography.character} (${filmography.movie.title})") {
                        onMovieClick(filmography.movie.id)
                    }
                }
            }
        }
    }
}