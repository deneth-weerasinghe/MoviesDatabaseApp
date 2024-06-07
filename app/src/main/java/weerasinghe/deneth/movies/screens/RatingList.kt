package weerasinghe.deneth.movies.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import weerasinghe.deneth.movies.R
import weerasinghe.deneth.movies.Screen
import weerasinghe.deneth.movies.components.ListScaffold
import weerasinghe.deneth.movies.components.SimpleListText
import weerasinghe.deneth.repository.dto.RatingDto

@Composable
fun RatingList(
    ratings: List<RatingDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onRatingClick: (String) -> Unit,

    // For selection
    selectedItemIds: Set<String>,
    onClearSelection: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onDeleteSelectedItems: () -> Unit,
) = ListScaffold(
    title = stringResource(id = R.string.screen_title_rating),
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
    items = ratings,
    icon = Icons.Default.Emergency,
    iconDescriptionId = R.string.tap_to_toggle_selection,
    onItemClick = onRatingClick,

    // For selection
    selectedItemIds = selectedItemIds,
    onClearSelection = onClearSelection,
    onToggleSelection = onToggleSelection,
    onDeleteSelectedItems = onDeleteSelectedItems
) {
    SimpleListText(text = it.name)
}
