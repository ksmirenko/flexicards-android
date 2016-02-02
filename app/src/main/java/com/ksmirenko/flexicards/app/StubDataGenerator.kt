package com.ksmirenko.flexicards.app

import com.ksmirenko.flexicards.app.datatypes.Category
import com.ksmirenko.flexicards.app.datatypes.CategoryInfo

/**
 * Generates stub data for app testing.
 */
object StubDataGenerator {
    val categories = arrayOf(
            Category("Английский", "RU", emptyArray(), emptyArray()),
            Category("Испанский", "RU", emptyArray(), emptyArray()),
            Category("Spanish", "EN", emptyArray(), emptyArray())
    )
    /**
     * Generates and returns STUB categories.
     */
    fun getStubCategoryInfos() : Array<CategoryInfo> = arrayOf(
        CategoryInfo("Английский", "RU"),
        CategoryInfo("Испанский", "RU"),
        CategoryInfo("Немецкий", "RU"),
        CategoryInfo("Петуханский", "RU"),
        CategoryInfo("Spanish", "EN"),
        CategoryInfo("German", "EN"),
        CategoryInfo("Russian", "EN"),
        CategoryInfo("Englisch", "DE"),
        CategoryInfo("Ingles", "ES"),
        //CategoryInfo("Inglés", "ES"),
        CategoryInfo("Ruso", "ES")
    )
}