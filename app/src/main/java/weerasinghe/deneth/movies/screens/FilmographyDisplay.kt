package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
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
        val actorDto = actorWithFilmographyDto  // captures value since getter and setter delegated to mutableState bucket i.e. allows smart cast

        // Imperative way of handling case of empty list (using if instead of scope functions
        if (actorDto == null || actorDto.filmography.isEmpty()) {
            SimpleText(
                text = stringResource(id = R.string.no_movies_found_for_this_actor),
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                SimpleText(text = stringResource(id = R.string.filmography, actorDto.actor.name))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                        .weight(1f),
                ) {
                    items(
                        items = actorWithFilmographyDto?.filmography ?: emptyList()
                    ) { filmography ->
                        Card(
                            elevation = CardDefaults.cardElevation(),
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            SimpleText(text = "${filmography.character} (${filmography.movie.title})") {
                                onMovieClick(filmography.movie.id)
                            }
                        }
                    }
                }
            }
        }
    }
}