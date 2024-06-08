package weerasinghe.deneth.movies.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextEntry(
    @StringRes labelId: Int,
    @StringRes placeholderId: Int,
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) =
    OutlinedTextField(
        label = {
            Text(
                text = stringResource(id = labelId)
            )
        },
        placeholder = {
            Text(
                text =
                    if (value == null) {
                        "loading..."
                    } else {
                        stringResource(id = placeholderId)
                    }
                )
            },
        value = value ?: "",
        onValueChange = {
            if (value != null) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )