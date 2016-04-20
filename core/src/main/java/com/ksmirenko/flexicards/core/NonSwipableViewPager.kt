package com.ksmirenko.flexicards.core

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Non-swipable extension of support ViewPager.
 *
 * @author Kirill Smirenko
 */
class NonSwipableViewPager : ViewPager {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onInterceptTouchEvent(event : MotionEvent) = false

    override fun onTouchEvent(event : MotionEvent) = false
}