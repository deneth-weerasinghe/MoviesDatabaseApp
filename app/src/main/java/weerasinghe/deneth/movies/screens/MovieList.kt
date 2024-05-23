package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.MovieDto

@Composable
fun MovieList(
    movies: List<MovieDto>
) {
    Column {
        SimpleText(text = "Movies")
        movies.forEach {  // loops until all movies displayed
            SimpleText(text = it.title)
        }
    }
}