package weerasinghe.deneth.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity
class MovieEntity(  // database entity! Parameters are the entity's keys, need those annotations
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var ratingId: String,  // foreign key for one-to-many relationship with RatingEntity: one rating can have multiple movies
)

// many to many relationship
// NOT an entity
data class MovieWithCast(
    @Embedded
    val movie: MovieEntity,

    @Relation(
        entity = RoleEntity::class,
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val rolesWithActors: List<RoleWithActor>
)

data class RoleWithActor(
    @Embedded
    val role: RoleEntity,

    @Relation(
        parentColumn = "actorId",
        entityColumn = "id"
    )
    val actor: ActorEntity
)