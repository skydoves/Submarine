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
import com.skydoves.submarinedemo.databinding.ActivityMainBinding

class MainActivity :
  AppCompatActivity(),
  SubmarineItemClickListener,
  SubmarineCircleClickListener,
  SampleAdapter.SampleViewHolder.Delegate {

  private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
  private val adapter by lazy { SampleAdapter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(binding.root)

    with(binding) {
      recyclerView.adapter = adapter
      recyclerView.layoutManager =
        LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
      adapter.addItems(SampleUtils.getSamples(this@MainActivity))

      submarineView.submarineItemClickListener = this@MainActivity
      submarineView.submarineCircleClickListener = this@MainActivity
      submarineView.floats()

      scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
        if (!submarineView2.isFloating) {
          if (scrollY == 0) submarineView.floats()
          else submarineView.retreats()
        } else if (submarineView.isFloating) {
          submarineView.retreats()
        } else if (submarineView2.isFloating) {
          submarineView2.retreats()
        }
      }

      submarineView.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_edit), null,
          "edit"
        )
      )
      submarineView.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_wallpaper), null,
          "gallery"
        )
      )
      submarineView.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_archive), null,
          "download"
        )
      )
      submarineView.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_share), null,
          "share"
        )
      )

      submarineView2.submarineItemClickListener = this@MainActivity
      submarineView2.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_star), null,
          "star"
        )
      )
      submarineView2.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_style), null,
          "tag"
        )
      )
      submarineView2.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_office), null,
          "email"
        )
      )
      submarineView2.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_phone), null,
          "call"
        )
      )
    }
  }

  override fun onCircleClick() {
    with(binding) {
      if (submarineView.isNavigating) {
        submarineView.dips()
      } else {
        submarineView.floats()
      }
    }
  }

  override fun onItemClick(position: Int, submarineItem: SubmarineItem) {
    with(binding) {
      if (submarineView.isFloating) {
        submarineView.dips()
        when (position) {
          0 -> toast("edit")
          1 -> toast("style")
          2 -> toast("download")
          3 -> toast("share")
        }
      } else if (submarineView2.isFloating) {
        submarineView2.dips()
        when (position) {
          0 -> toast("star")
          1 -> toast("tag")
          2 -> toast("email")
          3 -> toast("phone")
        }
      }
    }
  }

  override fun onItemClick(sampleItem: SampleItem) {
    with(binding) {
      if (submarineView2.isFloating) {
        submarineView2.dips()
      } else if (!submarineView.isFloating) {
        submarineView2.circleIcon.setImageDrawable(sampleItem.image)
        submarineView2.floats()
      }
    }
  }

  private fun toast(content: String) {
    Toast.makeText(baseContext, content, Toast.LENGTH_SHORT).show()
  }
}
