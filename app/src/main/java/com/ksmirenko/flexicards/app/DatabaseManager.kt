package com.ksmirenko.flexicards.app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.ksmirenko.flexicards.app.datatypes.*
import com.ksmirenko.flexicards.app.Utils
import java.util.*

/**
 * Application's SQL database manager.
 *
 * @author Kirill Smirenko
 */
object DatabaseManager :
        SQLiteOpenHelper(MainActivity.getAppContext(), DatabaseManager.DATABASE_NAME, null, DatabaseManager.DATABASE_VERSION) {
    private val DATABASE_NAME = "FlexiCardsDatabase"
    private val DATABASE_VERSION = 1

    // SQLs for creating tables
    private val SQL_CREATE_CARD_TABLE =
            "CREATE TABLE IF NOT EXISTS ${CardEntry.TABLE_NAME} (" +
                    "${CardEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${CardEntry.COLUMN_NAME_CATEGORY_ID} INTEGER, " +
                    "${CardEntry.COLUMN_NAME_FRONT_CONTENT} TEXT, " +
                    "${CardEntry.COLUMN_NAME_BACK_CONTENT} TEXT )";
    private val SQL_CREATE_CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS ${CategoryEntry.TABLE_NAME} (" +
                    "${CategoryEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${CategoryEntry.COLUMN_NAME_NAME} TEXT, " +
                    "${CategoryEntry.COLUMN_NAME_LANGUAGE} TEXT )";
    private val SQL_CREATE_MODULE_TABLE =
            "CREATE TABLE IF NOT EXISTS ${ModuleEntry.TABLE_NAME} (" +
                    "${ModuleEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${ModuleEntry.COLUMN_NAME_CATEGORY_ID} INTEGER, " +
                    "${ModuleEntry.COLUMN_NAME_NAME} TEXT, " +
                    "${ModuleEntry.COLUMN_NAME_CARDS} TEXT )";

    // SQLs for deleting tables
    private val SQL_DELETE_CARD_TABLE = "DROP TABLE IF EXISTS " + CardEntry.TABLE_NAME;
    private val SQL_DELETE_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;
    private val SQL_DELETE_MODULE_TABLE = "DROP TABLE IF EXISTS " + ModuleEntry.TABLE_NAME;


    override fun onCreate(db : SQLiteDatabase) {
        db.execSQL(SQL_DELETE_CARD_TABLE)
        db.execSQL(SQL_DELETE_CATEGORY_TABLE)
        db.execSQL(SQL_DELETE_MODULE_TABLE)
        db.execSQL(SQL_CREATE_CARD_TABLE)
        db.execSQL(SQL_CREATE_CATEGORY_TABLE)
        db.execSQL(SQL_CREATE_MODULE_TABLE)
    }

    override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int) {
        db.execSQL(SQL_DELETE_CARD_TABLE)
        db.execSQL(SQL_DELETE_CATEGORY_TABLE)
        db.execSQL(SQL_DELETE_MODULE_TABLE)
        onCreate(db)
    }

    /**
     * Manually resets DB.
     */
    fun reset() {
        val db = this.readableDatabase
        db.execSQL(SQL_DELETE_CATEGORY_TABLE)
        db.execSQL(SQL_CREATE_CATEGORY_TABLE)
        db.execSQL(SQL_DELETE_CARD_TABLE)
        db.execSQL(SQL_CREATE_CARD_TABLE)
        db.execSQL(SQL_DELETE_MODULE_TABLE)
        db.execSQL(SQL_CREATE_MODULE_TABLE)
    }

    /**
     * Adds a new [category] to database.
     */
    fun createCategory(category : Category) = addCategory(category) > 0

    /**
     * Adds a new [card] to database.
     */
    fun createCard(card : Card) = addCard(card) > 0

    /**
     * Adds a new [module] to database.
     */
    fun createModule(module : Module) = addModule(module) > 0

    /**
     * Returns a Cursor to cards of the specified module.
     */
    fun getModuleCards(moduleId : Long) : Cursor {
        val moduleCursor = readableDatabase.query(
                ModuleEntry.TABLE_NAME,
                arrayOf(ModuleEntry.COLUMN_NAME_CARDS),
                ModuleEntry._ID + "=?",
                arrayOf(moduleId.toString()),
                null, null, null)
        moduleCursor.moveToFirst()
        val inClause = Utils.stringToSqlReadyString(moduleCursor.getString(0))
        return readableDatabase.query(
                CardEntry.TABLE_NAME,
                CardQuery.getQueryArg(),
                CardEntry._ID + " in " + inClause,
                null, null, null, null)
    }

    /**
     * Returns a Cursor to all categories (for category selecting).
     */
    fun getCategories() : Cursor {
        val db = readableDatabase
        // This SQL call should be conformed with CategoryQuery
        val sql = "SELECT * FROM ${CategoryEntry.TABLE_NAME} " +
                "ORDER BY ${CategoryEntry.COLUMN_NAME_LANGUAGE}, ${CategoryEntry.COLUMN_NAME_NAME}"
        return db.rawQuery(sql, null)
    }

    /**
     * Returns a Cursor to modules for the specified category.
     */
    fun getModules(categoryId : Long) = readableDatabase.query(
            ModuleEntry.TABLE_NAME,
            ModuleQuery.getNamesQueryArg(),
            ModuleEntry.COLUMN_NAME_CATEGORY_ID + "=?",
            arrayOf(categoryId.toString()),
            null, null, null)

    /**
     * Inserts [pack] into DB.
     * @param shouldAppendModule
     *  If true, new cards will be added to the existing module (if it exists).
     *  Otherwise the module will be overwritten.
     */
    fun insertCardPack(pack : CardPack, shouldAppendModule : Boolean) : Boolean {
        if (pack.categoryName == null) return false
        val db = writableDatabase
        // finding or creating category and memorizing its ID
        val categoryCursor = db.query(
                CategoryEntry.TABLE_NAME,
                arrayOf(CategoryEntry._ID),
                CategoryEntry.COLUMN_NAME_NAME + "=?",
                arrayOf(pack.categoryName),
                null, null, null)
        var categoryId = 0L
        if (categoryCursor != null && categoryCursor.moveToFirst())
            categoryId = categoryCursor.getString(0).toLong()
        else {
            if (pack.language == null) return false
            val values = ContentValues()
            values.put(CategoryEntry.COLUMN_NAME_NAME, pack.categoryName)
            values.put(CategoryEntry.COLUMN_NAME_LANGUAGE, pack.language)
            categoryId = db.insert(CategoryEntry.TABLE_NAME, null, values)
        }
        categoryCursor.close()
        // adding cards to DB and memorizing their IDs
        val cardIds = addCards(categoryId, pack.cardList)
        // creating or updating module
        if (pack.moduleName == null) return true
        val moduleCursor = db.query(
                ModuleEntry.TABLE_NAME,
                arrayOf(ModuleEntry._ID, ModuleEntry.COLUMN_NAME_CARDS),
                ModuleEntry.COLUMN_NAME_NAME + "=?",
                arrayOf(pack.moduleName),
                null, null, null)
        if (moduleCursor.count > 0) {
            val moduleId = categoryCursor.getString(0)
            val newCardIdsString = if (shouldAppendModule) {
                val existingCardIds = Utils.stringToIntList(categoryCursor.getString(1))
                Utils.listToString(existingCardIds.union(cardIds).toList())
            }
            else {
                Utils.listToString(cardIds)
            }
            val values = ContentValues()
            values.put(ModuleEntry.COLUMN_NAME_CARDS, newCardIdsString)
            val res = db.update(ModuleEntry.TABLE_NAME, values, ModuleEntry._ID + "=?", arrayOf(moduleId.toString()))
            moduleCursor.close()
            db.close()
            return res > 0
        }
        else {
            val values = ContentValues()
            values.put(ModuleEntry.COLUMN_NAME_CATEGORY_ID, categoryId)
            values.put(ModuleEntry.COLUMN_NAME_NAME, pack.moduleName)
            values.put(ModuleEntry.COLUMN_NAME_CARDS, Utils.listToString(cardIds))
            val res = db.insert(ModuleEntry.TABLE_NAME, null, values)
            moduleCursor.close()
            db.close()
            return res > 0
        }
    }

    // FIXME: should not add if a category/module with the same name exists
    /**
     * Adds a new category to DB (private method).
     * @return Row ID of the new category.
     */
    private fun addCategory(category : Category) : Long {
        val db = this.writableDatabase
        db.execSQL(SQL_CREATE_CATEGORY_TABLE)

        val values = ContentValues()
        values.put(CategoryEntry.COLUMN_NAME_NAME, category.name)
        values.put(CategoryEntry.COLUMN_NAME_LANGUAGE, category.language)

        val newCategoryId = db.insert(CategoryEntry.TABLE_NAME, null, values)
        return newCategoryId
    }

    /**
     * Adds a new card to DB (private method).
     * @return Row ID of the new card.
     */
    private fun addCard(card : Card) : Long {
        val db = this.writableDatabase
        db.execSQL(SQL_CREATE_CARD_TABLE)

        val values = ContentValues()
        values.put(CardEntry.COLUMN_NAME_CATEGORY_ID, card.categoryId)
        values.put(CardEntry.COLUMN_NAME_FRONT_CONTENT, card.frontContent)
        values.put(CardEntry.COLUMN_NAME_BACK_CONTENT, card.backContent)

        val newCardId = db.insert(CardEntry.TABLE_NAME, null, values)
        return newCardId
    }

    /**
     * Adds several cards to DB (private method).
     * @return IDs of the new cards.
     */
    private fun addCards(categoryId : Long, cardContentList : List<Pair<String, String>>) : List<Long> {
        val db = this.writableDatabase
        val newCardIds = ArrayList<Long>()
        val values = ContentValues()
        for (item in cardContentList) {
            values.clear()
            values.put(CardEntry.COLUMN_NAME_CATEGORY_ID, categoryId)
            values.put(CardEntry.COLUMN_NAME_FRONT_CONTENT, item.first)
            values.put(CardEntry.COLUMN_NAME_BACK_CONTENT, item.second)
            newCardIds.add(db.insert(CardEntry.TABLE_NAME, null, values))
        }
        return newCardIds
    }

    /**
     * Adds a new module to DB (private method).
     * @return Row ID of the new module.
     */
    private fun addModule(module : Module) : Long {
        val db = this.writableDatabase
        db.execSQL(SQL_CREATE_MODULE_TABLE)

        val values = ContentValues()
        values.put(ModuleEntry.COLUMN_NAME_CATEGORY_ID, module.categoryId)
        values.put(ModuleEntry.COLUMN_NAME_NAME, module.name)
        val cardsString = Utils.listToString(module.cards)
        values.put(ModuleEntry.COLUMN_NAME_CARDS, cardsString)

        val newModuleId = db.insert(ModuleEntry.TABLE_NAME, null, values)
        return newModuleId
    }

    /**
     * Contract for extracting a Category from SQL row.
     */
    class CategoryQuery {
        companion object {
            val COLUMN_INDEX_NAME = 1
            val COLUMN_INDEX_LANGUAGE = 2
        }
    }

    /**
     * Contract for extracting a Card from SQL row.
     */
    class CardQuery {
        companion object {
            val COLUMN_INDEX_FRONT = 0
            val COLUMN_INDEX_BACK = 1
            fun getQueryArg() = arrayOf(CardEntry.COLUMN_NAME_FRONT_CONTENT,
                    CardEntry.COLUMN_NAME_BACK_CONTENT)
        }
    }

    /**
     * Contract for extracting a Module from SQL row.
     */
    class ModuleQuery {
        companion object {
            val COLUMN_INDEX_NAME = 1
            fun getNamesQueryArg() = arrayOf(ModuleEntry._ID, ModuleEntry.COLUMN_NAME_NAME)
        }
    }

    /**
     * Card storing contract.
     */
    private class CardEntry : BaseColumns {
        companion object {
            val _ID = BaseColumns._ID
            val _COUNT = BaseColumns._COUNT
            val TABLE_NAME = "cards"
            val COLUMN_NAME_CATEGORY_ID = "cardCatId"
            var COLUMN_NAME_FRONT_CONTENT = "front"
            var COLUMN_NAME_BACK_CONTENT = "back"
        }
    }

    /**
     * Category storing contract.
     */
    private class CategoryEntry : BaseColumns {
        companion object {
            val _ID = BaseColumns._ID
            val _COUNT = BaseColumns._COUNT
            val TABLE_NAME = "categories"
            val COLUMN_NAME_NAME = "catName"
            var COLUMN_NAME_LANGUAGE = "catLang"
        }
    }

    /**
     * Module storing contract.
     */
    private class ModuleEntry : BaseColumns {
        companion object {
            val _ID = BaseColumns._ID
            val _COUNT = BaseColumns._COUNT
            val TABLE_NAME = "modules"
            val COLUMN_NAME_CATEGORY_ID = "moduleCatId"
            val COLUMN_NAME_NAME = "moduleName"
            var COLUMN_NAME_CARDS = "moduleCards" // this one is actually an array, so we'll have to decode it
        }
    }
}