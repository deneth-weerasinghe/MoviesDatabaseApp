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

data class CastScreen(
    val id: String
): Screen

data class FilmographyScreen(
    val id: String
): Screen

class MovieViewModel(
    private val repository: MovieRepository
): ViewModel() {
// ViewModel instance exists for as long as the screen/activity is not switched to another
// Rotating would not create a new ViewModel

    var screen by mutableStateOf<Screen?>(MovieListScreen)  // makes it so everytime the screen changes, the function is rerun because of by i.e. refreshes screen
        private set  // public property but hidden setter (only this class can set screen)

    private var screenStack = listOf<Screen>(MovieListScreen)
        set(value) {
            field = value
            screen = value.lastOrNull()
            // when adding to the stack, that screen will be set as the screen property of this class
            // if screenStack ends up being empty, then set screen property to null
        }

    fun pushScreen(screen: Screen) {
        screenStack = screenStack + screen
    }
    fun popScreen() {
        if (screenStack.isNotEmpty()) {
            screenStack = screenStack.dropLast(1)
        }
    }
    fun setScreenStack(screen: Screen) {
        screenStack = listOf(screen)
    }

    val ratingsFlow = repository.ratingsFlow  // flows of dtos
    val moviesFlow = repository.moviesFlow
    val actorsFlow = repository.actorsFlow

    suspend fun getRatingWithMovies(id: String) =
        repository.getRatingWithMovies(id)  // this is a suspend function so this whole function is suspend as well

    suspend fun getMovieWithCast(id: String) =
        repository.getMovieWithCast(id)

    suspend fun getActorWithFilmography(id: String) =
        repository.getActorWithFilmography(id)

    fun resetDatabase() {  // starts a coroutine and resets database
        // if this coroutine is running while MovieViewModel ends, it will cancel all the MovieViewModel is doing
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }

    var selectedItemIds by mutableStateOf<Set<String>>(emptySet())  // bucket to hold item ids (mutableStateOf is Compose aware state!, let's us update selection on UI)
        private set

    fun clearSelection() {
        selectedItemIds = emptySet()
    }

    fun toggleSelection(id: String) {
        selectedItemIds =
            if (id in selectedItemIds) {
                selectedItemIds - id
            } else {
                selectedItemIds + id
            }
    }

    fun deleteSelectedActors() {
        viewModelScope.launch {
            repository.deleteActorById(selectedItemIds)
            clearSelection()
        }
    }
    fun deleteSelectedMovies() {
        viewModelScope.launch {
            repository.deleteMovieById(selectedItemIds)
            clearSelection()
        }
    }
    fun deleteSelectedRatings() {
        viewModelScope.launch {
            repository.deleteRatingById(selectedItemIds)
            clearSelection()
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