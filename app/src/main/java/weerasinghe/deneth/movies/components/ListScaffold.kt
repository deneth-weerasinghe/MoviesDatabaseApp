package weerasinghe.deneth.movies.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import weerasinghe.deneth.movies.Screen
import weerasinghe.deneth.repository.HasId


// Common logic for all list, better than rewriting nearly identical code to each list file
@Composable
fun <T: HasId> ListScaffold(
    title: String,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    items: List<T>,
    icon: ImageVector,
    @StringRes iconDescriptionId: Int,  // id for alt-; annotation makes sure random integers aren't passed, just R-class integers
    onItemClick: (String) -> Unit,

    // For selection
    selectedItemIds: Set<String>,  // anytime something is selected, recompose (thanks to mutableStateOf)
    onClearSelection: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onDeleteSelectedItems: () -> Unit,

    itemContent: @Composable (T) -> Unit,
) = MovieScaffold(
    title = title,
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
    selectedItemCount = selectedItemIds.size,
    onDeleteSelectedItems = onDeleteSelectedItems,
    onClearSelections = onClearSelection
) { paddingValues ->
    LazyColumn(modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = items,
            key = { it.id }  // making T instance of HasId removes need to use functions to retrieve id to get a key
        ) { item ->

            val containerColor =
                if (item.id in selectedItemIds) {
                    MaterialTheme.colorScheme.primaryContainer  // when row is selected
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

            // The pointerInput lambda won't know if the passed in functions (parameters) change, so we need rememberUpdatedState to let Compose know
            // It is a bucket that can be updated during recomposition
            // Use this if lambda doesn't seem to see changes in parameters
            val selectedIds by rememberUpdatedState(newValue = selectedItemIds)

            Card(
                elevation = CardDefaults.cardElevation(),
                colors = CardDefaults.cardColors(
                    containerColor = containerColor  // uses above defined colours
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .pointerInput(true) {
                        detectTapGestures(
                            onLongPress = {
                                onToggleSelection(item.id)
                            },
                            onTap = {
                                if (selectedIds.isNotEmpty()) {
                                    onToggleSelection(item.id)
                                } else {
                                    onItemClick(item.id)
                                }
                            }
                        )
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onItemClick(item.id) }  // whole row clickable, not just text
                ) {
                    IconButton(
                        onClick = { onToggleSelection(item.id) },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        colors = IconButtonDefaults.filledIconButtonColors()
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(iconDescriptionId),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    itemContent(item)
                }
            }
        }
    }
}
