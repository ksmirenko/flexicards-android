package com.ksmirenko.flexicards.core.adapters

import android.app.Fragment
import android.app.FragmentManager
import android.database.Cursor
import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v4.app.FragmentManager
import android.support.v13.app.FragmentStatePagerAdapter
import com.ksmirenko.flexicards.core.layout.CardContainerFragment
import com.ksmirenko.flexicards.core.DatabaseManager

/**
 * Adapter for card collection (used when viewing a module).
 *
 * @author Kirill Smirenko
 */
class CardsPagerAdapter(
        fm : FragmentManager,
        private val cardsCursor : Cursor,
        private val isBackFirst : Boolean = false
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i : Int) : Fragment {
        val fragment = CardContainerFragment()
        val args = Bundle()
        cardsCursor.moveToPosition(i) // may be inefficient
        args.putString(CardContainerFragment.ARG_FRONT_CONTENT,
                cardsCursor.getString(DatabaseManager.CardQuery.COLUMN_INDEX_FRONT))
        args.putString(CardContainerFragment.ARG_BACK_CONTENT,
                cardsCursor.getString(DatabaseManager.CardQuery.COLUMN_INDEX_BACK))
        args.putBoolean(CardContainerFragment.ARG_IS_BACK_FIRST, isBackFirst)
        fragment.arguments = args

        return fragment
    }

    override fun getCount() : Int {
        return cardsCursor.count
    }
}