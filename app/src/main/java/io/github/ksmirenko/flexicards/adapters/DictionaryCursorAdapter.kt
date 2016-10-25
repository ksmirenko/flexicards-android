package io.github.ksmirenko.flexicards.adapters

import android.content.Context
import android.database.CharArrayBuffer
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import io.github.ksmirenko.flexicards.DatabaseManager
import io.github.ksmirenko.flexicards.R

/**
 * CursorAdapter for dictionary screen.
 *
 * @author Kirill Smirenko
 */
class DictionaryCursorAdapter(context : Context, cursor : Cursor?) : CursorAdapter(context, cursor, 0) {
    companion object {
        private val FRONT_BUFFER_SIZE = 256
        private val BACK_BUFFER_SIZE = 256
    }

    override fun newView(context : Context, cursor : Cursor, parent : ViewGroup) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item_dictionary, parent, false)
        view.tag = DictionaryListItemViewHolder(
                view.findViewById(R.id.textview_listitem_dict_front) as TextView,
                CharArrayBuffer(FRONT_BUFFER_SIZE),
                view.findViewById(R.id.textview_listitem_dict_back) as TextView,
                CharArrayBuffer(BACK_BUFFER_SIZE)
        )
        return view
    }

    override fun bindView(view : View, context : Context, cursor : Cursor) {
        val holder = view.tag as DictionaryListItemViewHolder;
        cursor.copyStringToBuffer(DatabaseManager.CardQuery.COLUMN_INDEX_FRONT, holder.frontBuffer)
        cursor.copyStringToBuffer(DatabaseManager.CardQuery.COLUMN_INDEX_BACK, holder.backBuffer)
        holder.frontView.setText(holder.frontBuffer.data, 0, holder.frontBuffer.sizeCopied)
        holder.backView.setText(holder.backBuffer.data, 0, holder.backBuffer.sizeCopied)
    }

    private data class DictionaryListItemViewHolder(
            var frontView : TextView,
            var frontBuffer : CharArrayBuffer,
            var backView : TextView,
            var backBuffer : CharArrayBuffer
    ) {}
}