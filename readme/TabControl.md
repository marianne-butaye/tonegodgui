# TabControl Class

The TabControl class extends Element. It implements a tab control in which combinations of a tab button and a panel can be added.

This creates a tab control with buttons and panels (which can be scrollable). By default it cannot be resized or removed. See panel methods to modify its behavior. Only one tab can be selected at a time.

#### Warning
At least one tab panel must be added to the control for it to work without errors [see *addTab* functions below].

## Table of Contents
1. [Functionnalities](#functionnalities)
2. [Constructors](#constructors)
3. [Principal Methods](#principal-methods)

## Functionnalities
* **TabSlider :** If some buttons could not be entirely displayed by the control, add a button on one end of the tab control buttons. When clicked, slide the buttons to see the other end of the control.
* **Orientation :** The tab control can display its buttons horizontally or vertically.

## Constructors
This class is abstract meaning the method **_onTabSelect_** must be **overriden**.

#### Constructor 1:

```java
/**
  * ElementManager screen
  */

TabControl tabControl = new TabControl(screen)
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
```

#### Constructor 2:
```java
/**
  * ElementManager screen
  * Vector2f position
  */

TabControl tabControl = new TabControl(screen, new Vector2f(15, 15))
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  */

TabControl tabControl = new TabControl(screen, "tab control", new Vector2f(15, 15))
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
```

#### Constructor 3:
```java
/**
  * ElementManager screen
  * Vector2f position
  * Vector2f dimensions
  */

TabControl tabControl = new TabControl(screen, new Vector2f(15, 15), new Vector2f(300, 400))
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
```
```java
/**
  * ElementManager screen
  * String UID
  * Vector2f position
  * Vector2f dimensions
  */

TabControl tabControl = new TabControl(screen, "tab control", new Vector2f(15, 15), new Vector2f(300, 400))
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
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

TabControl tabControl = new TabControl(screen, new Vector2f(15, 15), new Vector2f(300, 400), new Vector4f(4, 4, 4, 4), "tonegod/gui/style/def/Tabs/tab_x_u.png")
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
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

TabControl tabControl = new TabControl(screen, "tab control", new Vector2f(15, 15), new Vector2f(300, 400), new Vector4f(4, 4, 4, 4), "tonegod/gui/style/def/Tabs/tab_x_u.png")
{
    @Override
    public void onTabSelect(int i) {
        // Do something...
    }
};
```

## Principal Methods

* **void onTabSelect(int index) :** Method to override.


* **Element getTab(int index) :** Get a specific panel.


* **void addTab(String title)**
* **void addTab(String title, ButtonAdapter tab)**
* **void addTab(String title, boolean useScrollPanel)**
* **void addTab(String title, ButtonAdapter tab, boolean useScrollPanel, boolean isCustomButton)**


* **void addTabChild(int index, Element element)**
* **void setSelectedTab(int index)**
* **void setTabSize(float size)**
* **void setUseSlideEffect(boolean useSlideEffect)** : enable the SliderToEffect of the SlideTray
