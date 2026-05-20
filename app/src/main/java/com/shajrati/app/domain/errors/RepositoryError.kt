package com.shajrati.app.domain.errors

sealed class RepositoryError(val message: String) {
    data class NotFound(val id: String) : RepositoryError("العنصر غير موجود: $id")
    data class Duplicate(val id: String) : RepositoryError("العنصر مكرر: $id")
    data class DatabaseError(val cause: Throwable) : RepositoryError("خطأ في قاعدة البيانات: ${cause.message}")
    data class ValidationError(val reason: String) : RepositoryError("خطأ في التحقق: $reason")
    data object Unknown : RepositoryError("خطأ غير معروف")
}
