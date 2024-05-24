package weerasinghe.deneth.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity
class RatingEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String
)

// one-to-many relationship
// NOT an entity
data class RatingWithMovies(
    @Embedded
    val rating: RatingEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "ratingId"
    )
    val movies: List<MovieEntity>  // no extra data class needed because no extra association needed
)