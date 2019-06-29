# Submarine

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=19"><img alt="API" src="https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://travis-ci.org/skydoves/Submarine"><img alt="API" src="https://travis-ci.org/skydoves/Submarine.svg?branch=master"/></a>
  <a href="https://skydoves.github.io/libraries/submarine/javadoc/submarnie/com.skydoves.submarine/index.html"><img alt="Javadoc" src="https://img.shields.io/badge/Javadoc-Submarine-yellow.svg"/></a>
</p>

<p align="center">
Fully customizable floating navigation view for listing items dynamically on Android.
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/24237865/60386446-c4ab9e00-9acf-11e9-8e58-02f758f9667c.gif"/>
<img src="https://user-images.githubusercontent.com/24237865/60386447-c4ab9e00-9acf-11e9-872a-dfd6ca23ad4f.gif"/>
</p>

## Including in your project 

### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.skydoves:submarine:1.0.0"
}
```

## Usage
Add following XML namespace inside your XML layout file.

```gradle
xmlns:app="http://schemas.android.com/apk/res-auto"
```

### SubmarineView
Here is how to implement like previews using `SubmarineView`.
```gradle
<com.skydoves.submarine.SubmarineView
  android:id="@+id/submarineView"
  android:layout_width="50dp" // this will not affect on real view size. We can set anything.
  android:layout_height="50dp" //  this will not affect real view size. We can set anything.
  android:layout_alignParentBottom="true"
  android:layout_margin="20dp"
  android:alpha="0.8"
  app:submarine_animation="scale" // floating, dipping animation style.
  app:submarine_borderColor="@color/colorPrimaryDark" // navigation's border color.
  app:submarine_borderSize="1dp" // navigation's border thickness.
  app:submarine_circleAnchor="right" // decides where to moves the circle icon.
  app:submarine_circleBorderColor="@color/green" // circle icon's border color.
  app:submarine_circleBorderSize="1dp" // circle icon's border thickness.
  app:submarine_circleDrawable="@drawable/picasso" // circle icon's image drawable.
  app:submarine_circlePadding="2dp" // circle icon's padding value.
  app:submarine_circleSize="22dp" // circle icon's size. It decides the height or width of navigation.
  app:submarine_color="@android:color/black" // navigation's background color.
  app:submarine_duration="350" // navigation spreading duration.
  app:submarine_orientation="horizontal" // navigation spreading orientation.
/>
```

### Float and Dip
Shows and makes disappear using the below methods.
```kotlin
submarineView.float() // shows
submarineView.dip() // makes disappear
```

### SubmarineItem
SubmarineItem is a data class for composing navigation list.
```kotlin
val item = SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_edit))
submarineView.addSubmarineItem(item)
```
We can customize the icon drawable using `IconForm.Builder`.
```kotlin
val iconForm = IconForm.Builder(context)
  .setIconSize(45)
  .setIconTint(ContextCompat.getColor(context, R.color.colorPrimary))
  .setIconScaleType(ImageView.ScaleType.CENTER)
  .build()

// kotlin dsl
val iconForm = iconForm(context) {
  iconSize = 45
  iconColor = ContextCompat.getColor(context, R.color.colorPrimary)
  iconScaleType = ImageView.ScaleType.CENTER
}

val item = SubmarineItem(ContextCompat.getDrawable(this, R.drawable.ic_edit), iconForm)
submarineView.addSubmarineItem(item)
```
We can add `SubmarineItem` list, remove and clear all items.
```kotlin
submarineView.addSubmarineItem(item) // adds a SubmarineItem item.
submarineView.addSubmarineItems(itemList) // adds a SubmarineItem item list.
submarineView.removeSubmarineItem(item) // removes a SubmarineItem item.
submarineView.clearAllSubmarineItems() // clears all SubmarineItem items.
```

### SubmarineItemClickListener
Interface definition for a callback to be invoked when a item is clicked.
```kotlin
submarineView.submarineItemClickListener = onItemClickListener

private object onItemClickListener: SubmarineItemClickListener {
  override fun onItemClick(position: Int, submarineItem: SubmarineItem) {
    // doSomething
  }
}
```

### Circle Icon
We can customize the circle icon on the navigation. <br>
Submarine uses [CircleImageView](https://github.com/hdodenhof/CircleImageView). So you can reference the usage of it.
```kotlin
submarineView.circleIcon.setImageDrawable(drawable) // gets circle icon and changes image drawable.
submarineView.circleIcon.borderColor = ContextCompat.getColor(context, R.color.colorPrimary) // gets circle icon and changes border color.
submarineView.circleIcon.borderWidth = 2 // gets circle icon and changes border width.
```

### SubmarineCircleClickListener
Interface definition for a callback to be invoked when a circle icon is clicked.
```kotlin
submarineView.submarineCircleClickListener = onCircleIconClickListener

private object onCircleIconClickListener: SubmarineCircleClickListener {
  override fun onCircleClick() {
    // doSomething
  }
}
```

### Orientation and Anchor
`SubmarineOrientation` and `CircleAnchor` decide the where to spreads and where to moves.
```gradle
app:submarine_circleAnchor="right" // left, right, top, bottom
app:submarine_orientation="horizontal" // horizontal, vertical
```
Horizontal and Left | Horizontal and Right
------------ | -------------
![or00](https://user-images.githubusercontent.com/24237865/60387040-bc0b9580-9ad8-11e9-9f5b-8b3f634a8a91.gif)| ![or01](https://user-images.githubusercontent.com/24237865/60387041-bc0b9580-9ad8-11e9-927f-0d0758b553de.gif)

Vertical and Top | Vertical and Bottom
------------ | -------------
![or03](https://user-images.githubusercontent.com/24237865/60387043-bca42c00-9ad8-11e9-82a1-3d6cd7b10931.gif)| ![or02](https://user-images.githubusercontent.com/24237865/60387042-bca42c00-9ad8-11e9-8e17-5d9d843bb99d.gif)

### SubmarineAnimation
SubmarineAnimation decides the animation of `float` and `dip` methods.
```gradle
app:submarine_animation="scale"
```
SCALE | FADE
------------ | -------------
![or00](https://user-images.githubusercontent.com/24237865/60387040-bc0b9580-9ad8-11e9-9f5b-8b3f634a8a91.gif)| ![or05](https://user-images.githubusercontent.com/24237865/60387168-47395b00-9ada-11e9-93d1-5957b89bd677.gif)

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/submarine/stargazers)__ for this repository. :star:

# License
```xml
Copyright 2019 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
