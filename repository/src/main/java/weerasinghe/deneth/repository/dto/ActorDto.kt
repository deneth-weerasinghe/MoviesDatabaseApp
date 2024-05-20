package weerasinghe.deneth.repository.dto

import weerasinghe.deneth.data.entities.ActorEntity

data class ActorDto(
    val id: String,
    val name: String,
)

internal fun ActorEntity.toDto() =
    ActorDto(id = id, name = name)
internal fun ActorDto.toEntity() =
    ActorEntity(id = id, name = name)
