package weerasinghe.deneth.repository.dto

import weerasinghe.deneth.data.entities.RatingEntity

data class RatingDto(
    val id: String,
    val name: String,
    val description: String,
)

internal fun RatingEntity.toDto() =
    RatingDto(id = id, name = name, description = description)
internal fun RatingDto.toEntity() =
    RatingEntity(id = id, name = name, description = description)