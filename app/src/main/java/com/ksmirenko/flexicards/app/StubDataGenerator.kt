package com.ksmirenko.flexicards.app

import com.ksmirenko.flexicards.app.datatypes.Category

/**
 * Generates stub data for app testing.
 */
object StubDataGenerator {
    /*val categories = arrayOf(
            Category("Английский", "RU", emptyArray(), emptyArray()),
            Category("Испанский", "RU", emptyArray(), emptyArray()),
            Category("Spanish", "EN", emptyArray(), emptyArray())
    )*/
    /**
     * Generates and returns STUB categories.
     */
    fun getStubCategories() : List<Category> = listOf(
        Category(1, "Английский", "RU"),
        Category(2, "Испанский", "RU"),
        Category(3, "Немецкий", "RU"),
        Category(4, "Эльфийский", "RU"),
        Category(5, "Spanish", "EN"),
        Category(6, "German", "EN"),
        Category(7, "Russian", "EN"),
        Category(8, "Englisch", "DE"),
        Category(9, "Ingles", "ES"),
        //CategoryInfo("Inglés", "ES"),
        Category(10, "Ruso", "ES")
    )
}