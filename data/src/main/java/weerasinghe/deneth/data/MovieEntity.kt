package weerasinghe.deneth.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
class MovieEntity(  // database entity! Parameters are the entity's keys, need those annotations
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var ratingId: String,  // foreign key for one-to-many relationship with RatingEntity: one rating can have multiple movies
)