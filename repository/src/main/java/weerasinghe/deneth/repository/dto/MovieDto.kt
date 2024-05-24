package weerasinghe.deneth.repository.dto

import weerasinghe.deneth.data.entities.MovieEntity
import weerasinghe.deneth.data.entities.MovieWithCast
import weerasinghe.deneth.data.entities.RoleWithActor

data class MovieDto(
    val id: String,
    val title: String,
    val description: String,
    var ratingId: String,
)

// we need to translate between Dto and Entity:
internal fun MovieEntity.toDto() = // extension function
    MovieDto(id = id, title = title, description = description, ratingId = ratingId)
internal fun MovieDto.toEntity() =
    MovieEntity(id = id, title = title, description = description, ratingId = ratingId)

data class MovieWithCastDto(
    val movie: MovieDto,
    val cast: List<RoleWithActorDto>
)

data class RoleWithActorDto(
    val actor: ActorDto,
    val character: String,
    val orderInCredits: Int
)

internal fun RoleWithActor.toDto() =
    RoleWithActorDto(
        actor = actor.toDto(),
        character = role.character,
        orderInCredits = role.orderInCredits
    )

internal fun MovieWithCast.toDto() =
    MovieWithCastDto(
        movie = movie.toDto(),
        cast = rolesWithActors.map {
            it.toDto()
        }
    )