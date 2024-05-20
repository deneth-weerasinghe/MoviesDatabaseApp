package weerasinghe.deneth.repository

import weerasinghe.deneth.data.RatingEntity

data class RatingDto(
    val id: String,
    val name: String,
    val description: String,
)

internal fun RatingEntity.toDto() =
    RatingDto(id = id, name = name, description = description)
internal fun RatingDto.toEntity() =
    RatingEntity(id = id, name = name, description = description)
