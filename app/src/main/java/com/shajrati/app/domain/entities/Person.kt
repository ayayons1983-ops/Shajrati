package com.shajrati.app.domain.entities

import com.shajrati.app.domain.common.DomainResult
import com.shajrati.app.domain.common.FamilyConfig
import com.shajrati.app.domain.errors.PersonError
import java.util.Calendar
import java.util.UUID

data class Person private constructor(
    val id: String,
    val firstName: String,
    val lastName: String?,
    val birthYear: Int?,
    val deathYear: Int?,
    val createdAt: Long,
    val updatedAt: Long
) {
    val fullName: String
        get() = buildString {
            append(firstName)
            lastName?.let { append(" ").append(it) }
        }.trim()

    val isAlive: Boolean get() = deathYear == null

    val currentAge: Int?
        get() = if (birthYear != null && deathYear == null)
            Calendar.getInstance().get(Calendar.YEAR) - birthYear
        else null

    val ageAtDeath: Int?
        get() = if (birthYear != null && deathYear != null)
            deathYear - birthYear
        else null

    fun update(
        firstName: String? = null,
        lastName: String? = null,
        birthYear: Int? = null,
        deathYear: Int? = null
    ): DomainResult<Person, PersonError> = create(
        id = this.id,
        firstName = firstName ?: this.firstName,
        lastName = lastName ?: this.lastName,
        birthYear = birthYear ?: this.birthYear,
        deathYear = deathYear ?: this.deathYear,
        createdAt = this.createdAt
    )

    companion object {
        private val NAME_REGEX = Regex("^[\\p{L}\\p{N} ]+$")

        fun create(
            id: String = UUID.randomUUID().toString(),
            firstName: String,
            lastName: String? = null,
            birthYear: Int? = null,
            deathYear: Int? = null,
            createdAt: Long = System.currentTimeMillis()
        ): DomainResult<Person, PersonError> {
            val validId = try {
                UUID.fromString(id).toString()
            } catch (e: IllegalArgumentException) {
                return DomainResult.Failure(PersonError.InvalidUUID(id))
            }

            val trimmedFirstName = firstName.trim()
            when {
                trimmedFirstName.isBlank() -> return DomainResult.Failure(PersonError.EmptyFirstName)
                trimmedFirstName.length < FamilyConfig.MIN_NAME_LENGTH -> return DomainResult.Failure(PersonError.FirstNameTooShort)
                trimmedFirstName.length > FamilyConfig.MAX_NAME_LENGTH -> return DomainResult.Failure(PersonError.FirstNameTooLong)
                !NAME_REGEX.matches(trimmedFirstName) -> return DomainResult.Failure(PersonError.InvalidChars(trimmedFirstName))
            }

            val trimmedLastName = lastName?.trim()
            if (trimmedLastName != null) {
                when {
                    trimmedLastName.length > FamilyConfig.MAX_NAME_LENGTH -> return DomainResult.Failure(PersonError.LastNameTooLong)
                    !NAME_REGEX.matches(trimmedLastName) -> return DomainResult.Failure(PersonError.InvalidChars(trimmedLastName))
                }
            }

            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            if (birthYear != null && (birthYear < FamilyConfig.MIN_YEAR || birthYear > currentYear))
                return DomainResult.Failure(PersonError.BirthYearOutOfRange)

            if (deathYear != null) {
                if (deathYear > currentYear) return DomainResult.Failure(PersonError.DeathYearInFuture)
                if (birthYear != null && deathYear < birthYear) return DomainResult.Failure(PersonError.DeathYearBeforeBirth)
            }

            return DomainResult.Success(Person(
                id = validId,
                firstName = trimmedFirstName,
                lastName = trimmedLastName,
                birthYear = birthYear,
                deathYear = deathYear,
                createdAt = createdAt,
                updatedAt = System.currentTimeMillis()
            ))
        }
    }
}
