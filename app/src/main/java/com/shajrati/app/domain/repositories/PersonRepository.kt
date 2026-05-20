package com.shajrati.app.domain.repositories

import com.shajrati.app.domain.common.DomainResult
import com.shajrati.app.domain.entities.Person

interface PersonRepository {
    suspend fun save(person: Person): DomainResult<Unit, Throwable>
    suspend fun findById(id: String): DomainResult<Person?, Throwable>
    suspend fun findAll(): DomainResult<List<Person>, Throwable>
    suspend fun delete(id: String): DomainResult<Unit, Throwable>
    suspend fun exists(id: String): DomainResult<Boolean, Throwable>
    suspend fun findByFullName(firstName: String, lastName: String?): DomainResult<List<Person>, Throwable>
}
