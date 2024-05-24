package weerasinghe.deneth.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import weerasinghe.deneth.repository.MovieDatabaseRepository
import weerasinghe.deneth.repository.MovieRepository

// Let's define a few screens
sealed interface Screen
// sealed interfaces are interfaces whose implementations are only defined in same module (app module here)
// useful for exhausting when statements
object RatingListScreen: Screen
object MovieListScreen: Screen
object ActorListScreen: Screen

data class RatingScreen(
    val id: String
): Screen

class MovieViewModel(
    private val repository: MovieRepository
): ViewModel() {
// ViewModel instance exists for as long as the screen/activity is not switched to another
// Rotating would not create a new ViewModel

    var screen by mutableStateOf<Screen>(RatingListScreen)  // makes it so everytime the screen changes, the function is rerun because of by i.e. refreshes screen
        private set  // public property but hidden setter

    val ratingsFlow = repository.ratingsFlow  // flows of dtos
    val moviesFlow = repository.moviesFlow
    val actorsFlow = repository.actorsFlow

    fun switchTo(screen: Screen) {
        this.screen = screen
    }

    suspend fun getRatingWithMovies(id: String) =
        repository.getRatingWithMovies(id)  // this is a suspend function so this whole function is suspend as well

    fun resetDatabase() {  // starts a coroutine and resets database
        // if this coroutine is running while MovieViewModel ends, it will cancel all the MovieViewModel is doing
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
                ): T {
                // Get the application object from extras:
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return MovieViewModel(
                    MovieDatabaseRepository.create(application)
                ) as T
            }
        }
    }
}