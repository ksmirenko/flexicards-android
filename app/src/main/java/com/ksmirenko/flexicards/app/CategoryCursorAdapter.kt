package com.ksmirenko.flexicards.app

import android.content.Context
import android.database.CharArrayBuffer
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * CursorAdapter for category select screen.
 *
 * @author Kirill Smirenko
 */
class CategoryCursorAdapter(context : Context, c : Cursor) : CursorAdapter(context, c, 0) {
    companion object {
        private val NAME_BUFFER_SIZE = 256
    }

    private data class CategoryListItemViewHolder(
            var languageView : TextView,
            var languageBuffer : CharArrayBuffer,
            var nameView : TextView,
            var nameBuffer : CharArrayBuffer
    ) {}

    override fun newView(context : Context, cursor : Cursor, parent : ViewGroup) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_categories, parent, false)
        view.tag = CategoryListItemViewHolder(
                view.findViewById(R.id.textview_mainscr_cat_language) as TextView,
                CharArrayBuffer(2),
                view.findViewById(R.id.textview_mainscr_cat_name) as TextView,
                CharArrayBuffer(NAME_BUFFER_SIZE)
        )
        return view
    }

    override fun bindView(view : View, context : Context, cursor : Cursor) {
        val holder = view.tag as CategoryListItemViewHolder;

        cursor.copyStringToBuffer(DatabaseManager.CategoryQuery.COLUMN_INDEX_LANGUAGE, holder.languageBuffer)
        cursor.copyStringToBuffer(DatabaseManager.CategoryQuery.COLUMN_INDEX_NAME, holder.nameBuffer)
    }

}