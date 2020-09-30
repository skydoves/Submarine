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
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.submarine.SubmarineCircleClickListener
import com.skydoves.submarine.SubmarineItem
import com.skydoves.submarine.SubmarineItemClickListener
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.scrollView
import kotlinx.android.synthetic.main.activity_main.submarineView
import kotlinx.android.synthetic.main.activity_main.submarineView2

class MainActivity :
  AppCompatActivity(),
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

    scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
      if (!submarineView2.isFloating) {
        if (scrollY == 0) submarineView.float()
        else submarineView.retreat()
      } else if (submarineView.isFloating) {
        submarineView.retreat()
      } else if (submarineView2.isFloating) {
        submarineView2.retreat()
      }
    }

    submarineView.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_edit))
    )
    submarineView.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_wallpaper))
    )
    submarineView.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_archive))
    )
    submarineView.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_share))
    )

    submarineView2.submarineItemClickListener = this
    submarineView2.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_star))
    )
    submarineView2.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_style))
    )
    submarineView2.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_office))
    )
    submarineView2.addSubmarineItem(
      SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_phone))
    )
  }

  override fun onCircleClick() {
    if (submarineView.isNavigating) {
      submarineView.dip()
    } else {
      submarineView.float()
    }
  }

  override fun onItemClick(position: Int, submarineItem: SubmarineItem) {
    if (submarineView.isFloating) {
      submarineView.dip()
      when (position) {
        0 -> toast("edit")
        1 -> toast("style")
        2 -> toast("download")
        3 -> toast("share")
      }
    } else if (submarineView2.isFloating) {
      submarineView2.dip()
      when (position) {
        0 -> toast("star")
        1 -> toast("tag")
        2 -> toast("email")
        3 -> toast("phone")
      }
    }
  }

  override fun onItemClick(sampleItem: SampleItem) {
    if (submarineView2.isFloating) {
      submarineView2.dip()
    } else if (!submarineView.isFloating) {
      submarineView2.circleIcon.setImageDrawable(sampleItem.image)
      submarineView2.float()
    }
  }

  private fun toast(content: String) {
    Toast.makeText(baseContext, content, Toast.LENGTH_SHORT).show()
  }
}
