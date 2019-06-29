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

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow

/** SubmarineAdapter is an implementation of [BaseAdapter] that has [SubmarineItem] as an section items. */
@Suppress("unused")
class SubmarineAdapter(
  private val submarineItemClickListener: SubmarineItemClickListener? = null
) : BaseAdapter() {

  init {
    addSection(ArrayList<SubmarineItemWrapper>())
  }

  internal fun addItem(submarineItem: SubmarineItemWrapper) {
    addItemOnSection(0, submarineItem)
    updateItemSize()
  }

  internal fun addItemList(submarineItemList: List<SubmarineItemWrapper>) {
    addItemListOnSection(0, submarineItemList)
    updateItemSize()
  }

  internal fun removeItem(submarineItem: SubmarineItemWrapper) {
    removeItemOnSection(0, submarineItem)
    updateItemSize()
  }

  internal fun clearAllItems() {
    clearSection(0)
    notifyDataSetChanged()
  }

  private fun updateItemSize() {
    for (item in sectionItems<SubmarineItemWrapper>(0)) {
      item as SubmarineItemWrapper
      item.itemSize = sectionItems<SubmarineItemWrapper>(0).size
    }
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_submarine

  override fun viewHolder(layout: Int, view: View) = SubmarineViewHolder(submarineItemClickListener, view)
}
