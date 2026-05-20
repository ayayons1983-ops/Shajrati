package com.shajrati.app.domain.errors

import com.shajrati.app.domain.common.FamilyConfig

sealed class PersonError(val message: String) {
    data object EmptyFirstName : PersonError("الاسم الأول لا يمكن أن يكون فارغاً")
    data object FirstNameTooShort : PersonError("الاسم الأول قصير جداً")
    data object FirstNameTooLong : PersonError("الاسم الأول طويل جداً")
    data object LastNameTooLong : PersonError("اسم العائلة طويل جداً")
    data class InvalidChars(val input: String) : PersonError("الاسم يحتوي على رموز غير مسموحة")
    data object BirthYearOutOfRange : PersonError("سنة الميلاد غير صالحة")
    data object DeathYearBeforeBirth : PersonError("سنة الوفاة لا يمكن أن تسبق سنة الميلاد")
    data object DeathYearInFuture : PersonError("سنة الوفاة لا يمكن أن تكون في المستقبل")
    data class InvalidUUID(val id: String) : PersonError("معرف غير صالح: $id")
    data object PersonNotFound : PersonError("الشخص غير موجود")
    data object PersonAlreadyExists : PersonError("الشخص موجود مسبقاً")
    data object NotFound : PersonError("الشخص غير موجود")
    data object ParentNotFound : PersonError("الوالد غير موجود")
    data object InvalidNameFormat : PersonError("صيغة الاسم غير صالحة")
    data class ValidationFailed(val reason: String) : PersonError("فشل التحقق: $reason")
    data class RepositoryError(val reason: String) : PersonError("خطأ في المستودع: $reason")
    data object RelationCreationFailed : PersonError("فشل إنشاء العلاقة")
    data object UnknownError : PersonError("خطأ غير معروف")
    data class DuplicateNameWithSuggestion(val suggestedName: String) : PersonError("الاسم مكرر، الاقتراح: $suggestedName")
    data class HasChildren(val count: Int) : PersonError("لا يمكن حذف الشخص لديه $count من الأبناء")
    data class MaxParentsReached(val max: Int) : PersonError("تم تجاوز الحد الأقصى للوالدين ($max)")
}
