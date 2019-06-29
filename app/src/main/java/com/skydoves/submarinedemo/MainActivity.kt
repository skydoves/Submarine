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

package com.skydoves.submarinedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.submarine.SubmarineCircleClickListener
import com.skydoves.submarine.SubmarineItem
import com.skydoves.submarine.SubmarineItemClickListener
import com.skydoves.submarine.iconForm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
  SubmarineItemClickListener,
  SubmarineCircleClickListener,
  SampleViewHolder.Delegate {

  private val adapter by lazy { SampleAdapter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    adapter.addItems(SampleUtils.getSamples(this))

    submarineView.submarineItemClickListener = this
    submarineView.submarineCircleClickListener = this
    submarineView.float()

    val form = iconForm(this) {
      iconSize = 40
      iconColor = ContextCompat.getColor(baseContext, R.color.colorPrimary)
    }

    submarineView.addSubmarineItem(SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_edit)))
    submarineView.addSubmarineItem(SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_cut)))
    submarineView.addSubmarineItem(SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_share)))
  }

  override fun onCircleClick() {
    if (submarineView.isNavigating) {
      submarineView.dip()
    } else {
      submarineView.float()
    }
  }

  override fun onItemClick(position: Int, submarineItem: SubmarineItem) {
    Toast.makeText(baseContext, "$position", Toast.LENGTH_SHORT).show()
  }

  override fun onItemClick(sampleItem: SampleItem) {
  }
}
