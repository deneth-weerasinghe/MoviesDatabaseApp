package weerasinghe.deneth.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
class RatingEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String
)