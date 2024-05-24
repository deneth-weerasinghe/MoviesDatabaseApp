package weerasinghe.deneth.movies.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.ActorWithFilmographyDto

@Composable
fun FilmographyDisplay(
    actorId: String,
    fetchActorWithFilmography: suspend (String) -> ActorWithFilmographyDto
) {
    var actorWithFilmographyDto by remember { mutableStateOf<ActorWithFilmographyDto?>(null) }

    LaunchedEffect(key1 = actorId) {
        actorWithFilmographyDto = fetchActorWithFilmography(actorId)
    }

    SimpleText(text = "Actor")
    actorWithFilmographyDto?.let { actorWithFilmography ->
        SimpleText(text = actorWithFilmography.actor.name)
        actorWithFilmography.filmography.forEach { filmography ->
            SimpleText(text = "${filmography.character} (${filmography.movie.title})")
        }
    }
}