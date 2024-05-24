package weerasinghe.deneth.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity
class ActorEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String,
)

// many-to-many relationship
// NOT an entity
data class ActorWithFilmography(
    @Embedded
    val actor: ActorEntity,

    @Relation(
        entity = RoleEntity::class,
        parentColumn = "id",
        entityColumn = "actorId"
    )
    val rolesWithMovies: List<RoleWithMovie>
)

data class RoleWithMovie(
    @Embedded
    val role: RoleEntity,

    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: MovieEntity
)