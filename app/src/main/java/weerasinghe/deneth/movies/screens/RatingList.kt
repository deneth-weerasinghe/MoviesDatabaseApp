package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.RatingDto

@Composable
fun RatingList(
    ratings: List<RatingDto>,
    onRatingClick: (String) -> Unit,
) {
    Column {
        SimpleText(text = "Ratings")
        ratings.forEach {
            SimpleText(text = it.name) {
                onRatingClick(it.id)
            }
        }
    }
}