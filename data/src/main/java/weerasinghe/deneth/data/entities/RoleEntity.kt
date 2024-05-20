package weerasinghe.deneth.data.entities

import androidx.room.Entity

// Associative entity for many-to-many relationship between MovieEntity and ActorEntity

@Entity(
    primaryKeys = ["actorId", "movieId"]  // needed for associative entities to create unique id
)
class RoleEntity(
    var movieID: String,
    var actorID: String,
    var character: String,  // associative relationship extra data, likewise with below
    var orderInCredits: Int,
)