package weerasinghe.deneth.movies.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit  // event handler
) =
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }  // remember that if there's a lambda as last parameter, it can be put outside the function call in braces