package com.shajrati.app.domain.repositories

import com.shajrati.app.domain.common.DomainResult
import com.shajrati.app.domain.entities.Person
import com.shajrati.app.domain.entities.Relation
import com.shajrati.app.domain.entities.RelationType

interface RelationRepository {
    suspend fun save(relation: Relation): DomainResult<Unit, Throwable>
    suspend fun findById(id: String): DomainResult<Relation?, Throwable>
    suspend fun delete(id: String): DomainResult<Unit, Throwable>
    suspend fun findByPersonId(personId: String): DomainResult<List<Relation>, Throwable>
    suspend fun findAsParent(personId: String): DomainResult<List<Relation>, Throwable>
    suspend fun findAsChild(personId: String): DomainResult<List<Relation>, Throwable>
    suspend fun findByType(personId: String, type: RelationType): DomainResult<List<Relation>, Throwable>
    suspend fun exists(personId: String, relatedPersonId: String, type: RelationType): DomainResult<Boolean, Throwable>
    suspend fun deleteByPersonId(personId: String): DomainResult<Unit, Throwable>
    suspend fun findAll(): DomainResult<List<Relation>, Throwable>
}
