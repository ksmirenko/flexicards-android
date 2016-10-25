package io.github.ksmirenko.flexicards.datatypes

/**
 * A CategoryInfo describes a category and is shown on the category select screen.
 * This is how a Category is stored in the app's SQL database.
 *
 * @author Kirill Smirenko
 */
data class Category(
        /**
         * Unique category ID. Ignored when adding a Category to DB (DB defines id).
         */
        var id : Int,
        /**
         * Category name.
         */
        var name : String,
        /**
         * Primary language of the category. Presumably it's the speaking language of the category user
         * (the language for the user to translate vocabulary into or to learn terms in)
         */
        var language : String
) {
}