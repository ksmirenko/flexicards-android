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
 * CursorAdapter for module selection.
 *
 * @author Kirill Smirenko
 */
class ModuleCursorAdapter(context : Context, cursor : Cursor?) : CursorAdapter(context, cursor, 0) {
    companion object {
        private val NAME_BUFFER_SIZE = 256
    }

    override fun newView(context : Context, cursor : Cursor, parent : ViewGroup) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_modules, parent, false)
        view.tag = ModuleListItemViewHolder(
                view.findViewById(R.id.textview_catscr_module_name) as TextView,
                CharArrayBuffer(NAME_BUFFER_SIZE)
        )
        return view
    }

    override fun bindView(view : View, context : Context, cursor : Cursor) {
        val holder = view.tag as ModuleListItemViewHolder;
        cursor.copyStringToBuffer(DatabaseManager.ModuleQuery.COLUMN_INDEX_NAME, holder.nameBuffer)
        holder.nameView.setText(holder.nameBuffer.data, 0, holder.nameBuffer.sizeCopied)
    }

    public fun getModuleName(position : Int) : String {
        val prevCursorPosition = cursor.position
        cursor.moveToPosition(position)
        val catName = cursor.getString(DatabaseManager.CategoryQuery.COLUMN_INDEX_NAME)
        cursor.moveToPosition(prevCursorPosition)
        return catName
    }

    private data class ModuleListItemViewHolder(
            var nameView : TextView,
            var nameBuffer : CharArrayBuffer
    ) {}
}