# Button Class

The Button class extends Element.
Buttons are an abstract class providing methods for handling user input

## Table of Contents
1. [Functionnalities](#functionnalities)
2. [Constructors](#constructors)
3. [Principal Methods](#principal-methods)

## Functionnalities
* **Appearance :** Buttons can either consist of a text label, an icon or both.
* **States :** Buttons have a default state, a hover state and a pressed state.
* **Listeners interfaces :** Buttons implement the tonegodGUI MouseButtonListener & MouseFocusListener interfaces
* **Events :** Buttons provide an optional stillPressed event
* **Modes :** Buttons can be set to Toggle mode.
* **Effects :** Buttons have default effects set for Hover, Pressed & LoseFocus
* **Enable/Disable :** Buttons can be enabled/disabled (no response to events)
and also grayed out (disabled with their appearance changed for the user).
* **Styles :** Buttons can be initialised with different styles (declared in your style_map.gui.xml).
You can then have buttons with different styles in your application.


## Constructors
This class is abstract meaning the methods **_onButtonMouseLeftDown_**, **_onButtonMouseRightDown_**, **_onButtonMouseLeftUp_**, **_onButtonMouseRightUp_**, **_onButtonFocus_**, **_onButtonLostFocus_** must be **overriden**.
The class *ButtonAdapter* (same constructors and methods) is the same as Button but is not abstract.

#### Constructor 1:

```java
/**
  * ElementManager screen
  */

Button button = new ButtonAdapter(screen);
```
```java
/**
  * ElementManager screen
  * String styleName
  */

Button button = new ButtonAdapter(screen, "Button");
```

#### Constructor 2:
```java
/**
  * ElementManager screen
  * Vector2f position
  */

Button button = new ButtonAdapter(screen, new Vector2f(15, 15));
```
```java
/**
  * ElementManager screen
  * Vector2f position
  * String styleName
  */

Button button = new ButtonAdapter(screen, new Vector2f(15, 15), "Button");
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  */

Button button = new ButtonAdapter(screen, "button", new Vector2f(15, 15));
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  * String styleName
  */

Button button = new ButtonAdapter(screen, "button", new Vector2f(15, 15), "Button");
```

#### Constructor 3:
```java
/**
  * ElementManager screen
  * Vector2f position
  * Vector2f dimensions
  */

Button button = new ButtonAdapter(screen, new Vector2f(15, 15), new Vector2f(300, 400));
```
```java
/**
  * ElementManager screen
  * Vector2f position
  * Vector2f dimensions
  * String styleName
  */

Button button = new ButtonAdapter(screen, new Vector2f(15, 15), new Vector2f(300, 400), "Button");
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  * Vector2f dimensions
  */

Button button = new ButtonAdapter(screen, "button", new Vector2f(15, 15), new Vector2f(300, 400));;
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  * Vector2f dimensions
  * String styleName
  */

Button button = new ButtonAdapter(screen, "button", new Vector2f(15, 15), new Vector2f(300, 400), "Button");;
```

#### Constructor 4:
```java
/**
  * ElementManager screen
  * Vector2f position
  * Vector2f dimensions
  * Vector4f resizeBorders
  * String defaultImg
  */

Button button = new ButtonAdapter(screen, new Vector2f(15, 15), new Vector2f(300, 400), new Vector4f(4, 4, 4, 4), "tonegod/gui/style/def/Button/button_x_u.png");
```
```java
/**
  * ElementManager screen
  * Vector2f position
  * Vector2f dimensions
  * Vector4f resizeBorders
  * String defaultImg
  * String styleName
  */

Button button = new ButtonAdapter(screen, new Vector2f(15, 15), new Vector2f(300, 400), new Vector4f(4, 4, 4, 4), "tonegod/gui/style/def/Button/button_x_u.png", "Button");
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  * Vector2f dimensions
  * Vector4f resizeBorders
  * String defaultImg
  */

Button button = new ButtonAdapter(screen, "button", new Vector2f(15, 15), new Vector2f(300, 400), new Vector4f(4, 4, 4, 4), "tonegod/gui/style/def/Button/button_x_u.png");
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  * Vector2f dimensions
  * Vector4f resizeBorders
  * String defaultImg
  * String styleName
  */

Button button = new ButtonAdapter(screen, "button", new Vector2f(15, 15), new Vector2f(300, 400), new Vector4f(4, 4, 4, 4), "tonegod/gui/style/def/Button/button_x_u.png", "Button");
```

## Principal Methods

* **boolean getIsToggled()**
* **boolean getIsStillPressed()**


* **void setText(String text)**
* **void setLabelText(String text)**
* **void setFontColor(ColorRGBA fontColor, boolean makeDefault)**
* **void setButtonIcon(float width, float height, String texturePath)**


* **void setTabFocus()**
* **void setIsGrayedOut(boolean isGrayedOut)**
* **void setIsToggleButton(boolean isToggleButton)**
* **void setIsRadioButton(boolean isRadioButton) :** Radio Button = click once to activate and stays active


* **void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) :** abstract
* **void onButtonMouseRightDown(MouseButtonEvent evt, boolean toggled) :** abstract
* **void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) :** abstract
* **void onButtonMouseRightUp(MouseButtonEvent evt, boolean toggled) :** abstract
* **void onButtonFocus(MouseMotionEvent evt) :** abstract
* **void onButtonLostFocus(MouseMotionEvent evt) :** abstract
