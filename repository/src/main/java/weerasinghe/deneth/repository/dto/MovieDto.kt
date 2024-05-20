package weerasinghe.deneth.repository.dto

import weerasinghe.deneth.data.entities.MovieEntity

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

