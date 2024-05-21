package weerasinghe.deneth.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import weerasinghe.deneth.movies.ui.theme.MoviesTheme

// Screen type
//      tracks which "screen" is visible
// Screen composable functions


class MainActivity : ComponentActivity() {
    // by operator: property delegation: right side of by creates object that manages this property
    // anytime getter or setter called, it's gonna be delegated to that object created
    // attempts to find the viewmodel in the system
    // left side says if viewmodel needs to be created, use this factory, which must implement an interface
    private val viewModel: MovieViewModel by viewModels { MovieViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}