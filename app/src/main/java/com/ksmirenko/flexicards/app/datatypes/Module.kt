package com.ksmirenko.flexicards.app.datatypes

/**
 * A Module is a holistic pack of cards of one category.
 */
data class Module(
        /**
         * Module name.
         */
        var name : String,
        /**
         * IDs of cards which are packed in the module.
         */
        var cardIds : Array<Int>
) {
}