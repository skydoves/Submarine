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

package com.skydoves.submarine

import android.content.res.ColorStateList
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.ImageViewCompat
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_submarine.view.*

/** SubmarineViewHolder is an implementation of [BaseViewHolder]  that has [SubmarineItemWrapper] as a data. */
class SubmarineViewHolder(
  private val onItemClickListener: SubmarineItemClickListener? = null,
  view: View
) : BaseViewHolder(view) {

  private lateinit var wrapper: SubmarineItemWrapper
  private lateinit var submarineItem: SubmarineItem

  override fun bindData(data: Any) {
    if (data is SubmarineItemWrapper) {
      this.wrapper = data
      this.submarineItem = data.submarineItem
      drawItemUI()
    }
  }

  private fun drawItemUI() {
    itemView.apply {
      if (wrapper.orientation == SubmarineOrientation.HORIZONTAL) {
        val params = RelativeLayout.LayoutParams(
          wrapper.parentWidth / wrapper.itemSize, RelativeLayout.LayoutParams.MATCH_PARENT)
        this.layoutParams = params
      } else {
        val params = RelativeLayout.LayoutParams(
          RelativeLayout.LayoutParams.MATCH_PARENT, wrapper.parentWidth / wrapper.itemSize)
        this.layoutParams = params
      }

      item_submarine_icon.setImageDrawable(submarineItem.icon)

      submarineItem.iconForm?.let {
        item_submarine_icon.layoutParams.width = context.dp2Px(it.iconSize)
        item_submarine_icon.layoutParams.height = context.dp2Px(it.iconSize)
        item_submarine_icon.scaleType = it.iconScaleType
        ImageViewCompat.setImageTintList(item_submarine_icon, ColorStateList.valueOf(it.iconColor))
      }
    }
  }

  override fun onClick(p0: View?) {
    this.onItemClickListener?.onItemClick(adapterPosition, submarineItem)
  }

  override fun onLongClick(p0: View?) = false
}
