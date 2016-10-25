package io.github.ksmirenko.flexicards

import io.github.ksmirenko.flexicards.datatypes.CardPack
import io.github.ksmirenko.flexicards.datatypes.Category

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
            Category(43, "Spanish", "EN")
    )

    val stubPacks : List<CardPack> = listOf(
            CardPack(
                    "Английский",
                    "RU",
                    "Цвета",
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
                    "Животные",
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
                            Pair("usted", "Вы (ед., вежливое)"),
                            Pair("nosotros", "мы"),
                            Pair("vosotros", "вы (мн.)"),
                            Pair("ellos", "они"),
                            Pair("ustedes", "Вы (мн., вежливое)")
                    )

            ),
            CardPack(
                    "Spanish",
                    "EN",
                    "Pronouns",
                    listOf(
                            Pair("yo", "I"),
                            Pair("tú", "you (single, informal)"),
                            Pair("él", "he"),
                            Pair("ella", "she"),
                            Pair("usted", "you (single, formal)"),
                            Pair("nosotros", "we"),
                            Pair("vosotros", "you (plural, informal)"),
                            Pair("ellos", "they"),
                            Pair("ustedes", "you (plural, formal)")
                    )

            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Внешность 1",
                    listOf(
                            "attractive" to "привлекательный",
                            "good-looking" to "хорошо выглядящий",
                            "beautiful" to "красивая",
                            "handsome" to "красивый",
                            "pretty" to "симпатичный",
                            "cute" to "симпатичный",
                            "nice" to "приятный",
                            "plain-looking" to "выглядящий просто, непривлекательно",
                            "unattractive" to "непривлекательный",
                            "ugly" to "уродливый"
                    )
            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Внешность 2",
                    listOf(
                            "well-dressed" to "хорошо одетый",
                            "nicely dressed" to "приятно одетый",
                            "casually dressed" to "неброско одетый",
                            "poorly dressed" to "плохо одетый",
                            "neat" to "аккуратный",
                            "clean" to "чистый",
                            "untidy" to "неопрятный",
                            "dirty" to "грязный",
                            "scruffy" to "неряшливый",
                            "messy" to "неряшливый",
                            "stinky" to "вонючий"
                    )
            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Волосы 1",
                    listOf(
                            "dark" to "темный",
                            "fair" to "русый",
                            "black" to "брюнет",
                            "red" to "рыжий",
                            "brown" to "шатен",
                            "blond" to "блондин",
                            "chestnut brown" to "каштановый",
                            "white, gray" to "седой",
                            "long" to "длинный",
                            "short" to "короткий",
                            "medium-length" to "средний длины",
                            "shoulder-length" to "длиной до плеч"
                    )
            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Волосы 2",
                    listOf(
                            "straight" to "прямой",
                            "curly" to "кучерявый",
                            "wavy" to "волнистый",
                            "thick" to "густой",
                            "thin" to "редкий",
                            "bald" to "лысый",
                            "shiny" to "лоснящийся",
                            "smooth" to "гладкий",
                            "neatly combed" to "аккуратно причесанный",
                            "disheveled" to "растрепанный",
                            "tousled" to "взлохмаченный"
                    )
            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Чувства 1",
                    listOf(
                            "hot" to "жарко",
                            "thirsty" to "хотеть пить",
                            "sleepy" to "хотеть спать",
                            "cold" to "холодно",
                            "hungry" to "голоден",
                            "full" to "сыт",
                            "comfortable" to "удобно",
                            "uncomfortable" to "неудобно",
                            "disgusted" to "испытывающий отвращение",
                            "calm" to "спокойный",
                            "nervous" to "нервный",
                            "in pain" to "испытывающий боль"
                    )
            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Чувства 2",
                    listOf(
                            "worried" to "обеспокоенный",
                            "sick" to "больной",
                            "well" to "хорошо",
                            "relieved" to "легче",
                            "hurt" to "обиженный",
                            "lonely" to "одинокий",
                            "in love" to "влюбленный",
                            "sad" to "грустный",
                            "homesick" to "чувство ностальгии/тоски по родине (дому)",
                            "proud" to "гордый",
                            "excited" to "взволнованный",
                            "scared" to "испуганный"
                    )
            ),
            CardPack(
                    "Английский",
                    "RU",
                    "Чувства 3",
                    listOf(
                            "embarrassed" to "испытывающий неловкость",
                            "bored" to "скучающий",
                            "confused" to "смущенный",
                            "frustrated" to "расстроенный",
                            "angry" to "сердитый",
                            "upset" to "огорченный",
                            "surprised" to "удивленный",
                            "happy" to "счастливый",
                            "tired" to "усталый"
                    )
            ),
            CardPack(
                    "Испанский",
                    "RU",
                    "Дни недели",
                    listOf(
                            "lunes" to "понедельник",
                            "martes" to "вторник",
                            "miércoles" to "среда",
                            "jueves" to "четверг",
                            "viernes" to "пятница",
                            "sábado" to "суббота",
                            "domingo" to "воскресенье"
                    )
            ),
            CardPack(
                    "Испанский",
                    "RU",
                    "Одежда 1",
                    listOf(
                            "camisa" to "рубашка",
                            "camiseta" to "футболка",
                            "traje" to "костюм",
                            "vestido" to "платье",
                            "falda" to "юбка",
                            "blusa" to "кофта",
                            "vaqueros" to "джинсы",
                            "chaqueta" to "куртка/пиджак"
                    )
            ),
            CardPack(
                    "Испанский",
                    "RU",
                    "Одежда 2",
                    listOf(
                            "casadora" to "пуховик",
                            "guantes" to "перчатки",
                            "zapatos" to "туфли/ботинки",
                            "pantalones cortos" to "шорты",
                            "chándal" to "спортивный костюм",
                            "esquistos" to "сланцы",
                            "visera" to "кепка",
                            "calcetines" to "носки"
                    )
            )
    )

    fun fillDatabaseIfEmptyOrOutdated(dbmanager : DatabaseManager) {
        if (dbmanager.isCategoriesEmpty()) {
            addAllCategories(dbmanager)
        }
        if (dbmanager.isCardsEmpty()) {
            addAllPacks(dbmanager)
        }
        else if (dbmanager.findCard("calcetines")) { // workaround
            // DB is outdated, should reset, re-add categories and add all cards
            dbmanager.resetAll()
            addAllCategories(dbmanager)
            addAllPacks(dbmanager)
        }
    }

    private fun addAllCategories(dbmanager : DatabaseManager) {
        stubCategories.forEach { cat -> dbmanager.createCategory(cat) }
    }

    private fun addAllPacks(dbmanager : DatabaseManager) {
        stubPacks.forEach { pack -> dbmanager.insertCardPack(pack, false) }
    }
}