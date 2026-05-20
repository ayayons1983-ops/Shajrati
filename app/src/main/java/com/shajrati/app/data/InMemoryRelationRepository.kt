package com.shajrati.app.data

import com.shajrati.app.domain.common.DomainResult
import com.shajrati.app.domain.entities.Relation
import com.shajrati.app.domain.entities.RelationType
import com.shajrati.app.domain.repositories.RelationRepository
import java.util.concurrent.ConcurrentHashMap

class InMemoryRelationRepository : RelationRepository {
    private val storage = ConcurrentHashMap<String, Relation>()

    override suspend fun save(relation: Relation): DomainResult<Unit, Throwable> = try {
        storage[relation.id] = relation
        DomainResult.Success(Unit)
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findById(id: String): DomainResult<Relation?, Throwable> = try {
        DomainResult.Success(storage[id])
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun delete(id: String): DomainResult<Unit, Throwable> = try {
        storage.remove(id)
        DomainResult.Success(Unit)
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findByPersonId(personId: String): DomainResult<List<Relation>, Throwable> = try {
        DomainResult.Success(storage.values.filter {
            it.personId == personId || it.relatedPersonId == personId
        })
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findAsParent(personId: String): DomainResult<List<Relation>, Throwable> = try {
        DomainResult.Success(storage.values.filter {
            it.personId == personId && it.type == RelationType.PARENT_OF
        })
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findAsChild(personId: String): DomainResult<List<Relation>, Throwable> = try {
        DomainResult.Success(storage.values.filter {
            it.relatedPersonId == personId && it.type == RelationType.PARENT_OF
        })
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findByType(personId: String, type: RelationType): DomainResult<List<Relation>, Throwable> = try {
        DomainResult.Success(storage.values.filter {
            it.personId == personId && it.type == type
        })
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun exists(personId: String, relatedPersonId: String, type: RelationType): DomainResult<Boolean, Throwable> = try {
        val exists = storage.values.any {
            it.type == type &&
            ((it.personId == personId && it.relatedPersonId == relatedPersonId) ||
             (type == RelationType.SPOUSE_OF && it.personId == relatedPersonId && it.relatedPersonId == personId))
        }
        DomainResult.Success(exists)
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun deleteByPersonId(personId: String): DomainResult<Unit, Throwable> = try {
        storage.values.filter {
            it.personId == personId || it.relatedPersonId == personId
        }.forEach { storage.remove(it.id) }
        DomainResult.Success(Unit)
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findAll(): DomainResult<List<Relation>, Throwable> = try {
        DomainResult.Success(storage.values.toList())
    } catch (e: Exception) { DomainResult.Failure(e) }

    fun clear() = storage.clear()
}
