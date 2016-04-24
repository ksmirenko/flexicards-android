package com.ksmirenko.flexicards.core

import android.content.Context

/**
 * A singleton that provides FlexiDatabase to the app.
 *
 * @author Kirill Smirenko
 */
object FlexiDatabaseProvider {
    private var database : FlexiDatabase? = null

    fun init(context : Context, dbname : String) {
        database = FlexiDatabase(context, dbname);
    }

    fun hasDb() = database != null

    val db : FlexiDatabase
        get() {
            if (database == null) {
                throw IllegalStateException(
                    "FlexiDatabaseProvider must be initialized prior to usage.")
            }
            return database as FlexiDatabase
        }
}