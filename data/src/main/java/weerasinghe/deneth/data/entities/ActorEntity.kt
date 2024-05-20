package weerasinghe.deneth.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
class ActorEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String,
)