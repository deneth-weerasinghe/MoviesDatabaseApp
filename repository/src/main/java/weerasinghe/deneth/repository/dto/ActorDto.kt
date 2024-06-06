package weerasinghe.deneth.repository.dto

import weerasinghe.deneth.data.entities.ActorEntity
import weerasinghe.deneth.data.entities.ActorWithFilmography
import weerasinghe.deneth.data.entities.RoleWithMovie
import weerasinghe.deneth.repository.HasId

data class ActorDto(
    override val id: String,
    val name: String,
): HasId

internal fun ActorEntity.toDto() =
    ActorDto(id = id, name = name)
internal fun ActorDto.toEntity() =
    ActorEntity(id = id, name = name)

data class ActorWithFilmographyDto(
    val actor: ActorDto,
    val filmography: List<RoleWithMovieDto>
)
data class RoleWithMovieDto(
    val movie: MovieDto,
    val character: String,
    val orderInCredits: Int
)

internal fun RoleWithMovie.toDto() =
    RoleWithMovieDto(
        movie = movie.toDto(),
        character = role.character,
        orderInCredits = role.orderInCredits
    )

internal fun ActorWithFilmography.toDto() =
    ActorWithFilmographyDto(
        actor = actor.toDto(),
        filmography = rolesWithMovies.map {
            it.toDto()
        }
    )