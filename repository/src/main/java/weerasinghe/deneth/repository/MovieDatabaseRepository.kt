package weerasinghe.deneth.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import weerasinghe.deneth.data.MovieDao

class MovieDatabaseRepository(
    private val dao: MovieDao  // instance of dao must be passed to create repository, known as constructor injection/dependency injection into constructor
): MovieRepository {

    override val ratingsFlow: Flow<List<RatingDto>> =
        dao.getRatingFlow()
            .map { ratings ->  // map creates a new list based on the items it receives
                // "for each List<RatingEntity> that's emitted, create a list of RatingDto, and using that list, do the following:
                ratings.map { rating -> rating.toDto() }  // maps each entity to Dto
                // summary: converting from Flow<List<RatingEntity>> to Flow<List<RatingDto>>
            }
    override val moviesFlow: Flow<List<MovieDto>> =
        dao.getMovieFlow()
            .map { movies ->
                movies.map { movie -> movie.toDto() }
            }
    override val actorsFlow: Flow<List<ActorDto>> =
        dao.getActorFlow()
            .map { actors ->
                actors.map { it.toDto() }  // using it is shorter syntax
            }

    override suspend fun insert(rating: RatingDto) = dao.insert(rating.toEntity())  // note lack of varargs
    override suspend fun insert(movie: MovieDto) = dao.insert(movie.toEntity())
    override suspend fun insert(actor: ActorDto) = dao.insert(actor.toEntity())

    override suspend fun update(rating: RatingDto) = dao.update(rating.toEntity())
    override suspend fun update(movie: MovieDto) = dao.update(movie.toEntity())
    override suspend fun update(actor: ActorDto) = dao.update(actor.toEntity())

    override suspend fun delete(rating: RatingDto) = dao.delete(rating.toEntity())
    override suspend fun delete(movie: MovieDto) = dao.delete(movie.toEntity())
    override suspend fun delete(actor: ActorDto) = dao.delete(actor.toEntity())
    override suspend fun resetDatabase() = dao.resetDatabase()
}