package com.ksmirenko.flexicards.app.datatypes

/**
 * A Card is a flash card with two sides.
 */
data class Card(
        /**
         * An ID which must be unique within a category.
         */
        var id : Int,
        /**
         * Front side content ("word").
         */
        var frontContent : String,
        /**
         * Back side content ("translation"). Must be in the category's primary language.
         */
        var backContent : String
) {
}