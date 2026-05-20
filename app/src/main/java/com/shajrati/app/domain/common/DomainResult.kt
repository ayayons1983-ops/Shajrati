package com.shajrati.app.domain.common

sealed class DomainResult<out T, out E> {
    data class Success<T>(val data: T) : DomainResult<T, Nothing>()
    data class Failure<E>(val error: E) : DomainResult<Nothing, E>()
}

fun <T, E> DomainResult<T, E>.getOrNull(): T? = when (this) {
    is DomainResult.Success -> data
    is DomainResult.Failure -> null
}

fun <T, E> DomainResult<T, E>.isSuccess(): Boolean = this is DomainResult.Success
fun <T, E> DomainResult<T, E>.isFailure(): Boolean = this is DomainResult.Failure
