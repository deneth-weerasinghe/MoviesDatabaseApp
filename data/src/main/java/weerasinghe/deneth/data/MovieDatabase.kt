package weerasinghe.deneth.data

import androidx.room.Database
import androidx.room.RoomDatabase
import weerasinghe.deneth.data.entities.ActorEntity
import weerasinghe.deneth.data.entities.MovieEntity
import weerasinghe.deneth.data.entities.RatingEntity
import weerasinghe.deneth.data.entities.RoleEntity

@Database(
    version = 1,
    entities = [
        RatingEntity::class,
        MovieEntity::class,
        ActorEntity::class,
        RoleEntity::class,
    ],
    exportSchema = false

)
abstract class MovieDatabase: RoomDatabase() {
    abstract val dao: MovieDao  // property way of defining dao
//    abstract fun getDao(): MovieDao  // functional way of defining dao
}