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

@file:Suppress("unused")

package com.skydoves.submarine

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat

@DslMarker
internal annotation class IconFormMarker

/** creates an instance of [IconForm] from [IconForm.Builder] using kotlin dsl. */
@JvmSynthetic
@IconFormMarker
inline fun iconForm(crossinline block: IconForm.Builder.() -> Unit): IconForm =
  IconForm.Builder().apply(block).build()

/**
 * IconForm is an attribute class what has some attributes about ImageView
 * for customizing [SubmarineView] navigation icon image easily.
 */
class IconForm(builder: Builder) {

  @Px val iconSize = builder.iconSize
  @ColorInt val iconColor = builder.iconColor
  val iconScaleType = builder.iconScaleType

  /** Builder class for[IconForm]. */
  class Builder {
    @JvmField @Px
    @set:JvmSynthetic
    var iconSize = 40

    @JvmField @ColorInt
    @set:JvmSynthetic
    var iconColor = Color.parseColor("#f4f4f4")

    @JvmField
    @set:JvmSynthetic
    var iconScaleType = ImageView.ScaleType.FIT_XY

    /** sets the icon square size. */
    fun setIconSize(@Px value: Int): Builder = apply { this.iconSize = value }

    /** sets the icon square size using a dimension resource. */
    fun setIconSizeRes(context: Context, @DimenRes value: Int): Builder = apply {
      return setIconSize(context.resources.getDimension(value).toInt())
    }

    /** sets tint color of the the icon. */
    fun setIconTint(@ColorInt value: Int): Builder = apply { this.iconColor = value }

    /** sets tint color using a color resource of the icon. */
    fun setIconTintRes(context: Context, @ColorRes value: Int): Builder = apply {
      return setIconTint(ContextCompat.getColor(context, value))
    }

    /** sets the scale type of the icon. */
    fun setIconScaleType(value: ImageView.ScaleType): Builder = apply { this.iconScaleType = value }

    fun build() = IconForm(this)
  }
}
