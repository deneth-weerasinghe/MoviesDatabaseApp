package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import weerasinghe.deneth.movies.R
import weerasinghe.deneth.movies.Screen
import weerasinghe.deneth.movies.components.MovieScaffold
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.RatingDto

@Composable
fun RatingList(
    ratings: List<RatingDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onRatingClick: (String) -> Unit,
    ) = MovieScaffold(
        title = stringResource(id = R.string.screen_title_rating),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase
    ) { paddingValues ->
    LazyColumn(modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = ratings,
            key = { it.id }  // needs to uniquely identify each card, so we use actorId; Remember we can use it since it's a single parameter in lambda
            // key = { actor -> actor.id } is the alternative
        ) {
            Card(
                elevation = CardDefaults.cardElevation(),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                SimpleText(text = it.name) {
                    onRatingClick(it.id)
                }
            }
        }
    }
}
