package com.ksmirenko.flexicards.app.datatypes

/**
 * A CategoryInfo describes a category and is shown on the category select screen.
 */
data class CategoryInfo(
        /**
         * Category name.
         */
        var name : String,
        /**
         * Primary language of the category. Presumably it's the speaking language of the category user
         * (the language for the user to translate vocabulary into or to learn terms in)
         */
        var primaryLanguage : String
) {
}