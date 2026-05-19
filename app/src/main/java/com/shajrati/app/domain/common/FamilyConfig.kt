package com.shajrati.app.domain.common

data class FamilyConfig(
    val allowPolygamy: Boolean = false,
    val allowMultipleGuardians: Boolean = false,
    val strictAgeValidation: Boolean = true,
    val maxParents: Int = 2
) {
    companion object {
        const val MAX_TREE_DEPTH = 20
        const val MIN_YEAR = 1800
        const val MIN_PARENT_AGE_DIFFERENCE = 13
        const val WARNING_PARENT_AGE_DIFFERENCE = 18
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 50

        fun default() = FamilyConfig()
    }
}
