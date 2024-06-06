package weerasinghe.deneth.movies.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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
    onToggleSelection: (String) -> Unit,
    itemContent: @Composable (T) -> Unit,
) = MovieScaffold(
    title = title,
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase
) { paddingValues ->
    LazyColumn(modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = items,
            key = { it.id }  // making T instance of HasId removes need to use functions to retrieve id to get a key
        ) { item ->
            Card(
                elevation = CardDefaults.cardElevation(),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
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
