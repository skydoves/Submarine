package com.skydoves.submarinedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.skydoves.submarine.SubmarineCircleClickListener
import com.skydoves.submarine.SubmarineItem
import com.skydoves.submarine.SubmarineItemClickListener
import com.skydoves.submarine.iconForm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SubmarineItemClickListener, SubmarineCircleClickListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    button.setOnClickListener {
      submarineView.float()
    }

    button2.setOnClickListener {
      submarineView.dip()
    }

    submarineView.submarineItemClickListener = this
    submarineView.submarineCircleClickListener = this

    val form = iconForm(this) {
      iconSize = 40
      iconColor = ContextCompat.getColor(baseContext, R.color.colorPrimary)
    }

    submarineView.addSubmarineItem(SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_cut)))
    submarineView.addSubmarineItem(SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_edit)))
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
}
