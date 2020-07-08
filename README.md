ToolTipDialog
=============
[ ![Download](https://api.bintray.com/packages/kcrimi/oss_android/com.kcrimi.tooltipdialog/images/download.svg) ](https://bintray.com/kcrimi/oss_android/com.kcrimi.tooltipdialog/_latestVersion) [![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-ToolTip%20Dialog-green.svg?style=flat )]( https://android-arsenal.com/details/1/8116 )

An easy-to-use dialog to add tooltips to your app with teh ability to point to specific screen
 locations and also highlight views on-screen.

Great for on-boarding, calling out new features, or simply calling out bits of UI.

<img src="https://github.com/kcrimi/ToolTipDialog/raw/master/assets/Tooltip%20demo.gif" width="50%">

Features
--------

* Show a default dialog pop up banner
* Align the dialog to a certain vertical location on screen
* Point to a specific element on-screen 
* Highlight specific UI elements by letting them "peek through" a background shade
 
Gradle Setup
------
In your app's `build.gradle` add 

```Groovy
repositories {
    jcenter()
}

dependencies {
    implementation 'com.kcrimi.tooltipdialog:tooltipdialog:1.0.0'
}
```
_Note: the `jcenter()` repository is not necessary if it already exists in `allRepositories` in the project's `build.gradle`._

Usage
-----
The below are shown as called by an Activity

## Basic

```kotlin
ToolTipDialog(this, this)
  .title("This is a basic dialog") // Define the title for the tooltip
  .content("This dialog will show at the top of the screen by default") // Body content 
  .subtitle("Subtitle for tooltip") // Subtitle on the tooltip
  .setToolTipListener(toolTipListener) // Define the listener for clicks on the tooltip
  .show() // Build and show the tooltip
```

## Vertically positioned

Great for underlining an element or section of you UI.

```kotlin
ToolTipDialog(this, this)
  ...
  .setYPosition(720) // Align the top of the dialog with this position on screen
  .show()
```

## Point to specific location

You can also point to a specific point on screen. Great for calling out buttons and improving discoverability of buried features.

```kotlin
ToolTipDialog(this, this)
  ...
  .pointTo(250, 720) // Point to the specific X,Y position on-screen
  .show()
```

View the sample app for how to point to specific views.

## Peek-through views

Draw a typical shade behind your dialog but let specific views peek through.

```kotlin
ToolTipDialog(this, this)
  ...
  .addPeekThroughView(myCoolButton)
  .show()
```

You can also add as many of these as you'd like

```kotlin
ToolTipDialog(this, this)
  ...
  .addPeekThroughView(myCoolButton)
  .addPeekThroughView(myOtherButton)
  .addPeekThroughView(myAwesomeImage)
  .show()
```

FAQ
---

**My view keeps saying it's at `x:0, y:0`, making the dialog to point to the wrong spot**

You'll need to make sure that you let the system have time to inflate the layout or else the views won't have their proper sizes/dimenstions.

If you need to show the tooltip  on an early lifecycle event like `onCreate()` or `onViewCreated()`, yout can usually solve this issue by adding an additional delay or, more reliably, adding a ViewTreeObserver.

```kotlin
contentView.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener,
            ViewTreeObserver.OnGlobalFocusChangeListener {
            override fun onGlobalLayout() {
                contentView.viewTreeObserver.removeOnGlobalFocusChangeListener(this)
                // Show Dialog here
            }
            
            ...
        })
```

