package weerasinghe.deneth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import weerasinghe.deneth.data.entities.ActorEntity
import weerasinghe.deneth.data.entities.MovieEntity
import weerasinghe.deneth.data.entities.RatingEntity
import weerasinghe.deneth.data.entities.RoleEntity

@Dao
abstract class MovieDao {  // abstract class: can have both implemented and abstract functions
    @Query("SELECT * FROM RatingEntity")
    abstract fun getRatingFlow(): Flow<List<RatingEntity>>
    @Query("SELECT * FROM MovieEntity")
    abstract fun getMovieFlow(): Flow<List<MovieEntity>>
    @Query("SELECT * FROM ActorEntity")
    abstract fun getActorFlow(): Flow<List<ActorEntity>>
    // note getRolesFlow not used

    @Insert
    abstract suspend fun insert(vararg ratings: RatingEntity)
    @Insert
    abstract suspend fun insert(vararg movies: MovieEntity)
    @Insert
    abstract suspend fun insert(vararg actors: ActorEntity)
    @Insert
    abstract suspend fun insert(vararg roles: RoleEntity)

    @Update
    abstract suspend fun update(vararg ratings: RatingEntity)
    @Update
    abstract suspend fun update(vararg movies: MovieEntity)
    @Update
    abstract suspend fun update(vararg actors: ActorEntity)
    @Update
    abstract suspend fun update(vararg roles: RoleEntity)

    @Delete
    abstract suspend fun delete(vararg ratings: RatingEntity)
    @Delete
    abstract suspend fun delete(vararg movies: MovieEntity)
    @Delete
    abstract suspend fun delete(vararg actors: ActorEntity)
    @Delete
    abstract suspend fun delete(vararg roles: RoleEntity)

    @Query("DELETE FROM RatingEntity")
    abstract suspend fun clearRatings()
    @Query("DELETE FROM MovieEntity")
    abstract suspend fun clearMovies()
    @Query("DELETE FROM ActorEntity")
    abstract suspend fun clearActors()
    @Query("DELETE FROM RoleEntity")
    abstract suspend fun clearRoles()

    @Transaction  // adds dummy data to test with
    open suspend fun resetDatabase() {
        clearMovies()
        clearActors()
        clearRoles()
        clearRatings()

        insert(NotRated, G, PG, PG13, R)

        insert(
            MovieEntity("m1", "The Transporter", "Jason Statham kicks a guy in the face", "r3"),
            MovieEntity("m2", "Transporter 2", "Jason Statham kicks a bunch of guys in the face", "r4"),
            MovieEntity("m3", "Hobbs and Shaw", "Cars, Explosions and Stuff", "r3"),
            MovieEntity("m4", "Jumanji - Welcome to the Jungle", "The Rock smolders", "r3"),
        )
        insert(
            ActorEntity("a1", "Jason Statham"),
            ActorEntity("a2", "The Rock"),
            ActorEntity("a3", "Shu Qi"),
            ActorEntity("a4", "Amber Valletta"),
            ActorEntity("a5", "Kevin Hart"),
        )
        insert(
            RoleEntity("m1", "a1", "Frank Martin", 1),
            RoleEntity("m1", "a3", "Lai", 2),
            RoleEntity("m2", "a1", "Frank Martin", 1),
            RoleEntity("m2", "a4", "Audrey Billings", 2),
            RoleEntity("m3", "a2", "Hobbs", 1),
            RoleEntity("m3", "a1", "Shaw", 2),
            RoleEntity("m4", "a2", "Spencer", 1),
            RoleEntity("m4", "a5", "Fridge", 2),
        )
    }

    companion object {  // equivalent to static functions/static fields in Java i.e. constants; vals can be called anywhere in MovieDao
        private val NotRated = RatingEntity(id = "r0", name = "Not Rated", description = "Not yet rated")
        private val G = RatingEntity(id = "r1", name = "G", description = "General Audiences")
        private val PG = RatingEntity(id = "r2", name = "PG", description = "Parental Guidance Suggested")
        private val PG13 = RatingEntity(id = "r3", name = "PG-13", description = "Unsuitable for those under 13")
        private val R = RatingEntity(id = "r4", name = "R", description = "Restricted - 17 and older")
    }
}
