package weerasinghe.deneth.repository

import kotlinx.coroutines.flow.Flow
import weerasinghe.deneth.repository.dto.ActorDto
import weerasinghe.deneth.repository.dto.ActorWithFilmographyDto
import weerasinghe.deneth.repository.dto.MovieDto
import weerasinghe.deneth.repository.dto.MovieWithCastDto
import weerasinghe.deneth.repository.dto.RatingDto
import weerasinghe.deneth.repository.dto.RatingWithMovieDto

interface MovieRepository {
    val ratingsFlow: Flow<List<RatingDto>>  // exposing flows as properties
    val moviesFlow: Flow<List<MovieDto>>
    val actorsFlow: Flow<List<ActorDto>>

    suspend fun getRatingWithMovies(id: String): RatingWithMovieDto
    suspend fun getMovieWithCast(id: String): MovieWithCastDto
    suspend fun getActorWithFilmography(id: String): ActorWithFilmographyDto

    suspend fun insert(rating: RatingDto)
    suspend fun insert(movie: MovieDto)
    suspend fun insert(actor: ActorDto)

    suspend fun update(rating: RatingDto)
    suspend fun update(movie: MovieDto)
    suspend fun update(actor: ActorDto)

    suspend fun delete(rating: RatingDto)
    suspend fun delete(movie: MovieDto)
    suspend fun delete(actor: ActorDto)

    suspend fun deleteMovieById(ids: Set<String>)
    suspend fun deleteActorById(ids: Set<String>)
    suspend fun deleteRatingById(ids: Set<String>)

    suspend fun resetDatabase()
}