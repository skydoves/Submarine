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
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Px
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

  private var orientation = SubmarineOrientation.HORIZONTAL
  private var circleAnchor = CircleAnchor.LEFT
  val circleIcon = CircleImageView(context)
  val recyclerView = RecyclerView(context)
  var autoNavigate = true
  var autoDip = true
  var duration = 350L

  @Px var circleSize = 52f
    set(value) {
      field = value
      updateSubmarine()
    }
  var circleImage: Drawable? = null
    set(value) {
      field = value
      updateSubmarine()
    }
  @Px var circlePadding = 4f
    set(value) {
      field = value
      updateSubmarine()
    }
  @Px var circleBorderSize = 0f
    set(value) {
      field = value
      updateSubmarine()
    }
  @ColorInt var circleBorderColor = ContextCompat.getColor(context, R.color.colorPrimary)
    set(value) {
      field = value
      updateSubmarine()
    }
  @Px var radius = dp2Px(48).toFloat()
    set(value) {
      field = value
      updateSubmarine()
    }
  @ColorInt var color = ContextCompat.getColor(context, R.color.colorPrimary)
    set(value) {
      field = value
      updateSubmarine()
    }
  @Px var borderSize = 0f
    set(value) {
      field = value
      updateSubmarine()
    }
  @ColorInt var borderColor = ContextCompat.getColor(context, R.color.colorPrimary)
    set(value) {
      field = value
      updateSubmarine()
    }
  @Px var expandSize = context.displaySize().x - dp2Px(30)
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

  constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context,
    attributeSet, defStyle) {
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
    val typedArray =
      context.obtainStyledAttributes(attributeSet, R.styleable.SubmarineView, defStyleAttr, 0)
    try {
      setTypeArray(typedArray)
    } finally {
      typedArray.recycle()
    }
  }

  private fun setTypeArray(a: TypedArray) {
    when (a.getInt(R.styleable.SubmarineView_submarine_orientation,
      SubmarineOrientation.HORIZONTAL.value)) {
      SubmarineOrientation.HORIZONTAL.value -> this.orientation = SubmarineOrientation.HORIZONTAL
      SubmarineOrientation.VERTICAL.value -> this.orientation = SubmarineOrientation.VERTICAL
    }
    when (a.getInt(R.styleable.SubmarineView_submarine_circleAnchor, CircleAnchor.LEFT.value)) {
      CircleAnchor.LEFT.value -> this.circleAnchor = CircleAnchor.LEFT
      CircleAnchor.RIGHT.value -> this.circleAnchor = CircleAnchor.RIGHT
      CircleAnchor.TOP.value -> this.circleAnchor = CircleAnchor.TOP
      CircleAnchor.BOTTOM.value -> this.circleAnchor = CircleAnchor.BOTTOM
    }
    when (a.getInt(R.styleable.SubmarineView_submarine_animation, SubmarineAnimation.NONE.value)) {
      SubmarineAnimation.NONE.value -> this.submarineAnimation = SubmarineAnimation.NONE
      SubmarineAnimation.FADE.value -> this.submarineAnimation = SubmarineAnimation.FADE
      SubmarineAnimation.SCALE.value -> this.submarineAnimation = SubmarineAnimation.SCALE
    }
    this.autoNavigate =
      a.getBoolean(R.styleable.SubmarineView_submarine_autoNavigate, this.autoNavigate)
    this.autoDip = a.getBoolean(R.styleable.SubmarineView_submarine_autoDip, this.autoDip)
    this.duration =
      a.getInt(R.styleable.SubmarineView_submarine_duration, this.duration.toInt()).toLong()
    this.circleSize =
      a.getDimension(R.styleable.SubmarineView_submarine_circleSize, this.circleSize)
    this.circleImage = a.getDrawable(R.styleable.SubmarineView_submarine_circleDrawable)
    this.circlePadding =
      a.getDimension(R.styleable.SubmarineView_submarine_circlePadding, this.circlePadding)
    this.circleBorderSize =
      a.getDimension(R.styleable.SubmarineView_submarine_circleBorderSize, this.circleBorderSize)
    this.circleBorderColor =
      a.getColor(R.styleable.SubmarineView_submarine_circleBorderColor, this.circleBorderColor)
    this.radius = a.getDimension(R.styleable.SubmarineView_submarine_radius, this.radius)
    this.color = a.getColor(R.styleable.SubmarineView_submarine_color, this.color)
    this.borderSize =
      a.getDimension(R.styleable.SubmarineView_submarine_borderSize, this.borderSize)
    this.borderColor = a.getColor(R.styleable.SubmarineView_submarine_borderColor, this.borderColor)
    this.expandSize =
      a.getDimension(R.styleable.SubmarineView_submarine_expandSize, this.expandSize.toFloat())
        .toInt()
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
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        updateLayoutParams {
          width = dp2Px(circleSize)
          height = dp2Px(circleSize)
        }
      }
    })
  }

  /** updates background and bordering. */
  private fun updateBackground() {
    background = GradientDrawable().apply {
      cornerRadius = radius
      setColor(this@SubmarineView.color)
      if (borderSize != 0f) {
        setStroke(borderSize.toInt(), borderColor)
      }
    }
  }

  /** updates the circle icon. */
  private fun updateCircleIcon() {
    if (circleImage != null) {
      with(circleIcon) {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setImageDrawable(circleImage)
        setPadding(dp2Px(circlePadding), dp2Px(circlePadding), dp2Px(circlePadding),
          dp2Px(circlePadding))
        borderWidth = circleBorderSize.toInt()
        borderColor = circleBorderColor
      }
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
              autoDip()
            }
          }
      } else if (orientation == SubmarineOrientation.VERTICAL) {
        updateHeightParams(dp2Px(circleSize), 1000f)
        circleIcon.translateY(duration, 0f)
          .doAfterAnimate {
            if (!isNavigating) {
              recyclerView.visible(false)
              autoDip()
            }
          }
      }
    }
  }

  /** dips the circle icon on the layer. */
  fun dip() {
    if (isFloating) {
      if (isNavigating && autoDip) {
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

  /** dips if the [autoDip] attribute is true. */
  private fun autoDip() {
    if (autoDip) {
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
      if (background is GradientDrawable) {
        (background as GradientDrawable).cornerRadius = radius
      }
    }
  }

  /** updates layout height params. */
  private fun updateHeightParams(value: Int, radius: Float) {
    updateLayoutParams {
      height = value
      if (background is GradientDrawable) {
        (background as GradientDrawable).cornerRadius = radius
      }
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
    val params = layoutParams
    var width = expandSize - getCircleViewAllocation()
    if (params is MarginLayoutParams) {
      val margins = (params.leftMargin + params.rightMargin)
      val space = context.displaySize().x - expandSize - margins
      if (space < 0) width += space
    }
    return width
  }

  /** gets px size from the dp size. */
  private fun dp2Px(@Dimension size: Int): Int {
    return context.dp2Px(size)
  }

  /** gets px size from the dp size. */
  private fun dp2Px(@Dimension size: Float): Int {
    return context.dp2Px(size)
  }
}
