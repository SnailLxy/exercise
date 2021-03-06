package com.lixueyang.exercise.widgets

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * Created on 2021/4/7.
 *
 */
class DepthPageTransformer : ViewPager2.PageTransformer {
    private val MIN_SCALE = 0.75f

    override fun transformPage(page: View, position: Float) {
        page.apply {
            val pageWidth = width
            when {
                position < -1 -> {
                    alpha = 0f
                }

                position <= 0 -> {
                    alpha = 1f
                    translationX = 0f
                    scaleY = 1f
                    scaleX = 1f
                }

                position <= 1 -> {
                    alpha = 1 - position
                    translationX = pageWidth * -position
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }
}