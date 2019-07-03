/*
 * Copyright (C) 2019 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UNCHECKED_CAST")

package com.skydoves.submarine

import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager

/** begins delayed transition. */
internal fun ViewGroup.beginDelayedTransition(duration: Long) {
  TransitionManager.beginDelayedTransition(this, ChangeBounds().setDuration(duration))
}

/** updates [FrameLayout] params. */
internal fun ViewGroup.updateLayoutParams(block: ViewGroup.LayoutParams.() -> Unit) {
  layoutParams?.let {
    val params: ViewGroup.LayoutParams = (layoutParams as ViewGroup.LayoutParams).apply { block(this) }
    layoutParams = params
  }
}

/** makes visible or invisible a View align the value parameter. */
internal fun View.visible(value: Boolean) {
  if (value) {
    this.visibility = View.VISIBLE
  } else {
    this.visibility = View.GONE
  }
}

/** translates view's x position to destination. */
internal fun View.translateX(duration: Long, destination: Float): ViewPropertyAnimator {
  return this.animate()
    .setDuration(duration)
    .translationX(destination)
    .withLayer()
}

/** translates view's y position to destination. */
internal fun View.translateY(duration: Long, destination: Float): ViewPropertyAnimator {
  return this.animate()
    .setDuration(duration)
    .translationY(destination)
    .withLayer()
}

/** animates view's x and y scale size. */
internal fun View.animateScale(toX: Float, toY: Float, duration: Long): ViewPropertyAnimator {
  return this.animate()
    .scaleX(toX)
    .scaleY(toY)
    .setDuration(duration)
    .withLayer()
}

/** animates view's alpha value. */
internal fun View.animateFade(alpha: Float, duration: Long): ViewPropertyAnimator {
  return this.animate()
    .alpha(alpha)
    .setDuration(duration)
    .withLayer()
}
