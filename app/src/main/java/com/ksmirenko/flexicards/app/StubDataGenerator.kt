package com.ksmirenko.flexicards.app

import com.ksmirenko.flexicards.app.datatypes.Category

/**
 * Generates stub data for app testing.
 *
 * @author Kirill Smirenko
 */
object StubDataGenerator {
    /**
     * Generates and returns stub categories.
     */
    public val stubCategories : List<Category> = listOf(
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

    public fun fillDatabaseWithCategories(dbmanager : DatabaseManager) {
        dbmanager.clearDatabase()
        stubCategories.forEach { cat -> dbmanager.createCategory(cat) }
    }
}