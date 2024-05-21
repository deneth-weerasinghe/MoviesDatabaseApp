package weerasinghe.deneth.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import weerasinghe.deneth.repository.MovieDatabaseRepository
import weerasinghe.deneth.repository.MovieRepository

class MovieViewModel(
    private val repository: MovieRepository
): ViewModel() {
// Viewmodel instance exists for as long as the screen/activity is not switched to another
// Rotating would not create a new viewmodel

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