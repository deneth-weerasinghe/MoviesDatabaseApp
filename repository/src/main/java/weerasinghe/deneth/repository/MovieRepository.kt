package weerasinghe.deneth.repository

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    val ratingsFlow: Flow<List<RatingDto>>  // exposing flows as properties
    val moviesFlow: Flow<List<MovieDto>>
    val actorsFlow: Flow<List<ActorDto>>

    suspend fun insert(rating: RatingDto)
    suspend fun insert(movie: MovieDto)
    suspend fun insert(actor: ActorDto)

    suspend fun update(rating: RatingDto)
    suspend fun update(movie: MovieDto)
    suspend fun update(actor: ActorDto)

    suspend fun delete(rating: RatingDto)
    suspend fun delete(movie: MovieDto)
    suspend fun delete(actor: ActorDto)

    suspend fun resetDatabase()
}