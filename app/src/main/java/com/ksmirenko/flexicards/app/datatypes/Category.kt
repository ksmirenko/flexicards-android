package com.ksmirenko.flexicards.app.datatypes

/**
 * A Category contains cards related to a specific subject or area of study.
 */
data class Category(
        /**
         * Category info.
         */
        var info : CategoryInfo,
        /**
         * Cards which the category contains; the dictionary of the category.
         */
        var cards : Array<Card>,
        /**
         * Modules which exist in the category.
         */
        var modules : Array<Module>
) {
    constructor(name : String, primaryLanguage : String, cards : Array<Card>, modules : Array<Module>) :
    this(CategoryInfo(name, primaryLanguage), cards, modules)
}