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
    val stubCategories : List<Category> = listOf(
            Category(41, "Английский", "RU"),
            Category(42, "Испанский", "RU"),
            Category(43, "Spanish", "EN"),
            Category(44, "Long Content. Very long content and very long category name", "EN")
    )

    val stubPacks : List<CardPack> = listOf(
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

            ),
            CardPack(
                    "Испанский",
                    "RU",
                    "Местоимения",
                    listOf(
                            Pair("yo", "я"),
                            Pair("tú", "ты"),
                            Pair("él", "он"),
                            Pair("ella", "она"),
                            Pair("usded", "Вы (ед., вежливое)"),
                            Pair("nosotros", "мы"),
                            Pair("vosotros", "вы (мн.)"),
                            Pair("ellos", "они"),
                            Pair("ustedes", "Вы (мн., вежливое)")
                    )

            ),
            CardPack(
                    "Spanish",
                    "EN",
                    "Pronombres",
                    listOf(
                            Pair("yo", "I"),
                            Pair("tú", "you (single, informal)"),
                            Pair("él", "he"),
                            Pair("ella", "she"),
                            Pair("usded", "you (single, formal)"),
                            Pair("nosotros", "we"),
                            Pair("vosotros", "you (plural, informal)"),
                            Pair("ellos", "they"),
                            Pair("ustedes", "you (plural, formal)")
                    )

            ),
            CardPack(
                    "Long Content. Very long content and very long category name",
                    "EN",
                    "Sample very-very-very-very-very-very long module name",
                    listOf(
//                            Pair("front content", "back content"),
//                            Pair("longer, but not too long front content", "back content"),
//                            Pair("front content", "longer, but not too long back content"),
//                            Pair("longer, but not too long front content", "longer, but not too long back content"),
//                            Pair("Incredibly long front content: bla-bla-bla-bla-bla-bla-bla-bla-bla-bla-bla-bla stop",
//                                    "longer, but not too long back content"),
//                            Pair("longer, but not too long front content",
//                                    "Incredibly long back content: bla-bla-bla-bla-bla-bla-bla-bla-bla-bla-bla-bla stop"),
                            Pair("FRONT CONTENT 2 front content FRONT CONTENT front content FRONT CONTENT front content " +
                                    "FRONT CONTENT front content FRONT CONTENT front content FRONT CONTENT front content !",
                                    "BACK CONTENT 2 back content BACK CONTENT back content BACK CONTENT back content " +
                                    "BACK CONTENT back content BACK CONTENT back content BACK CONTENT back content " +
                                    "BACK CONTENT back content BACK CONTENT back content BACK CONTENT back content !"),
                            Pair("FRONT CONTENT front content FRONT CONTENT front content FRONT CONTENT front content " +
                                    "FRONT CONTENT front content FRONT CONTENT front content FRONT CONTENT front content !",
                                    "BACK CONTENT back content BACK CONTENT back content BACK CONTENT back content " +
                                    "BACK CONTENT back content BACK CONTENT back content BACK CONTENT back content " +
                                    "BACK CONTENT back content BACK CONTENT back content BACK CONTENT back content !")
                    )

            )
    )

    fun fillDatabaseWithCategories(dbmanager : DatabaseManager) {
        dbmanager.reset()
        stubCategories.forEach { cat -> dbmanager.createCategory(cat) }
    }

    fun fillDatabaseWithStubPacks(dbmanager : DatabaseManager) {
        stubPacks.forEach { pack -> dbmanager.insertCardPack(pack, false) }
    }
}