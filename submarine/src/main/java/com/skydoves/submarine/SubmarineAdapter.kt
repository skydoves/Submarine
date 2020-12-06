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
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.submarine.databinding.ItemSubmarineBinding

/** SubmarineAdapter is an implementation of [RecyclerView.Adapter] that has [SubmarineItem] as an section items. */
@Suppress("unused")
class SubmarineAdapter(
  private val submarineItemClickListener: SubmarineItemClickListener? = null
) : RecyclerView.Adapter<SubmarineAdapter.SubmarineViewHolder>() {

  private val itemList: MutableList<SubmarineItemWrapper> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmarineViewHolder {
    val binding = ItemSubmarineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return SubmarineViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
          ?: return@setOnClickListener
        submarineItemClickListener?.onItemClick(position, itemList[position].submarineItem)
      }
    }
  }

  override fun onBindViewHolder(holder: SubmarineViewHolder, position: Int) {
    val wrapper = itemList[position]
    val submarineItem = wrapper.submarineItem
    holder.bindItem(submarineItem)
    holder.binding.run {
      root.layoutParams = if (wrapper.orientation == SubmarineOrientation.HORIZONTAL) {
        FrameLayout.LayoutParams(
          wrapper.parentWidth / wrapper.itemSize, RelativeLayout.LayoutParams.MATCH_PARENT
        )
      } else {
        FrameLayout.LayoutParams(
          RelativeLayout.LayoutParams.MATCH_PARENT, wrapper.parentWidth / wrapper.itemSize
        )
      }
    }
  }

  internal fun addItem(submarineItem: SubmarineItemWrapper) {
    this.itemList.add(submarineItem)
    updateItemSize()
  }

  internal fun addItemList(submarineItemList: List<SubmarineItemWrapper>) {
    this.itemList.addAll(submarineItemList)
    updateItemSize()
  }

  internal fun removeItem(submarineItem: SubmarineItemWrapper) {
    this.itemList.remove(submarineItem)
    updateItemSize()
  }

  internal fun clearAllItems() {
    this.itemList.clear()
    notifyDataSetChanged()
  }

  private fun updateItemSize() {
    this.itemList.forEach { it.itemSize = itemList.size }
    notifyDataSetChanged()
  }

  override fun getItemCount() = this.itemList.size

  class SubmarineViewHolder(
    val binding: ItemSubmarineBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(submarineItem: SubmarineItem) {
      with(binding.itemSubmarineIcon) {
        setImageDrawable(submarineItem.icon)
        submarineItem.iconForm?.let {
          layoutParams.width = context.dp2Px(it.iconSize)
          layoutParams.height = context.dp2Px(it.iconSize)
          scaleType = it.iconScaleType
          ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(it.iconColor))
        }
      }
    }
  }
}
