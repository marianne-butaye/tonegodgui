/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tonegod.gui.controls.buttons;

import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import tonegod.gui.core.ElementManager;
import tonegod.gui.core.utils.UIDUtil;

/**
 *
 * @author t0neg0d
 */
public class ButtonAdapter extends Button {

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     */
    public ButtonAdapter(ElementManager screen) {
        this(screen, UIDUtil.getUID(), Vector2f.ZERO,
                screen.getStyle(defaultStyleName).getVector2f("defaultSize"),
                screen.getStyle(defaultStyleName).getVector4f("resizeBorders"),
                screen.getStyle(defaultStyleName).getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, String styleName) {
        this(screen, UIDUtil.getUID(), Vector2f.ZERO,
                screen.getStyle(styleName).getVector2f("defaultSize"),
                screen.getStyle(styleName).getVector4f("resizeBorders"),
                screen.getStyle(styleName).getString("defaultImg"),
                styleName
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     */
    public ButtonAdapter(ElementManager screen, Vector2f position) {
        this(screen, UIDUtil.getUID(), position,
                screen.getStyle(defaultStyleName).getVector2f("defaultSize"),
                screen.getStyle(defaultStyleName).getVector4f("resizeBorders"),
                screen.getStyle(defaultStyleName).getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, Vector2f position, String styleName) {
        this(screen, UIDUtil.getUID(), position,
                screen.getStyle(styleName).getVector2f("defaultSize"),
                screen.getStyle(styleName).getVector4f("resizeBorders"),
                screen.getStyle(styleName).getString("defaultImg"),
                styleName
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     */
    public ButtonAdapter(ElementManager screen, Vector2f position, Vector2f dimensions) {
        this(screen, UIDUtil.getUID(), position, dimensions,
                screen.getStyle(defaultStyleName).getVector4f("resizeBorders"),
                screen.getStyle(defaultStyleName).getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, Vector2f position, Vector2f dimensions, String styleName) {
        this(screen, UIDUtil.getUID(), position, dimensions,
                screen.getStyle(styleName).getVector4f("resizeBorders"),
                screen.getStyle(styleName).getString("defaultImg"),
                styleName
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param resizeBorders A Vector4f containing the border information used
     * when resizing the default image (x = N, y = W, z = E, w = S)
     * @param defaultImg The default image to use for the Button
     */
    public ButtonAdapter(ElementManager screen, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg) {
        this(screen, UIDUtil.getUID(), position, dimensions, resizeBorders, defaultImg);
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param resizeBorders A Vector4f containing the border information used
     * when resizing the default image (x = N, y = W, z = E, w = S)
     * @param defaultImg The default image to use for the Button
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg, String styleName) {
        this(screen, UIDUtil.getUID(), position, dimensions, resizeBorders, defaultImg, styleName);
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     */
    public ButtonAdapter(ElementManager screen, String UID, Vector2f position) {
        this(screen, UID, position,
                screen.getStyle(defaultStyleName).getVector2f("defaultSize"),
                screen.getStyle(defaultStyleName).getVector4f("resizeBorders"),
                screen.getStyle(defaultStyleName).getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, String UID, Vector2f position, String styleName) {
        this(screen, UID, position,
                screen.getStyle(styleName).getVector2f("defaultSize"),
                screen.getStyle(styleName).getVector4f("resizeBorders"),
                screen.getStyle(styleName).getString("defaultImg"),
                styleName
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     */
    public ButtonAdapter(ElementManager screen, String UID, Vector2f position, Vector2f dimensions) {
        this(screen, UID, position, dimensions,
                screen.getStyle(defaultStyleName).getVector4f("resizeBorders"),
                screen.getStyle(defaultStyleName).getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, String UID, Vector2f position, Vector2f dimensions, String styleName) {
        this(screen, UID, position, dimensions,
                screen.getStyle(styleName).getVector4f("resizeBorders"),
                screen.getStyle(styleName).getString("defaultImg"),
                styleName
        );
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param resizeBorders A Vector4f containing the border information used
     * when resizing the default image (x = N, y = W, z = E, w = S)
     * @param defaultImg The default image to use for the Button
     */
    public ButtonAdapter(ElementManager screen, String UID, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg) {
        this(screen, UID, position, dimensions, resizeBorders, defaultImg, defaultStyleName);
    }

    /**
     * Creates a new instance of the Button control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param resizeBorders A Vector4f containing the border information used
     * when resizing the default image (x = N, y = W, z = E, w = S)
     * @param defaultImg The default image to use for the Button
     * @param styleName The name of the style to use on the Element
     */
    public ButtonAdapter(ElementManager screen, String UID, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg, String styleName) {
        super(screen, UID, position, dimensions, resizeBorders, defaultImg);
    }

    @Override
    public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
    }

    @Override
    public void onButtonMouseRightDown(MouseButtonEvent evt, boolean toggled) {
    }

    @Override
    public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
    }

    @Override
    public void onButtonMouseRightUp(MouseButtonEvent evt, boolean toggled) {
    }

    @Override
    public void onButtonFocus(MouseMotionEvent evt) {
    }

    @Override
    public void onButtonLostFocus(MouseMotionEvent evt) {
    }
}
