package com.shajrati.app.domain.entities

import com.shajrati.app.domain.common.DomainResult
import com.shajrati.app.domain.errors.RelationError
import java.util.UUID

enum class RelationType {
    PARENT_OF,
    SPOUSE_OF;

    val isSymmetric: Boolean get() = this == SPOUSE_OF
}

data class Relation private constructor(
    val id: String,
    val personId: String,
    val relatedPersonId: String,
    val type: RelationType,
    val createdAt: Long
) {
    fun isSameAs(other: Relation): Boolean {
        return type == other.type &&
               ((personId == other.personId && relatedPersonId == other.relatedPersonId) ||
                (type == RelationType.SPOUSE_OF &&
                 personId == other.relatedPersonId &&
                 relatedPersonId == other.personId))
    }

    companion object {
        fun create(
            id: String = UUID.randomUUID().toString(),
            personId: String,
            relatedPersonId: String,
            type: RelationType,
            createdAt: Long = System.currentTimeMillis()
        ): DomainResult<Relation, RelationError> {
            if (personId == relatedPersonId)
                return DomainResult.Failure(RelationError.SamePerson)

            return DomainResult.Success(Relation(
                id = id,
                personId = personId,
                relatedPersonId = relatedPersonId,
                type = type,
                createdAt = createdAt
            ))
        }
    }
}
