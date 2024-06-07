package weerasinghe.deneth.movies.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import weerasinghe.deneth.movies.R
import weerasinghe.deneth.movies.Screen
import weerasinghe.deneth.movies.components.ListScaffold
import weerasinghe.deneth.movies.components.SimpleListText
import weerasinghe.deneth.repository.dto.MovieDto

@Composable
fun MovieList(
    movies: List<MovieDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieClick: (String) -> Unit,

    // For selection
    selectedItemIds: Set<String>,
    onClearSelection: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onDeleteSelectedItems: () -> Unit,
) = ListScaffold(
    title = stringResource(id = R.string.screen_title_movies),
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
    items = movies,
    icon = Icons.Default.Movie,
    iconDescriptionId = R.string.tap_to_toggle_selection,
    onItemClick = onMovieClick,

    // For selection
    selectedItemIds = selectedItemIds,
    onClearSelection = onClearSelection,
    onToggleSelection = onToggleSelection,
    onDeleteSelectedItems = onDeleteSelectedItems
) {
    SimpleListText(text = it.title)
}
