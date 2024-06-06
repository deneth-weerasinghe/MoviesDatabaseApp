package weerasinghe.deneth.repository.dto

import weerasinghe.deneth.data.entities.RatingEntity
import weerasinghe.deneth.data.entities.RatingWithMovies
import weerasinghe.deneth.repository.HasId

data class RatingDto(
    override val id: String,
    val name: String,
    val description: String,
): HasId

internal fun RatingEntity.toDto() =
    RatingDto(id = id, name = name, description = description)
internal fun RatingDto.toEntity() =
    RatingEntity(id = id, name = name, description = description)

data class RatingWithMovieDto(
    val rating: RatingDto,
    val movies: List<MovieDto>
)

internal fun RatingWithMovies.toDto() =
    RatingWithMovieDto(
        rating = rating.toDto(),
        movies = movies.map {
            it.toDto()
        }
    )