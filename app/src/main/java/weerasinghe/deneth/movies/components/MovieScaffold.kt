package weerasinghe.deneth.movies.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import weerasinghe.deneth.movies.ActorListScreen
import weerasinghe.deneth.movies.MovieListScreen
import weerasinghe.deneth.movies.R
import weerasinghe.deneth.movies.RatingListScreen
import weerasinghe.deneth.movies.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScaffold(
    title: String,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,

    // For selection
    selectedItemCount: Int = 0,
    onClearSelections: () -> Unit = {},
    onDeleteSelectedItems: () -> Unit = {},

    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            if (selectedItemCount == 0) {  // normal behaviour
            TopAppBar(
                title =  { SimpleText(text = title) },
                actions = {
                    IconButton(
                        onClick = onResetDatabase,
                        modifier = Modifier.padding(8.dp)
                        ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.reset_database)
                        )
                    }
                }
            )
            } else {  // behaviour if any is selected, either by longPress initially or tap afterwards
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = onClearSelections,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.clear_selections)
                            )
                        }
                    },
                    title = { SimpleText(text = selectedItemCount.toString()) },
                    actions = {
                        IconButton(
                            onClick = onDeleteSelectedItems,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete_selected_items)
                            )
                        }
                    }
                )
            }
        },
        content = { paddingValues ->
            content(paddingValues)
        },
        bottomBar = {
            NavigationBar {
                ScreenSelectButton(
                    targetScreen = RatingListScreen,
                    imageVector = Icons.Default.Emergency,
                    labelId = R.string.screen_title_rating,
                    onSelectListScreen = onSelectListScreen
                )
                ScreenSelectButton(
                    targetScreen = MovieListScreen,
                    imageVector = Icons.Default.Movie,
                    labelId = R.string.screen_title_movies,
                    onSelectListScreen = onSelectListScreen
                )
                ScreenSelectButton(
                    targetScreen = ActorListScreen,
                    imageVector = Icons.Default.Person,
                    labelId = R.string.screen_title_actors,
                    onSelectListScreen = onSelectListScreen
                )
            }
        }
    )
}

@Composable
fun RowScope.ScreenSelectButton(
    targetScreen: Screen,
    imageVector: ImageVector,
    @StringRes labelId: Int,
    onSelectListScreen: (Screen) -> Unit
) =
    NavigationBarItem(
        selected = false,
        icon = {
               Icon(
                   imageVector = imageVector,
                   contentDescription = stringResource(id = labelId)
               )
        },
        label = {
                Text(text = stringResource(id = labelId))
        },
        onClick = {
            onSelectListScreen(targetScreen)
        }
    )