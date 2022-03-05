ToolTipDialog
=============
[![](https://jitpack.io/v/kcrimi/ToolTipDialog.svg)](https://jitpack.io/#kcrimi/ToolTipDialog) [![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-ToolTip%20Dialog-green.svg?style=flat )]( https://android-arsenal.com/details/1/8116 )

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
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kcrimi:ToolTipDialog:~1.1.4'
}
```
_Note: the `jitpack` repository is not necessary if it already exists in `allRepositories` in the project's `build.gradle`._

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

Title, subtitle, and content text views are hidden unless you set the content for them.

If you wish to only use 1 line of text, you should prefer to use the `title` since this will be centered within the dialog box.

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

## Custom styles

You can define a custom theme inheriting from `ToolTipDialog`, defining any of the following attributes for the styles you wish to override.

Eg changing fonts, colors, padding, margins, etc.

```xml
<style name="ToolTipDialogTheme.Custom">
        <item name="toolTipDialog.titleTextStyle">@style/custom_title_text</item>
        <item name="toolTipDialog.subtitleTextStyle">@style/custom_subtitle_text</item>
        <item name="toolTipDialog.bodyTextStyle">@style/custom_body_text</item>
        <item name="toolTipDialog.backgroundColor">@color/custom_background_color</item>
        <item name="toolTipDialog.dialogBoxStyle">@style/my_custom_dialog_box_style</item>
</style>
```
If you only want to make small tweaks, have your custom styles declare the stock styles as the parent.

Now you simply pass in this custom theme when creating a new ToolTipDialog

```kotlin
ToolTipDialog(this, this, R.style.TooltipDialogTheme_Custom)
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
---

This project is licensed under the terms of the Apache 2.0 license.
