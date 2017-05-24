package tonegod.gui.controls.text;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.List;
import tonegod.gui.core.Element;
import tonegod.gui.core.ElementManager;
import tonegod.gui.core.Screen;
import tonegod.gui.core.utils.UIDUtil;
import tonegod.gui.listeners.KeyboardListener;
import tonegod.gui.listeners.MouseButtonListener;
import tonegod.gui.listeners.MouseFocusListener;
import tonegod.gui.listeners.TabFocusListener;

/**
 *
 * @author marianne-butaye
 */
public class TextArea extends TextField implements Control, KeyboardListener, TabFocusListener, MouseFocusListener, MouseButtonListener {

    protected int lineNumber = 1;
    protected float orgY = 0;
    
    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     */
    public TextArea(ElementManager screen) {
        this(screen, UIDUtil.getUID(), Vector2f.ZERO,
                screen.getStyle("TextField").getVector2f("defaultSize"),
                screen.getStyle("TextField").getVector4f("resizeBorders"),
                screen.getStyle("TextField").getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     */
    public TextArea(ElementManager screen, Vector2f position) {
        this(screen, UIDUtil.getUID(), position,
                screen.getStyle("TextField").getVector2f("defaultSize"),
                screen.getStyle("TextField").getVector4f("resizeBorders"),
                screen.getStyle("TextField").getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     */
    public TextArea(ElementManager screen, Vector2f position, Vector2f dimensions) {
        this(screen, UIDUtil.getUID(), position, dimensions,
                screen.getStyle("TextField").getVector4f("resizeBorders"),
                screen.getStyle("TextField").getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param resizeBorders A Vector4f containg the border information used when
     * resizing the default image (x = N, y = W, z = E, w = S)
     * @param defaultImg The default image to use for the TextField
     */
    public TextArea(ElementManager screen, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg) {
        this(screen, UIDUtil.getUID(), position, dimensions, resizeBorders, defaultImg);
    }

    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     */
    public TextArea(ElementManager screen, String UID, Vector2f position) {
        this(screen, UID, position,
                screen.getStyle("TextField").getVector2f("defaultSize"),
                screen.getStyle("TextField").getVector4f("resizeBorders"),
                screen.getStyle("TextField").getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     */
    public TextArea(ElementManager screen, String UID, Vector2f position, Vector2f dimensions) {
        this(screen, UID, position, dimensions,
                screen.getStyle("TextField").getVector4f("resizeBorders"),
                screen.getStyle("TextField").getString("defaultImg")
        );
    }

    /**
     * Creates a new instance of the TextField control
     *
     * @param screen The screen control the Element is to be added to
     * @param UID A unique String identifier for the Element
     * @param position A Vector2f containing the x/y position of the Element
     * @param dimensions A Vector2f containing the width/height dimensions of
     * the Element
     * @param resizeBorders A Vector4f containg the border information used when
     * resizing the default image (x = N, y = W, z = E, w = S)
     * @param defaultImg The default image to use for the Slider's track
     */
    public TextArea(ElementManager screen, String UID, Vector2f position, Vector2f dimensions, Vector4f resizeBorders, String defaultImg) {
        super(screen, UID, position, dimensions, resizeBorders, defaultImg);

        this.setScaleEW(true);
        this.setScaleNS(false);
        this.setDocking(Docking.NW);

        compareClick = screen.getApplication().getTimer().getTimeInSeconds();

        float padding = screen.getStyle("TextField").getFloat("textPadding");

        caret = new Element(screen, UID + ":Caret", new Vector2f(padding, padding), new Vector2f(dimensions.x - (padding * 2), dimensions.y - (padding * 2)), new Vector4f(0, 0, 0, 0), null);
        if (screen.getStyle("TextField").getFloat("fontSize") - padding * 2 <= dimensions.y) {
            caret.setHeight(screen.getStyle("TextField").getFloat("fontSize"));
        }

        caretMat = caret.getMaterial().clone();
        caretMat.setBoolean("IsTextField", true);
        caretMat.setTexture("ColorMap", null);
        caretMat.setColor("Color", getFontColor());

        caret.setLocalMaterial(caretMat);
        caret.setIgnoreMouse(true);
        caret.setScaleEW(true);
        caret.setScaleNS(false);
        caret.setDocking(Docking.SW);
        
        this.setFontSize(screen.getStyle("TextField").getFloat("fontSize"));
        this.setTextPadding(padding);
        this.setTextWrap(LineWrapMode.valueOf(screen.getStyle("TextField").getString("textWrap")));
        this.setTextAlign(BitmapFont.Align.valueOf(screen.getStyle("TextField").getString("textAlign")));
        this.setTextVAlign(BitmapFont.VAlign.valueOf(screen.getStyle("TextField").getString("textVAlign")));

        this.setMinDimensions(dimensions.clone());

        setTextFieldFontColor(screen.getStyle("TextField").getColorRGBA("fontColor"));
        this.addChild(caret);
        this.updateText("");

        orgY = caret.getY();

        populateEffects("TextField");
    }

    // Interaction
    @Override
    public void onKeyPress(KeyInputEvent evt) {
        System.out.println("onKeyPress");
        if (evt.getKeyCode() == KeyInput.KEY_F1 || evt.getKeyCode() == KeyInput.KEY_F2
                || evt.getKeyCode() == KeyInput.KEY_F3 || evt.getKeyCode() == KeyInput.KEY_F4
                || evt.getKeyCode() == KeyInput.KEY_F5 || evt.getKeyCode() == KeyInput.KEY_F6
                || evt.getKeyCode() == KeyInput.KEY_F7 || evt.getKeyCode() == KeyInput.KEY_F8
                || evt.getKeyCode() == KeyInput.KEY_F9 || evt.getKeyCode() == KeyInput.KEY_CAPITAL
                || evt.getKeyCode() == KeyInput.KEY_ESCAPE || evt.getKeyCode() == KeyInput.KEY_TAB) {
        } else if (evt.getKeyCode() == KeyInput.KEY_LCONTROL || evt.getKeyCode() == KeyInput.KEY_RCONTROL) {
            ctrl = true;
        } else if (evt.getKeyCode() == KeyInput.KEY_LSHIFT || evt.getKeyCode() == KeyInput.KEY_RSHIFT) {
            shift = true;
        } else if (evt.getKeyCode() == KeyInput.KEY_LMENU || evt.getKeyCode() == KeyInput.KEY_RMENU) {
            alt = true;
        } else if (evt.getKeyCode() == KeyInput.KEY_LMETA || evt.getKeyCode() == KeyInput.KEY_RMETA) {
            meta = true;
        } else if (evt.getKeyCode() == KeyInput.KEY_DELETE) {
            if (rangeHead != -1 && rangeTail != -1) {
                editTextRangeText("");
            } else {
                if (caretIndex < finalText.length()) {
                    textFieldText.remove(caretIndex);
                }
            }
        } else if (evt.getKeyCode() == KeyInput.KEY_BACK) {
            if (rangeHead != -1 && rangeTail != -1) {
                editTextRangeText("");
            } else {
                if (caretIndex > 0) {
                    textFieldText.remove(caretIndex - 1);
                    caretIndex--;
                }
            }
        } else if (evt.getKeyCode() == KeyInput.KEY_LEFT) {
            if (!shift) {
                resetTextRange();
            }
            if (caretIndex > -1) {
                if (Screen.isMac()) {
                    if (meta) {
                        caretIndex = 0;
                        getVisibleText();
                        if (shift) {
                            setTextRangeEnd(caretIndex);
                        } else {
                            resetTextRange();
                            setTextRangeStart(caretIndex);
                        }
                        return;
                    }
                }

                if ((Screen.isMac() && !alt)
                        || (Screen.isWindows() && !ctrl)
                        || (Screen.isUnix() && !ctrl)
                        || (Screen.isSolaris() && !ctrl)) {
                    caretIndex--;
                } else {
                    int cIndex = caretIndex;
                    if (cIndex > 0) {
                        if (finalText.charAt(cIndex - 1) == ' ') {
                            cIndex--;
                        }
                    }
                    int index = 0;
                    if (cIndex > 0) {
                        index = finalText.substring(0, cIndex).lastIndexOf(' ') + 1;
                    }
                    if (index < 0) {
                        index = 0;
                    }
                    caretIndex = index;
                }
                if (caretIndex < 0) {
                    caretIndex = 0;
                }

                if (!shift) {
                    setTextRangeStart(caretIndex);
                }
            }
        } else if (evt.getKeyCode() == KeyInput.KEY_RIGHT) {
            if (!shift) {
                resetTextRange();
            }
            if (caretIndex <= textFieldText.size()) {
                if (Screen.isMac()) {
                    if (meta) {
                        caretIndex = textFieldText.size();
                        getVisibleText();
                        if (shift) {
                            setTextRangeEnd(caretIndex);
                        } else {
                            resetTextRange();
                            setTextRangeStart(caretIndex);
                        }
                        return;
                    }
                }

                if ((Screen.isMac() && !alt)
                        || (Screen.isWindows() && !ctrl)
                        || (Screen.isUnix() && !ctrl)
                        || (Screen.isSolaris() && !ctrl)) {
                    caretIndex++;
                } else {
                    int cIndex = caretIndex;
                    if (cIndex < finalText.length()) {
                        if (finalText.charAt(cIndex) == ' ') {
                            cIndex++;
                        }
                    }
                    int index;
                    if (cIndex < finalText.length()) {
                        index = finalText.substring(cIndex, finalText.length()).indexOf(' ');
                        if (index == -1) {
                            index = finalText.length();
                        } else {
                            index += cIndex;
                        }
                    } else {
                        index = finalText.length();
                    }
                    caretIndex = index;
                }
                if (caretIndex > finalText.length()) {
                    caretIndex = finalText.length();
                }

                if (!shift) {
                    if (caretIndex < textFieldText.size()) {
                        setTextRangeStart(caretIndex);
                    } else {
                        setTextRangeStart(textFieldText.size());
                    }
                }
            }
        } else if (evt.getKeyCode() == KeyInput.KEY_END || evt.getKeyCode() == KeyInput.KEY_NEXT) {
            caretIndex = textFieldText.size();
            getVisibleText();
            if (shift) {
                setTextRangeEnd(caretIndex);
            } else {
                resetTextRange();
                setTextRangeStart(caretIndex);
            }
        } else if(evt.getKeyCode() == KeyInput.KEY_DOWN || evt.getKeyCode() == KeyInput.KEY_UP) {
            int indexSinceStartOfLine = 0, currentLine = 1, goal = whichLineNumber();
            List<Integer> lastLineSizes = new ArrayList<>();
            for(int i = 0, j = -1; i < textFieldText.size(); i++)
            {
                char c = textFieldText.get(i).charAt(0);
                if(goal == currentLine && caretIndex == i + 1)
                {
                    indexSinceStartOfLine = caretIndex;
                    for(int lineSize : lastLineSizes)
                    {
                        indexSinceStartOfLine -= (lineSize + 1); 
                    }   
                }
                if(c == '\n' || textFieldText.size() == i+1)
                {
                    currentLine++;
                    if(j >= 0)
                        lastLineSizes.add(i - lastLineSizes.get(j));
                    else
                        lastLineSizes.add(i);
                    j++;
                }
            }            
                    
            caretIndex = indexSinceStartOfLine;
            currentLine = 1;
            
            if(evt.getKeyCode() == KeyInput.KEY_UP)
            {
                for(int lineSize : lastLineSizes)
                {
                    if(currentLine < goal - 1)
                    {
                        currentLine++;
                        caretIndex += lineSize + 1;
                    }
                    else
                        break;
                }
                if(goal-1 >= 0 && lastLineSizes.size() >= goal-1 && lastLineSizes.get(goal-2) < indexSinceStartOfLine)
                    caretIndex -= (indexSinceStartOfLine - lastLineSizes.get(goal-2));
            }
            else
            {
                for(int lineSize : lastLineSizes)
                {
                    if(currentLine <= goal)
                    {
                        currentLine++;
                        caretIndex += lineSize + 1;
                    }
                    else
                        break;
                }
                if(lastLineSizes.size() >= goal + 1 && indexSinceStartOfLine > lastLineSizes.get(goal))
                    caretIndex -= (indexSinceStartOfLine - lastLineSizes.get(goal));
            }              
        } else if (evt.getKeyCode() == KeyInput.KEY_HOME || evt.getKeyCode() == KeyInput.KEY_PRIOR) {
            caretIndex = 0;
            getVisibleText();
            if (shift) {
                setTextRangeEnd(caretIndex);
            } else {
                resetTextRange();
                setTextRangeStart(caretIndex);
            }
        } else {
            if (ctrl) {
                if (evt.getKeyCode() == KeyInput.KEY_C) {
                    if (copy) {
                        screen.setClipboardText(textRangeText);
                    }
                } else if (evt.getKeyCode() == KeyInput.KEY_V) {
                    if (paste) {
                        this.pasteTextInto();
                    }
                }
            } else {
                if (isEnabled) {
                    if (rangeHead != -1 && rangeTail != -1) {
                        editTextRangeText("");
                    }

                    if (!shift) {
                        resetTextRange();
                    }

                    if (evt.getKeyCode() == KeyInput.KEY_RETURN) {
                        nextChar = "\n";
                    } else {
                        nextChar = String.valueOf(evt.getKeyChar());
                    }

                    if (forceUpperCase) {
                        nextChar = nextChar.toUpperCase();
                    } else if (forceLowerCase) {
                        nextChar = nextChar.toLowerCase();
                    }
                    valid = true;
                    if (maxLength > 0) {
                        if (getText().length() >= maxLength) {
                            valid = false;
                        }
                    }
                    if (valid) {
                        if (type == Type.DEFAULT) {
                            textFieldText.add(caretIndex, nextChar);
                            caretIndex++;
                        } else if (type == Type.ALPHA) {
                            if (validateAlpha.indexOf(nextChar) != -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.ALPHA_NOSPACE) {
                            if (validateAlphaNoSpace.indexOf(nextChar) != -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.NUMERIC) {
                            if (validateNumeric.indexOf(nextChar) != -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.ALPHANUMERIC) {
                            if (validateAlpha.indexOf(nextChar) != -1 || validateNumeric.indexOf(nextChar) != -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.ALPHANUMERIC_NOSPACE) {
                            if (validateAlphaNoSpace.indexOf(nextChar) != -1 || validateNumeric.indexOf(nextChar) != -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.EXCLUDE_SPECIAL) {
                            if (validateSpecChar.indexOf(nextChar) == -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.EXCLUDE_CUSTOM) {
                            if (validateCustom.indexOf(nextChar) == -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        } else if (type == Type.INCLUDE_CUSTOM) {
                            if (validateCustom.indexOf(nextChar) != -1) {
                                textFieldText.add(caretIndex, nextChar);
                                caretIndex++;
                            }
                        }
                    }
                    if (!shift) {
                        if (caretIndex < textFieldText.size()) {
                            setTextRangeStart(caretIndex);
                        } else {
                            setTextRangeStart(textFieldText.size());
                        }
                    }
                }
            }
        }
        this.updateText(getVisibleText());

        if (shift && (evt.getKeyCode() == KeyInput.KEY_LEFT || evt.getKeyCode() == KeyInput.KEY_RIGHT)) {
            setTextRangeEnd(caretIndex);
        }

        controlKeyPressHook(evt, getText());
        evt.setConsumed();
    }

    @Override
    protected void setFontSizeIntern(float fontSize) {
        this.fontSize = fontSize;

        if (textElement != null) {
            textElement.setSize(fontSize);
        }

        float padding = screen.getStyle("TextField").getFloat("textPadding");

        if (screen.getStyle("TextField").getFloat("fontSize") - padding * 2 <= getHeight()) {
            caret.setHeight(screen.getStyle("TextField").getFloat("fontSize"));
        } else {
            caret.setHeight(getHeight() - padding * 2);
        }
    }

    /**
     * Returns the visible portion of the TextField's text
     *
     * @return String
     */
    @Override
    protected String getVisibleText() {
        System.out.println("getVisibleText");
        getTextFieldText();

        widthTest = new BitmapText(font, false);
        widthTest.setBox(null);
        widthTest.setSize(getFontSize());

        int index1 = 0, index2;

        widthTest.setText(finalText);
        if (head == -1 || tail == -1 || widthTest.getLineWidth() < getWidth()) {
            head = 0;
            tail = finalText.length();
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, tail);
            } else {
                visibleText = "";
            }
        } else if (caretIndex < head) {
            head = caretIndex;
            index2 = caretIndex;
            if (index2 == caretIndex && caretIndex != textFieldText.size()) {
                index2 = caretIndex + 1;
                widthTest.setText(finalText.substring(caretIndex, index2));
                while (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2)) {
                    if (index2 == textFieldText.size()) {
                        break;
                    }
                    widthTest.setText(finalText.substring(caretIndex, index2 + 1));
                    if (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2)) {
                        index2++;
                    }
                }
            }
            if (index2 != textFieldText.size()) {
                index2++;
            }
            tail = index2;
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, tail);
            } else {
                visibleText = "";
            }
        } else if (caretIndex > tail) {
            tail = caretIndex;
            index2 = caretIndex;
            if (index2 == caretIndex && caretIndex != 0) {
                index2 = caretIndex - 1;
                widthTest.setText(finalText.substring(index2, caretIndex));
                while (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2)) {
                    if (index2 == 0) {
                        break;
                    }
                    widthTest.setText(finalText.substring(index2 - 1, caretIndex));
                    if (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2)) {
                        index2--;
                    }
                }
            }
            head = index2;
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, caretIndex);
            } else {
                visibleText = "";
            }
        } else {
            index2 = tail;
            if (index2 > finalText.length()) {
                index2 = finalText.length();
            }
            if (tail != head) {
                widthTest.setText(finalText.substring(head, index2));
                if (widthTest.getLineWidth() > getWidth() - (getTextPadding() * 2)) {
                    while (widthTest.getLineWidth() > getWidth() - (getTextPadding() * 2)) {
                        if (index2 == head) {
                            break;
                        }
                        widthTest.setText(finalText.substring(head, index2 - 1));
                        if (widthTest.getLineWidth() > getWidth() - (getTextPadding() * 2)) {
                            index2--;
                        }
                    }
                } else if (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2)) {
                    while (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2) && index2 < finalText.length()) {
                        if (index2 == head) {
                            break;
                        }
                        widthTest.setText(finalText.substring(head, index2 + 1));
                        if (widthTest.getLineWidth() < getWidth() - (getTextPadding() * 2)) {
                            index2++;
                        }

                    }
                }
            }
            tail = index2;
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, tail);
            } else {
                visibleText = "";
            }
        }
        
        updateLineNumber(visibleText);
        int indexActualLine = 0, line = 1;
        int goal = whichLineNumber();
        
        for(int i = 0; i < visibleText.length(); i++)
        {
            if(visibleText.charAt(i) == '\n')
                line++;
            if(line == goal)
            {
                indexActualLine = i;
                break;
            }
        }
        
        String testString = "";
        widthTest.setText(".");
        float fixWidth = widthTest.getLineWidth();
        boolean useFix = false;

        if (!finalText.equals("")) {
            try {
                testString = finalText.substring(indexActualLine, caretIndex);
                if (testString.charAt(testString.length() - 1) == ' ') {
                    testString += ".";
                    useFix = true;
                }
            } catch (Exception ex) {
            }
        }

        widthTest.setText(testString);
        float nextCaretX = widthTest.getLineWidth();
        if (useFix) {
            nextCaretX -= fixWidth;
        }

        caretX = nextCaretX;
        
    
        if(caretIndex > 0 && visibleText.charAt(caretIndex - 1) == '\n')
            caretX = 0;
        setCaretPosition(getAbsoluteX() + caretX, goal);

        return visibleText;
    }

    protected void updateLineNumber(String visibleText)
    {
        lineNumber = 1;
        for(int i = 0; i < visibleText.length(); i++)
        {
            char c = visibleText.charAt(i);
            if(c == '\n')
                lineNumber++;
        }
    }
    
    protected int whichLineNumber()
    {
        int actualLineNumber = 1;
        for(int i = 0; i < visibleText.length(); i++)
        {
            char c = visibleText.charAt(i);
            if(c == '\n')
                actualLineNumber++;
            if(i + 1 == caretIndex)
                return actualLineNumber;
        }
        return 1;
    }

    /**
     * For internal use - do not call this method
     *
     * @param caretX float
     */
    @Override
    protected void setCaretPosition(float caretX) {
        System.out.println("setCaretPosition(x)");
        if (textElement != null) {
            if (hasTabFocus) {
                float padding = screen.getStyle("TextField").getFloat("textPadding");
                caret.getMaterial().setFloat("CaretX", caretX + getTextPadding());
                caret.getMaterial().setFloat("LastUpdate", app.getTimer().getTimeInSeconds());
                System.out.println("setYWhich:"+whichLineNumber());
                caret.setY(orgY-(whichLineNumber()-1)*caret.getHeight());
            }
        }
    }
    
    protected void setCaretPosition(float caretX, float caretY) {
        System.out.println("setCaretPosition(x,y)");
        if (textElement != null) {
            if (hasTabFocus) {
                float padding = screen.getStyle("TextField").getFloat("textPadding");
                caret.getMaterial().setFloat("CaretX", caretX + getTextPadding());
                caret.getMaterial().setFloat("LastUpdate", app.getTimer().getTimeInSeconds());
                System.out.println("setY:"+caretY);
                caret.setY(orgY-(caretY-1)*caret.getHeight());
            }
        }
    }

    @Override
    protected void setCaretPositionByXYNoRange(float x, float y) { 
        System.out.println(y);
        System.out.println("setCaretPositionByXYNoRange");
        int currentLine = 1;
        List<Integer> lastLineSizes = new ArrayList<>();
        for(int i = 0, j = -1; i < textFieldText.size(); i++)
        {
            char c = textFieldText.get(i).charAt(0);
            if(c == '\n' || textFieldText.size() == i+1)
            {
                currentLine++;
                if(j >= 0)
                    lastLineSizes.add(i - lastLineSizes.get(j));
                else
                    lastLineSizes.add(i);
                j++;
            }
        }            
        
        int indexY = 1;
        float h, padding = screen.getStyle("TextField").getFloat("textPadding");
        if (screen.getStyle("TextField").getFloat("fontSize") - padding * 2 <= getHeight()) {
            h = screen.getStyle("TextField").getFloat("fontSize");
        } else {
            h = getHeight() - padding * 2;
        }
        
        System.out.println(this.whichLineNumber());
        float relY;
        if(whichLineNumber() == 2)
        {
            relY = (caret.getY() * (y - h * 1)) / caret.getAbsoluteY(); //ok for 295
            indexY = (int)((orgY - relY + h * 0) / h + 1);
        }
        else
        {
            relY = (caret.getY() * (y - h * 0)) / caret.getAbsoluteY();
            indexY = (int)((orgY - relY + h * 1) / h + 1);
        }
        System.out.println(indexY);
        
        int index1 =  visibleText.length();
        if (visibleText.length() > 0) {
            String testString = "";
            widthTest.setText(".");
            float fixWidth = widthTest.getLineWidth();
            boolean useFix = false;

            widthTest.setSize(getFontSize());
            widthTest.setText(visibleText.substring(0, index1));
            while (caret.getAbsoluteX() + widthTest.getLineWidth() > (x + getTextPadding())) {
                if (index1 > 0) {
                    index1--;
                    testString = visibleText.substring(0, index1);
                    widthTest.setText(testString);
                } else {
                    break;
                }
            }
            
            caretIndex = index1;
            currentLine = 1;

            for(int lineSize : lastLineSizes)
            {
                if(currentLine < indexY)
                {
                    currentLine++;
                    caretIndex += lineSize + 1;
                }
                else
                    break;
            }
            
            updateLineNumber(visibleText);
            int indexActualLine = 0, line = 1;
            int goal = whichLineNumber();

            for(int i = 0; i < visibleText.length(); i++)
            {
                if(visibleText.charAt(i) == '\n')
                    line++;
                if(line == goal)
                {
                    indexActualLine = i;
                    break;
                }
            }

            try {
                testString = finalText.substring(indexActualLine, caretIndex);
                if (testString.charAt(testString.length() - 1) == ' ') {
                    testString += ".";
                    useFix = true;
                }
            } catch (Exception ex) {
            }
        
            widthTest.setText(testString);
            float nextCaretX = widthTest.getLineWidth();
            if (useFix) {
                nextCaretX -= fixWidth;
            }

            caretX = nextCaretX;
        }
        
        if(caretIndex > visibleText.length())
                    caretIndex = visibleText.length();
        if(caretIndex > 0 && visibleText.charAt(caretIndex - 1) == '\n')
            caretX = 0;
        System.out.println("XYRange");
        setCaretPosition(getAbsoluteX() + caretX, indexY);
    }
    
    @Override
    protected void centerTextVertically() {
        System.out.println("centerTextVertically");
    }
}
