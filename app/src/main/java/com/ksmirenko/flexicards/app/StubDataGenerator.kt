package com.ksmirenko.flexicards.app

import com.ksmirenko.flexicards.app.datatypes.CardPack
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

    public val stubPacks : List<CardPack> = listOf(
            CardPack(
                    "Английский",
                    "RU",
                    "Colours",
                    listOf(
                            Pair("red", "красный"),
                            Pair("green", "зелёный"),
                            Pair("orange", "оранжевый"),
                            Pair("blue", "синий"),
                            Pair("yellow", "жёлтый")
                    )

            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Animals",
                    listOf(
                            Pair("bear", "медведь"),
                            Pair("dog", "собака"),
                            Pair("bird", "птица"),
                            Pair("frog", "лягушка"),
                            Pair("mouse", "мышь"),
                            Pair("cat", "кошка")
                    )

            )
    )

    public fun fillDatabaseWithCategories(dbmanager : DatabaseManager) {
        dbmanager.reset()
        stubCategories.forEach { cat -> dbmanager.createCategory(cat) }
    }

    public fun fillDatabaseWithStubPacks(dbmanager : DatabaseManager) {
        stubPacks.forEach { pack -> dbmanager.insertCardPack(pack, false) }
    }
}