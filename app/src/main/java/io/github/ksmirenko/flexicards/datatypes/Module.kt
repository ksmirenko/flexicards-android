package io.github.ksmirenko.flexicards.datatypes

/**
 * A Module is a holistic pack of cards of one category.
 * This is how a Module is stored in the app's SQL database.
 *
 * @author Kirill Smirenko
 */
data class Module(
        /**
         * Unique module ID. Ignored when adding a Module to DB (DB defines id).
         */
        var id : Int,
        /**
         * ID of the category to which the card belongs.
         */
        var categoryId : Int,
        /**
         * Module name.
         */
        var name : String,
        /**
         * IDs of cards which are packed in the module.
         */
        var cards : List<Int>
) {
}