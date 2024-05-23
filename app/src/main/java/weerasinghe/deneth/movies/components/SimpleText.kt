package weerasinghe.deneth.movies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleText(
    text: String,
    onClick: () -> Unit
) =
    Text(
        text = text,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick()
            }
    )