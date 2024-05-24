package weerasinghe.deneth.movies.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.MovieWithCastDto

@Composable
fun  CastDisplay(
    movieId: String,
    fetchMovieWithCast: suspend (String) -> MovieWithCastDto
) {
    var movieWithCastDto by remember { mutableStateOf<MovieWithCastDto?>(null) }

    LaunchedEffect(key1 = movieId) {
        movieWithCastDto = fetchMovieWithCast(movieId)
    }
    
    SimpleText(text = "Movie")
    movieWithCastDto?.let { movieWithCast ->
        SimpleText(text = movieWithCast.movie.title)
        movieWithCast.cast.forEach { cast ->
            SimpleText(text = "${cast.character}: ${cast.actor.name}")
        }
    }
}