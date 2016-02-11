package com.ksmirenko.flexicards.app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.ksmirenko.flexicards.app.datatypes.*
import com.ksmirenko.flexicards.app.Utils

/**
 * Application's SQL database manager.
 *
 * @author Kirill Smirenko
 */
class DatabaseManager(context : Context) :
        SQLiteOpenHelper(context, DatabaseManager.DATABASE_NAME, null, DatabaseManager.DATABASE_VERSION) {
    companion object {
        protected val DATABASE_NAME = "FlexiCardsDatabase"
        private val DATABASE_VERSION = 1
    }

    // SQLs for creating tables
    private val SQL_CREATE_CARD_TABLE =
            "CREATE TABLE ${CardEntry.TABLE_NAME} (" +
                    "${CardEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${CardEntry.COLUMN_NAME_CATEGORY_ID} INTEGER, " +
                    "${CardEntry.COLUMN_NAME_FRONT_CONTENT} TEXT, " +
                    "${CardEntry.COLUMN_NAME_BACK_CONTENT} TEXT )";
    private val SQL_CREATE_CATEGORY_TABLE =
            "CREATE TABLE ${CategoryEntry.TABLE_NAME} (" +
                    "${CategoryEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${CategoryEntry.COLUMN_NAME_NAME} TEXT, " +
                    "${CategoryEntry.COLUMN_NAME_LANGUAGE} TEXT )";
    private val SQL_CREATE_MODULE_TABLE =
            "CREATE TABLE ${ModuleEntry.TABLE_NAME} (" +
                    "${ModuleEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${ModuleEntry.COLUMN_NAME_CATEGORY_ID} INTEGER, " +
                    "${ModuleEntry.COLUMN_NAME_NAME} TEXT, " +
                    "${ModuleEntry.COLUMN_NAME_CARDS} TEXT )";

    // SQLs for deleting tables
    private val SQL_DELETE_CARD_TABLE = "DROP TABLE IF EXISTS " + CardEntry.TABLE_NAME;
    private val SQL_DELETE_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;
    private val SQL_DELETE_MODULE_TABLE = "DROP TABLE IF EXISTS " + ModuleEntry.TABLE_NAME;

    override fun onCreate(db : SQLiteDatabase) {
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
     * Adds a new [category] to database.
     */
    fun createCategory(category : Category) : Boolean {
        val values = ContentValues()
        values.put(CategoryEntry.COLUMN_NAME_NAME, category.name)
        values.put(CategoryEntry.COLUMN_NAME_LANGUAGE, category.language)

        val db = this.writableDatabase
        val createSuccessful = db.insert(CategoryEntry.TABLE_NAME, null, values) > 0
        db.close()
        return createSuccessful
    }

    /**
     * Adds a new [card] to database.
     */
    fun createCard(card : Card) : Boolean {
        val values = ContentValues()
        values.put(CardEntry.COLUMN_NAME_CATEGORY_ID, card.categoryId)
        values.put(CardEntry.COLUMN_NAME_FRONT_CONTENT, card.frontContent)
        values.put(CardEntry.COLUMN_NAME_BACK_CONTENT, card.backContent)

        val db = this.writableDatabase
        val createSuccessful = db.insert(CardEntry.TABLE_NAME, null, values) > 0
        db.close()
        return createSuccessful
    }

    /**
     * Adds a new [module] to database.
     */
    fun createModule(module : Module) : Boolean {
        val values = ContentValues()
        values.put(ModuleEntry.COLUMN_NAME_CATEGORY_ID, module.categoryId)
        values.put(ModuleEntry.COLUMN_NAME_NAME, module.name)
        val cardsString = Utils.listToString(module.cards)
        values.put(ModuleEntry.COLUMN_NAME_CARDS, cardsString)

        val db = this.writableDatabase
        val createSuccessful = db.insert(ModuleEntry.TABLE_NAME, null, values) > 0
        db.close()
        return createSuccessful
    }

    /**
     * Returns a Cursor to all categories (for category selecting).
     */
    fun getCategories() : Cursor {
        val db = readableDatabase
        val sql = "SELECT * FROM ${CategoryEntry.TABLE_NAME} " +
                "ORDER BY ${CategoryEntry.COLUMN_NAME_LANGUAGE}, ${CategoryEntry.COLUMN_NAME_NAME}"
        return db.rawQuery(sql, null)
    }

    public class CategoryQuery {
        companion object {
            val COLUMN_INDEX_NAME = 1
            val COLUMN_INDEX_LANGUAGE = 2
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
            val TABLE_NAME = "categories"
            val COLUMN_NAME_CATEGORY_ID = "moduleCatId"
            val COLUMN_NAME_NAME = "moduleName"
            var COLUMN_NAME_CARDS = "moduleCards" // this one is actually an array, so we'll have to decode it
        }
    }
}