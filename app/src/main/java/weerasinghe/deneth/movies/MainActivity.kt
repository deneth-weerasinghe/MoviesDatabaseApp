package weerasinghe.deneth.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import weerasinghe.deneth.movies.screens.TestUI
import weerasinghe.deneth.movies.ui.theme.MoviesTheme

// Screen type
//      tracks which "screen" is visible
// Screen composable functions


class MainActivity : ComponentActivity() {
    // By operator: property delegation; right side of by creates object that manages this property
    // Anytime getter or setter called, it's gonna be delegated to that object created
    // Attempts to find the ViewModel in the system
    // "If ViewModel needs to be created, use this factory, which must implement an interface"
    private val viewModel: MovieViewModel by viewModels { MovieViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  // a JetCompose specification
            MoviesTheme {  // composable function
                // functions inside can access the theme
                Surface(  // a surface container using the 'background' color from the theme
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestUI(viewModel)
                }
            }
        }
    }
}
