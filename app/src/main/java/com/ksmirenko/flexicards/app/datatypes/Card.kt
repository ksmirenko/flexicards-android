package com.ksmirenko.flexicards.app.datatypes

/**
 * A Card is a flash card with two sides.
 * This is how a Card is stored in the app's SQL database.
 *
 * @author Kirill Smirenko
 */
data class Card(
        /**
         * Unique card ID. Ignored when adding a Card to DB (DB defines id).
         */
        var id : Int,
        /**
         * ID of the category to which the card belongs.
         */
        var categoryId : Int,
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