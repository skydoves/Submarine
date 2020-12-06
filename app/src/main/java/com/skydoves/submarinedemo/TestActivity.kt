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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.skydoves.submarine.SubmarineItem
import com.skydoves.submarine.iconForm
import com.skydoves.submarinedemo.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityTestBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {
      submarineView.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_edit))
      )
      submarineView.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_wallpaper))
      )
      submarineView.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_archive))
      )
      submarineView.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_share))
      )

      submarineView2.circleIcon.setImageDrawable(
        ContextCompat.getDrawable(this@TestActivity, R.drawable.sample2)
      )
      val star = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.colorPrimary)
        iconSize = 35
      }
      submarineView2.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_star), star)
      )
      submarineView2.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_style))
      )
      submarineView2.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_office))
      )
      submarineView2.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_phone))
      )

      val blueForm = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.blue01)
        iconSize = 35
      }
      submarineView3.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_star), blueForm)
      )
      submarineView3.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_style), blueForm)
      )
      submarineView3.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_office), blueForm)
      )
      submarineView3.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_phone), blueForm)
      )
      submarineView3.addSubmarineItem(
        SubmarineItem(
          ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_wallpaper),
          blueForm
        )
      )

      submarineView4.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_star))
      )
      submarineView4.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_style))
      )
      submarineView4.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_office))
      )
      submarineView4.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.ic_phone))
      )

      val red = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.red)
        iconSize = 40
      }
      val amber = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.amber)
        iconSize = 40
      }
      val yellow = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.yellow)
        iconSize = 40
      }
      val green = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.green)
        iconSize = 40
      }
      val blue = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.blue01)
        iconSize = 40
      }
      val purple = iconForm {
        iconColor = ContextCompat.getColor(this@TestActivity, R.color.blue02)
        iconSize = 40
      }

      submarineView5.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), red)
      )
      submarineView5.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), amber)
      )
      submarineView5.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), yellow)
      )
      submarineView5.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), green)
      )
      submarineView5.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), blue)
      )
      submarineView5.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), purple)
      )

      submarineView6.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), red)
      )
      submarineView6.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), amber)
      )
      submarineView6.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), yellow)
      )
      submarineView6.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), green)
      )
      submarineView6.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), blue)
      )
      submarineView6.addSubmarineItem(
        SubmarineItem(ContextCompat.getDrawable(this@TestActivity, R.drawable.circle), purple)
      )

      button.setOnClickListener {
        if (!submarineView.isFloating) {
          submarineView.float()
          submarineView2.float()
          submarineView3.float()
          submarineView4.float()
          submarineView5.float()
          submarineView6.float()
          button.text = "dip"
        } else {
          submarineView.dip()
          submarineView2.dip()
          submarineView3.dip()
          submarineView4.dip()
          submarineView5.dip()
          submarineView6.dip()
          button.text = "float"
        }
      }
    }
  }
}
