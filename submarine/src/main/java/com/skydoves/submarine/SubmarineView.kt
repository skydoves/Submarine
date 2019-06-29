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

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

/** SubmarineView is floating navigation view that dynamically lists items. */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class SubmarineView : FrameLayout {

  var isFloating = false
    private set
  var isNavigating = false
    private set

  private lateinit var drawable: GradientDrawable
  private var orientation = SubmarineOrientation.HORIZONTAL
  private var circleAnchor = CircleAnchor.LEFT
  val circleIcon = CircleImageView(context)
  val recyclerView = RecyclerView(context)
  var autoNavigate = true
  var autoRetreat = true
  var duration = 350L

  var circleSize = 52f
    set(value) {
      field = value
      updateSubmarine()
    }
  var circleImage: Drawable? = null
    set(value) {
      field = value
      updateSubmarine()
    }
  var circlePadding = 4f
    set(value) {
      field = value
      updateSubmarine()
    }
  var circleBorderSize = 0f
    set(value) {
      field = value
      updateSubmarine()
    }
  var circleBorderColor = ContextCompat.getColor(context, R.color.grass)
    set(value) {
      field = value
      updateSubmarine()
    }
  var radius = dp2Px(48).toFloat()
    set(value) {
      field = value
      updateSubmarine()
    }
  var color = ContextCompat.getColor(context, R.color.grass)
    set(value) {
      field = value
      updateSubmarine()
    }
  var borderSize = 0f
    set(value) {
      field = value
      updateSubmarine()
    }
  var borderColor = ContextCompat.getColor(context, R.color.grass)
    set(value) {
      field = value
      updateSubmarine()
    }
  var expandSize = context.displaySize().x - dp2Px(20)
    set(value) {
      field = value
      updateSubmarine()
    }
  var submarineAnimation = SubmarineAnimation.NONE
    set(value) {
      field = value
      updateSubmarine()
    }

  private var adapter = SubmarineAdapter()
  var submarineCircleClickListener: SubmarineCircleClickListener? = null
    set(value) {
      field = value
      circleIcon.setOnClickListener { value?.onCircleClick() }
    }
  var submarineItemClickListener: SubmarineItemClickListener? = null
    set(value) {
      field = value
      adapter = SubmarineAdapter(value)
      recyclerView.adapter = adapter
    }

  constructor(context: Context) : super(context)

  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    getAttrs(attributeSet)
  }

  constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
    getAttrs(attributeSet, defStyle)
  }

  private fun getAttrs(attributeSet: AttributeSet) {
    val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SubmarineView)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun getAttrs(attributeSet: AttributeSet, defStyleAttr: Int) {
    val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SubmarineView, defStyleAttr, 0)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun setTypeArray(a: TypedArray) {
    when (a.getInt(R.styleable.SubmarineView_submarine_orientation, 0)) {
      0 -> this.orientation = SubmarineOrientation.HORIZONTAL
      1 -> this.orientation = SubmarineOrientation.VERTICAL
    }
    when (a.getInt(R.styleable.SubmarineView_submarine_circleAnchor, 0)) {
      0 -> this.circleAnchor = CircleAnchor.LEFT
      1 -> this.circleAnchor = CircleAnchor.RIGHT
      2 -> this.circleAnchor = CircleAnchor.TOP
      3 -> this.circleAnchor = CircleAnchor.BOTTOM
    }
    when (a.getInt(R.styleable.SubmarineView_submarine_animation, 0)) {
      0 -> this.submarineAnimation = SubmarineAnimation.NONE
      1 -> this.submarineAnimation = SubmarineAnimation.FADE
      2 -> this.submarineAnimation = SubmarineAnimation.SCALE
    }
    this.autoNavigate = a.getBoolean(R.styleable.SubmarineView_submarine_autoNavigate, this.autoNavigate)
    this.autoRetreat = a.getBoolean(R.styleable.SubmarineView_submarine_autoRetreat, this.autoRetreat)
    this.duration = a.getInt(R.styleable.SubmarineView_submarine_duration, this.duration.toInt()).toLong()
    this.circleSize = a.getDimension(R.styleable.SubmarineView_submarine_circleSize, this.circleSize)
    this.circleImage = a.getDrawable(R.styleable.SubmarineView_submarine_circleDrawable)
    this.circlePadding = a.getDimension(R.styleable.SubmarineView_submarine_circlePadding, this.circlePadding)
    this.circleBorderSize = a.getDimension(R.styleable.SubmarineView_submarine_circleBorderSize, this.circleBorderSize)
    this.circleBorderColor = a.getColor(R.styleable.SubmarineView_submarine_circleBorderColor, this.circleBorderColor)
    this.radius = a.getDimension(R.styleable.SubmarineView_submarine_radius, this.radius)
    this.color = a.getColor(R.styleable.SubmarineView_submarine_color, this.color)
    this.borderSize = a.getDimension(R.styleable.SubmarineView_submarine_borderSize, this.borderSize)
    this.borderColor = a.getColor(R.styleable.SubmarineView_submarine_borderColor, this.borderColor)
    this.expandSize = a.getDimension(R.styleable.SubmarineView_submarine_expandSize, this.expandSize.toFloat()).toInt()
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    updateSubmarine()
  }

  /** updates submarine attributes. */
  private fun updateSubmarine() {
    updateSize()
    updateBackground()
    updateCircleIcon()
    updateChildViews()
    updateAnimation()
  }

  /** updates the submarine view's param sizes. */
  private fun updateSize() {
    this.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        updateLayoutParams {
          width = dp2Px(circleSize)
          height = dp2Px(circleSize)
        }
        viewTreeObserver.removeOnGlobalLayoutListener(this)
      }
    })
  }

  /** updates background and bordering. */
  private fun updateBackground() {
    drawable = ContextCompat.getDrawable(context, R.drawable.rectangle_layout) as GradientDrawable
    drawable.cornerRadius = radius
    drawable.setColor(color)
    if (borderSize != 0f) {
      drawable.setStroke(borderSize.toInt(), borderColor)
    }
    background = drawable
  }

  /** updates the circle icon. */
  private fun updateCircleIcon() {
    if (circleImage != null) {
      circleIcon.scaleType = ImageView.ScaleType.CENTER_CROP
      circleIcon.setImageDrawable(circleImage)
      circleIcon.setPadding(dp2Px(circlePadding), dp2Px(circlePadding), dp2Px(circlePadding), dp2Px(circlePadding))
      circleIcon.borderWidth = circleBorderSize.toInt()
      circleIcon.borderColor = circleBorderColor
    }
  }

  /** updates the recyclerView and circle icon. */
  private fun updateChildViews() {
    removeAllViews()
    if (orientation == SubmarineOrientation.HORIZONTAL) {
      if (circleAnchor == CircleAnchor.LEFT) {
        addCircleImageView()
        addHorizontalRecyclerView()
        recyclerView.x = getCircleViewAllocation().toFloat()
      } else {
        addHorizontalRecyclerView()
        addCircleImageView()
      }
    } else {
      if (circleAnchor == CircleAnchor.TOP) {
        addCircleImageView()
        addVerticalRecyclerView()
        recyclerView.y = getCircleViewAllocation().toFloat()
      } else {
        addVerticalRecyclerView()
        addCircleImageView()
      }
    }
    invalidate()
  }

  /** updates the view attributes following animation. */
  private fun updateAnimation() {
    when (submarineAnimation) {
      SubmarineAnimation.SCALE -> {
        scaleX = 0f
        scaleY = 0f
        visible(false)
      }
      SubmarineAnimation.FADE -> {
        alpha = 0f
        visible(false)
      }
      SubmarineAnimation.NONE -> {
        scaleX = 1f
        scaleY = 1f
        alpha = 1f
        visible(true)
      }
    }
  }

  /** draws circle icon view. */
  private fun addCircleImageView() {
    addView(circleIcon, dp2Px(circleSize), dp2Px(circleSize))
  }

  /** draws recyclerView horizontally. */
  private fun addHorizontalRecyclerView() {
    recyclerView.visible(false)
    recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    recyclerView.adapter = adapter
    addView(recyclerView, getRecyclerViewSize(), dp2Px(circleSize))
  }

  /** draws recyclerView vertically. */
  private fun addVerticalRecyclerView() {
    recyclerView.visible(false)
    recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    recyclerView.adapter = adapter
    addView(recyclerView, dp2Px(circleSize), getRecyclerViewSize())
  }

  /** floats the circle icon on the layout. */
  fun float() {
    if (!isFloating) {
      isFloating = true
      visible(true)

      when (submarineAnimation) {
        SubmarineAnimation.SCALE -> {
          animateScale(1.0f, 1.0f, duration / 2)
            .doAfterAnimate { autoNavigate() }
        }
        SubmarineAnimation.FADE -> {
          animateFade(1.0f, duration)
            .doAfterAnimate { autoNavigate() }
        }
        SubmarineAnimation.NONE -> autoNavigate()
      }
    }
  }

  /** spreads the navigation views and listing items. */
  fun navigate() {
    if (!isNavigating) {
      isNavigating = true
      recyclerView.visible(true)
      beginDelayedTransition(duration)
      if (orientation == SubmarineOrientation.HORIZONTAL) {
        updateWidthParams(expandSize, radius)
        if (circleAnchor == CircleAnchor.RIGHT) {
          circleIcon.translateX(duration, getRecyclerViewSize().toFloat())
        }
      } else if (orientation == SubmarineOrientation.VERTICAL) {
        updateHeightParams(expandSize, radius)
        if (circleAnchor == CircleAnchor.BOTTOM) {
          circleIcon.translateY(duration, getRecyclerViewSize().toFloat())
        }
      }
    }
  }

  /** folds the navigation views and un-lists items. */
  fun retreat() {
    if (isNavigating) {
      isNavigating = false
      beginDelayedTransition(duration)

      if (orientation == SubmarineOrientation.HORIZONTAL) {
        updateWidthParams(dp2Px(circleSize), 1000f)
        circleIcon.translateX(duration, 0f)
          .doAfterAnimate {
            if (!isNavigating) {
              recyclerView.visible(false)
              autoRetreat()
            }
          }
      } else if (orientation == SubmarineOrientation.VERTICAL) {
        updateHeightParams(dp2Px(circleSize), 1000f)
        circleIcon.translateY(duration, 0f)
          .doAfterAnimate {
            if (!isNavigating) {
              recyclerView.visible(false)
              autoRetreat()
            }
          }
      }
    }
  }

  /** dips the circle icon on the layer. */
  fun dip() {
    if (isFloating) {
      if (isNavigating && autoRetreat) {
        retreat()
      } else if (!isNavigating) {
        isFloating = false

        when (submarineAnimation) {
          SubmarineAnimation.SCALE -> {
            animateScale(0f, 0f, duration / 2)
              .doAfterAnimate {
                scaleX = 0f
                scaleY = 0f
                visible(false)
              }
          }
          SubmarineAnimation.FADE -> {
            animateFade(.0f, duration)
              .doAfterAnimate {
                alpha = 0f
                visible(false)
              }
          }
          SubmarineAnimation.NONE -> visible(false)
        }
      }
    }
  }

  /** navigates if the [autoNavigate] attribute is ture. */
  private fun autoNavigate() {
    if (autoNavigate) {
      navigate()
    }
  }

  /** dips if the [autoRetreat] attribute is true. */
  private fun autoRetreat() {
    if (autoRetreat) {
      dip()
    }
  }

  /** adds a [SubmarineItem] to the navigation adapter. */
  fun addSubmarineItem(submarineItem: SubmarineItem) {
    adapter.addItem(getWrapperItem(submarineItem))
  }

  /** adds a [SubmarineItem] list to the navigation adapter. */
  fun addSubmarineItems(submarineItems: List<SubmarineItem>) {
    for (submarineItem in submarineItems) {
      adapter.addItem(getWrapperItem(submarineItem))
    }
  }

  /** removes a [SubmarineItem] to the navigation adapter. */
  fun removeSubmarineItem(submarineItem: SubmarineItem) {
    adapter.removeItem(getWrapperItem(submarineItem))
  }

  /** clears all of the [SubmarineItem] on the navigation adapter. */
  fun clearAllSubmarineItems() {
    adapter.clearAllItems()
  }

  /** updates layout width params. */
  private fun updateWidthParams(value: Int, radius: Float) {
    updateLayoutParams {
      width = value
      drawable.cornerRadius = radius
    }
  }

  /** updates layout height params. */
  private fun updateHeightParams(value: Int, radius: Float) {
    updateLayoutParams {
      height = value
      drawable.cornerRadius = radius
    }
  }

  /** gets wrapped item [SubmarineItemWrapper]. */
  private fun getWrapperItem(submarineItem: SubmarineItem): SubmarineItemWrapper {
    return SubmarineItemWrapper(submarineItem, 0, getRecyclerViewSize(), orientation)
  }

  /** gets circle icon's size. */
  private fun getCircleViewAllocation(): Int {
    return dp2Px(circleSize)
  }

  /** gets recyclerView's size. */
  private fun getRecyclerViewSize(): Int {
    return expandSize - getCircleViewAllocation()
  }

  /** gets px size from the dp size. */
  private fun dp2Px(size: Int): Int {
    return context.dp2Px(size)
  }

  /** gets px size from the dp size. */
  private fun dp2Px(size: Float): Int {
    return context.dp2Px(size)
  }
}
