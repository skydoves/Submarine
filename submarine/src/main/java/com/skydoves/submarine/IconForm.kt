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
import android.widget.ImageView
import androidx.core.content.ContextCompat

/** creates an instance of [IconForm] from [IconForm.Builder] using kotlin dsl. */
fun iconForm(context: Context, block: IconForm.Builder.() -> Unit): IconForm =
  IconForm.Builder(context).apply(block).build()

/**
 * IconForm is an attribute class what has some attributes about ImageView
 * for customizing [SubmarineView] navigation icon image easily.
 */
class IconForm(builder: Builder) {

  val iconSize = builder.iconSize
  val iconColor = builder.iconColor
  val iconScaleType = builder.iconScaleType

  /** Builder class for[IconForm]. */
  class Builder(context: Context) {
    @JvmField
    var iconSize = 40
    @JvmField
    var iconColor = ContextCompat.getColor(context, R.color.white_three)
    @JvmField
    var iconScaleType = ImageView.ScaleType.FIT_XY

    fun setIconSize(value: Int): Builder = apply { this.iconSize = value }
    fun setIconTint(value: Int): Builder = apply { this.iconColor = value }
    fun setIconScaleType(value: ImageView.ScaleType): Builder = apply { this.iconScaleType = value }
    fun build(): IconForm {
      return IconForm(this)
    }
  }
}
