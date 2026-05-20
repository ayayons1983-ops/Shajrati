package com.shajrati.app.domain.errors

sealed class RelationError(val message: String) {
    data object SamePerson : RelationError("لا يمكن إنشاء علاقة بين الشخص ونفسه")
    data object PersonNotFound : RelationError("أحد الأطراف غير موجود")
    data object DuplicateRelation : RelationError("العلاقة موجودة مسبقاً")
    data object SpouseReverseExists : RelationError("العلاقة العكسية موجودة")
    data class SpouseAlreadyExists(val personId: String) : RelationError("الشخص $personId لديه زوج/زوجة بالفعل")
    data object MaxParentsReached : RelationError("تم تجاوز الحد الأقصى للوالدين")
    data object CycleDetected : RelationError("إضافة هذه العلاقة ستخلق حلقة في شجرة العائلة")
    data object ParentTooYoung : RelationError("الوالد صغير جداً")
    data object InvalidRelation : RelationError("علاقة غير صالحة")
    data object RepositoryError : RelationError("خطأ في المستودع")
    data object NotFound : RelationError("العلاقة غير موجودة")
    data object ParentDiedBeforeChildBirth : RelationError("الوالد توفي قبل ولادة الطفل")
    data class ParentAgeWarning(val warning: String) : RelationError("تحذير: $warning")
}
