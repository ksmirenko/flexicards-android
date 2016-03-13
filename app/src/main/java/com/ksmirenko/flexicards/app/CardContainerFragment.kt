/*
 * Copyright 2012 The Android Open Source Project
 * Modifications copyright 2016 Kirill Smirenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ksmirenko.flexicards.app

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Fragment that represents a single viewed card.
 */
class CardContainerFragment : Fragment() {
    companion object {
        // arguments
        val ARG_FRONT_CONTENT = "front"
        val ARG_BACK_CONTENT = "back"
        val ARG_IS_BACK_FIRST = "backfirst"

        private val dummyCallbacks = DummyCallbacks()
    }

    private var callbacks : Callbacks = dummyCallbacks
    private var isShowingBack = false

    override fun onAttach(context : Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_cards_container, container, false)
        val args = arguments
        isShowingBack = args.getBoolean(ARG_IS_BACK_FIRST, false)

        // adding card layout
        val cardFragment = if (isShowingBack) CardBackFragment(callbacks) else CardFrontFragment(callbacks)
        cardFragment.arguments = args // small workaround
        //        fragmentManager
        childFragmentManager
                .beginTransaction()
                .add(R.id.layout_card_container, cardFragment)
                .commit()
        // adding tap event handler
        val layout = rootView.findViewById(R.id.layout_card_container)
        layout.setOnTouchListener { view, motionEvent ->
            flipCard()
            false
        }
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = dummyCallbacks
    }

    private fun flipCard() {
        //        if (isShowingBack) {
        //            fragmentManager.popBackStack()
        //            return
        //        }
        val newFragment = if (isShowingBack) CardFrontFragment(callbacks) else CardBackFragment(callbacks)
        newFragment.arguments = arguments
        //        fragmentManager
        childFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out
                )
                .replace(R.id.layout_card_container, newFragment)
                //                .addToBackStack(null)
                .commit()
        isShowingBack = !isShowingBack
    }

    class CardFrontFragment(val callbacks : Callbacks) : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_card, container, false)
            rootView.setBackgroundColor(ContextCompat.getColor(MainActivity.getAppContext(), R.color.background))
            val textView = rootView.findViewById(R.id.textview_cardview_mainfield) as TextView
            textView.text = arguments.getString(CardContainerFragment.ARG_FRONT_CONTENT)
            rootView.findViewById(R.id.button_cardview_know).setOnClickListener { callbacks.onCardButtonClicked(true) }
            rootView.findViewById(R.id.button_cardview_notknow).setOnClickListener { callbacks.onCardButtonClicked(false) }
            return rootView
        }
    }

    class CardBackFragment(val callbacks : Callbacks) : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_card, container, false)
            rootView.setBackgroundColor(ContextCompat.getColor(MainActivity.getAppContext(), R.color.backgroundDark))
            val textView = rootView.findViewById(R.id.textview_cardview_mainfield) as TextView
            textView.text = arguments.getString(CardContainerFragment.ARG_BACK_CONTENT)
            rootView.findViewById(R.id.button_cardview_know).setOnClickListener { callbacks.onCardButtonClicked(true) }
            rootView.findViewById(R.id.button_cardview_notknow).setOnClickListener { callbacks.onCardButtonClicked(false) }
            return rootView
        }
    }

    class DummyCallbacks() : Callbacks {
        override fun onCardButtonClicked(knowIt: Boolean) {
        }
    }

    interface Callbacks {
        fun onCardButtonClicked(knowIt: Boolean)
    }
}