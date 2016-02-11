package com.ksmirenko.flexicards.app.datatypes

/**
 * A Module is a holistic pack of cards of one category.
 *
 * @author Kirill Smirenko
 */
data class Module(
        /**
         * Unique module ID.
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