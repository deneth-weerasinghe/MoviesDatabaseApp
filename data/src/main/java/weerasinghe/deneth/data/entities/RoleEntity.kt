package weerasinghe.deneth.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

// Associative entity for many-to-many relationship between MovieEntity and ActorEntity

@Entity(
    primaryKeys = ["actorId", "movieId"],  // needed for associative entities to create unique id
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ActorEntity::class,
            parentColumns = ["id"],
            childColumns = ["actorId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),  // if either movie or actor deleted, delete the role
    ]
)
data class RoleEntity(
    var movieId: String,
    var actorId: String,
    var character: String,  // associative relationship extra data, likewise with below
    var orderInCredits: Int,
)