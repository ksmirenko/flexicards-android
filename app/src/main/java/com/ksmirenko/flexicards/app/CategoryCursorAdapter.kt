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
class CategoryCursorAdapter(context : Context, cursor : Cursor?) : CursorAdapter(context, cursor, 0) {
    companion object {
        private val NAME_BUFFER_SIZE = 256
    }

    private val langBuffer = CharArrayBuffer(2)
    private var rowTypeInfo = emptyArray<RowType>()

    init {
        rowTypeInfo = if (cursor != null) Array(cursor.count, { RowType.UNKNOWN }) else emptyArray();
    }

    override fun changeCursor(cursor : Cursor?) {
        super.changeCursor(cursor);
        rowTypeInfo = if (cursor != null) Array(cursor.count, { RowType.UNKNOWN }) else emptyArray();
    }

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

        val position = cursor.position
        val separatorVisibility = when (rowTypeInfo[position]) {
            RowType.SECTIONED -> View.VISIBLE
            RowType.REGULAR -> View.GONE
            RowType.UNKNOWN -> {
                // it's yet unknown whether separator should be shown
                val ansVisible : Boolean
                if (position == 0) {
                    // if first row
                    ansVisible = true
                } else {
                    // if previous row's language is different than this one's
                    cursor.moveToPosition(position - 1)
                    cursor.copyStringToBuffer(DatabaseManager.CategoryQuery.COLUMN_INDEX_LANGUAGE, langBuffer)
                    if (langBuffer.sizeCopied > 0 && holder.languageBuffer.sizeCopied > 0 &&
                            (langBuffer.data[0] != holder.languageBuffer.data[0] ||
                                    langBuffer.data[1] != holder.languageBuffer.data[1])) {
                        ansVisible = true
                    } else {
                        ansVisible = false
                    }
                    cursor.moveToPosition(position)
                }
                // caching the result of calculation
                rowTypeInfo[position] = if (ansVisible) RowType.SECTIONED else RowType.REGULAR;
                if (ansVisible) View.VISIBLE else View.GONE
            }
        }

        holder.languageView.setText(holder.languageBuffer.data, 0, holder.languageBuffer.sizeCopied)
        holder.languageView.visibility = separatorVisibility

        holder.nameView.setText(holder.nameBuffer.data, 0, holder.nameBuffer.sizeCopied)
    }

    fun getCategoryName(position : Int) : String {
        val prevCursorPosition = cursor.position
        cursor.moveToPosition(position)
        val catName = cursor.getString(DatabaseManager.CategoryQuery.COLUMN_INDEX_NAME)
        cursor.moveToPosition(prevCursorPosition)
        return catName
    }

    private data class CategoryListItemViewHolder(
            var languageView : TextView,
            var languageBuffer : CharArrayBuffer,
            var nameView : TextView,
            var nameBuffer : CharArrayBuffer
    ) {}

    private enum class RowType {
        UNKNOWN, SECTIONED, REGULAR
    }
}