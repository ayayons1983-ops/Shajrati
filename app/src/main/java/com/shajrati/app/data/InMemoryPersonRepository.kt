package com.shajrati.app.data

import com.shajrati.app.domain.common.DomainResult
import com.shajrati.app.domain.entities.Person
import com.shajrati.app.domain.repositories.PersonRepository
import java.util.concurrent.ConcurrentHashMap

class InMemoryPersonRepository : PersonRepository {
    private val storage = ConcurrentHashMap<String, Person>()

    override suspend fun save(person: Person): DomainResult<Unit, Throwable> = try {
        storage[person.id] = person
        DomainResult.Success(Unit)
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findById(id: String): DomainResult<Person?, Throwable> = try {
        DomainResult.Success(storage[id])
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findAll(): DomainResult<List<Person>, Throwable> = try {
        DomainResult.Success(storage.values.toList())
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun delete(id: String): DomainResult<Unit, Throwable> = try {
        storage.remove(id)
        DomainResult.Success(Unit)
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun exists(id: String): DomainResult<Boolean, Throwable> = try {
        DomainResult.Success(storage.containsKey(id))
    } catch (e: Exception) { DomainResult.Failure(e) }

    override suspend fun findByFullName(firstName: String, lastName: String?): DomainResult<List<Person>, Throwable> = try {
        val results = storage.values.filter {
            it.firstName.equals(firstName, ignoreCase = true) &&
            (lastName == null || it.lastName?.equals(lastName, ignoreCase = true) == true)
        }
        DomainResult.Success(results)
    } catch (e: Exception) { DomainResult.Failure(e) }

    fun clear() = storage.clear()
}
