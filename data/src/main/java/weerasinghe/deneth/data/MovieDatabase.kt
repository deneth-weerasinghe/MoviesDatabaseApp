package weerasinghe.deneth.data

import androidx.room.Database
import androidx.room.RoomDatabase

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