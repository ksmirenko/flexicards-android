package com.ksmirenko.flexicards.app

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Adapter for card collection (used when viewing a module).
 *
 * @author Kirill Smirenko
 */
class CardsPagerAdapter(fm : FragmentManager, private val cardsCursor : Cursor) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i : Int) : Fragment {
        val fragment = CardFragment()
        val args = Bundle()
        cardsCursor.moveToPosition(i) // may be inefficient
        args.putString(CardFragment.ARG_FRONT_CONTENT,
                cardsCursor.getString(DatabaseManager.CardQuery.COLUMN_INDEX_FRONT))
        args.putString(CardFragment.ARG_BACK_CONTENT,
                cardsCursor.getString(DatabaseManager.CardQuery.COLUMN_INDEX_BACK))
        fragment.arguments = args
        return fragment
    }

    override fun getCount() : Int {
        return cardsCursor.count
    }

    /**
     * Fragment that represents a single viewed card.
     */
    class CardFragment : Fragment() {
        companion object {
            /**
             * The argument representing content of the card's front side.
             */
            val ARG_FRONT_CONTENT = "front"
            /**
             * The argument representing content of the card's back side.
             */
            val ARG_BACK_CONTENT = "back"
        }

        override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
                savedInstanceState : Bundle?) : View? {
            val rootView = inflater!!.inflate(R.layout.fragment_cards_view, container, false)
            val args = arguments
            val textView = rootView.findViewById(R.id.textview_cardview_mainfield) as TextView
            textView.text = args.getString(ARG_FRONT_CONTENT)
            // TODO: add flip animation and logic (on tap)
            return rootView
        }
    }
}