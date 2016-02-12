package com.ksmirenko.flexicards.app.datatypes

/**
 * Pack of cards to be added to DB that can be
 * parsed from a file or downloaded from some server.
 *
 * @author Kirill Smirenko
 */
data class CardPack(
        /**
         * Category name. May not be preset, but is required for inserting the pack into the DB.
         */
        var categoryName : String?,
        /**
         * Module name (not required).
         */
        var moduleName : String?,
        /**
         * Cards' content.
         */
        var cardList : List<Pair<String, String>>
) {
}