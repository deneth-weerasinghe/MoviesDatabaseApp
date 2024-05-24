package weerasinghe.deneth.movies.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import weerasinghe.deneth.movies.components.SimpleText
import weerasinghe.deneth.repository.dto.ActorDto

@Composable
fun ActorList(
    actors: List<ActorDto>,
    onActorClick: (String) -> Unit,
) {
    Column {
        SimpleText(text = "Actors")
        actors.forEach {  // loops until all movies displayed in column, automatically refreshes (because of state)
            SimpleText(text = it.name) {
                onActorClick(it.id)
            }
        }
    }
}