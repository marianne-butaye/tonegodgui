package tonegod.gui.controls.text;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.material.Material;
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
        } else if (evt.getKeyCode() == KeyInput.KEY_DOWN || evt.getKeyCode() == KeyInput.KEY_UP) {
            int indexSinceStartOfLine = 0, currentLine = 1, goal = whichLineNumber();
            List<Integer> lastLineSizes = new ArrayList<>();
            int sum = 0;
            for (int i = 0, j = -1; i < finalText.length(); i++) {
                char c = finalText.charAt(i);
                if (c == '\n' || finalText.length() == i + 1)
                {
                    if(j >= 0 && c == '\n')
                        lastLineSizes.add(i - sum - 1);
                    else if (j >= 0)
                        lastLineSizes.add(i - sum);
                    else
                    {
                        lastLineSizes.add(i);
                        sum--;
                    }
                    j++;
                    sum += (lastLineSizes.get(j) + 1);
                }
            }
        
            sum = 0;
            for (int i = 0; i < lastLineSizes.size(); i++) {
                if(caretIndex >= sum && caretIndex <= sum + lastLineSizes.get(i))
                    break;
                currentLine++;
                sum += (lastLineSizes.get(i) + 1);
            }
            if (evt.getKeyCode() == KeyInput.KEY_UP && currentLine >= 2) {
                int ciBefore = caretIndex;
                caretIndex -= (lastLineSizes.get(currentLine - 2) + 1);
                
                if (sum + lastLineSizes.get(currentLine-1) == ciBefore && lastLineSizes.get(currentLine-1) > lastLineSizes.get(currentLine - 2)) {
                    caretIndex -= (lastLineSizes.get(currentLine - 1) - lastLineSizes.get(currentLine - 2));
                }
            } else if (evt.getKeyCode() == KeyInput.KEY_DOWN && lastLineSizes.size() > currentLine){
                int ciBefore = caretIndex;
                caretIndex += (lastLineSizes.get(currentLine - 1) + 1);
                
                if (sum + lastLineSizes.get(currentLine-1) == ciBefore && lastLineSizes.get(currentLine-1) > lastLineSizes.get(currentLine)) {
                    caretIndex -= (lastLineSizes.get(currentLine - 1) - lastLineSizes.get(currentLine));
                }
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
    protected void setTextRangeEnd(int tail) {
        System.out.println("setTextRangeEnd");
        if (!visibleText.equals("") && rangeHead != -1) {
            widthTest.setSize(getFontSize());

            widthTest.setText(".");
            float diff = widthTest.getLineWidth();

            float rangeX;
            System.out.println(rangeHead);
            System.out.println(head);
            if (rangeHead - this.head <= 0) {
                widthTest.setText("");
                rangeX = widthTest.getLineWidth();
            } else if (rangeHead - this.head < visibleText.length()) {
                widthTest.setText(visibleText.substring(0, rangeHead - this.head));
                float width = widthTest.getLineWidth();
                if (widthTest.getText().length() > 0) {
                    if (widthTest.getText().charAt(widthTest.getText().length() - 1) == ' ') {
                        widthTest.setText(widthTest.getText() + ".");
                        width = widthTest.getLineWidth() - diff;
                    }
                }
                rangeX = width;
                System.out.println("rX:"+rangeX);
            } else {
                widthTest.setText(visibleText);
                rangeX = widthTest.getLineWidth();
            }

            if (rangeHead >= this.head) {
                rangeX = getAbsoluteX() + rangeX + getTextPadding();
                System.out.println(rangeX);
            } else {
                rangeX = getTextPadding();
            }

            rangeTail = tail;
            System.out.println(rangeTail);
            if (tail - this.head <= 0) {
                widthTest.setText("");
            } else if (tail - this.head < visibleText.length()) {
                widthTest.setText(visibleText.substring(0, tail - this.head));
            } else {
                widthTest.setText(visibleText);
            }

            textRangeText = (rangeHead < rangeTail) ? finalText.substring(rangeHead, rangeTail) : finalText.substring(rangeTail, rangeHead);

            float rangeW = getTextPadding();
            if (rangeTail <= this.tail) {
                float width = widthTest.getLineWidth();
                if (widthTest.getText().length() > 0) {
                    if (widthTest.getText().charAt(widthTest.getText().length() - 1) == ' ') {
                        widthTest.setText(widthTest.getText() + ".");
                        width = widthTest.getLineWidth() - diff;
                    }
                }
                rangeW = getAbsoluteX() + width + getTextPadding();
                System.out.println("rW:"+rangeW);
            }
            
            float rangeY = 0, rangeZ = 0;

            if (rangeHead > rangeTail) {
                caret.getMaterial().setFloat("TextRangeStartX", rangeW);
                caret.getMaterial().setFloat("TextRangeEndX", rangeX);
                caret.getMaterial().setFloat("TextRangeStartY", rangeY);
                caret.getMaterial().setFloat("TextRangeEndY", rangeZ);
            } else {
                caret.getMaterial().setFloat("TextRangeStartX", rangeX);
                caret.getMaterial().setFloat("TextRangeEndX", rangeW);
                caret.getMaterial().setFloat("TextRangeStartY", rangeZ);
                caret.getMaterial().setFloat("TextRangeEndY", rangeY);
            }

            caret.getMaterial().setBoolean("ShowTextRange", true);
        }
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

        int index2;
        
        List<Integer> lastLineSizes = new ArrayList<>();
        int sum = 0;
        for (int i = 0, j = -1; i < finalText.length(); i++) {
            char c = finalText.charAt(i);
            if (c == '\n' || finalText.length() == i + 1)
            {
                if(j >= 0 && c == '\n')
                    lastLineSizes.add(i - sum - 1);
                else if (j >= 0)
                    lastLineSizes.add(i - sum);
                else
                {
                    lastLineSizes.add(i);
                    sum--;
                }
                j++;
                sum += (lastLineSizes.get(j) + 1);
            }
        } 

        widthTest.setText(finalText);
        if (caretIndex < head) {    
            int indexActualLine = 0;
            for (int i = 0, j = 0; i <= caretIndex; i++) {
                if (i > 0 && finalText.charAt(i - 1) == '\n') {
                    indexActualLine += lastLineSizes.get(j)+1;
                    j++;
                }
            }
            head = indexActualLine;
  
            index2 = finalText.length(); 
            for (int i = lastLineSizes.size(); i > lineNumber; i--) {
                index2 -= (lastLineSizes.get(i - 1) + 1);
            }
            
            widthTest.setText(finalText.substring(head, index2));
            tail = index2;
            
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, tail);
            } else {
                visibleText = "";
            }
        } else if (caretIndex > tail) {
            tail = finalText.length();

            index2 = 0;
            int i = 0;
            while (widthTest.getHeight() > this.getHeight()) {
                index2 += (lastLineSizes.get(i) + 1);
                widthTest.setText(finalText.substring(index2, finalText.length()));
                i++;
            }

            head = index2;
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, finalText.length());
            } else {
                visibleText = "";
            }
        } else if (caretIndex <= tail && caretIndex >= head) {
            if(tail > finalText.length())
                tail = finalText.length();
    
            if (head != tail && head != -1 && tail != -1) {
                visibleText = finalText.substring(head, tail);
            } else {
                visibleText = "";
            }
        }

        updateLineNumber(visibleText);
        int indexActualLine = 0, line = 1;
        int goal = whichLineNumber();

        for (int i = 0; i < visibleText.length(); i++) {
            if (visibleText.charAt(i) == '\n') {
                line++;
            }
            if (line == goal) {
                indexActualLine = i + head + 1;
                if (i == 0) {
                    indexActualLine--;
                }
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

        if (caretIndex - head - 1 > 0 && visibleText.charAt(caretIndex - head - 1) == '\n') {
            caretX = 0;
        }
        setCaretPosition(getAbsoluteX() + caretX, goal);
        
        return visibleText;
    }

    protected void updateLineNumber(String visibleText) {
        lineNumber = 1;
        for (int i = 0; i < visibleText.length(); i++) {
            char c = visibleText.charAt(i);
            if (c == '\n') {
                lineNumber++;
            }
        }
    }

    protected int whichLineNumber() {
        int actualLineNumber = 1;
        for (int i = 0; i < visibleText.length(); i++) {
            char c = visibleText.charAt(i);
            if (c == '\n') {
                actualLineNumber++;
            }
            if (i + 1 == caretIndex - head) {
                return actualLineNumber;
            }
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
                System.out.println("setYWhich:" + whichLineNumber());
                caret.setY(orgY - (whichLineNumber() - 1) * caret.getHeight());
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
                System.out.println("setY:" + caretY);
                caret.setY(orgY - (caretY - 1) * caret.getHeight());
            }
        }
    }

    @Override
    protected void setCaretPositionByXYNoRange(float x, float y) {
        System.out.println("setCaretPositionByXYNoRange");
        int currentLine = 1;
        List<Integer> lastLineSizes = new ArrayList<>();
        int sum = 0;
        for (int i = 0, j = -1; i < finalText.length(); i++) {
            char c = finalText.charAt(i);
            if (c == '\n' || finalText.length() == i + 1)
            {
                if(j >= 0 && c == '\n')
                    lastLineSizes.add(i - sum - 1);
                else if (j >= 0)
                    lastLineSizes.add(i - sum);
                else
                {
                    lastLineSizes.add(i);
                    sum--;
                }
                j++;
                sum += (lastLineSizes.get(j) + 1);
            }
        } 

        int indexY = 1;
        float h, padding = screen.getStyle("TextField").getFloat("textPadding");
        if (screen.getStyle("TextField").getFloat("fontSize") - padding * 2 <= getHeight()) {
            h = screen.getStyle("TextField").getFloat("fontSize");
        } else {
            h = getHeight() - padding * 2;
        }

        float relY;
        if (whichLineNumber() == 2) {
            relY = (caret.getY() * (y - h * 1)) / caret.getAbsoluteY(); //ok for 295
            indexY = (int) ((orgY - relY + h * 0) / h + 1);
        } else {
            relY = (caret.getY() * (y - h * 0)) / caret.getAbsoluteY();
            indexY = (int) ((orgY - relY + h * 1) / h + 1);
        }

        int index1 = visibleText.length();
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

            caretIndex = index1 + head;
            currentLine = 1;

            for (int lineSize : lastLineSizes) {
                if (currentLine < indexY) {
                    currentLine++;
                    caretIndex += lineSize + 1;
                } else {
                    break;
                }
            }
        
            updateLineNumber(visibleText);
            int indexActualLine = 0, line = 1;
            int goal = whichLineNumber();

            for (int i = 0; i < visibleText.length(); i++) {
                if (visibleText.charAt(i) == '\n') {
                    line++;
                }
                if (line == goal) {
                    indexActualLine = i + head + 1;
                    if (i == 0) {
                        indexActualLine--;
                    }
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

        if (caretIndex > visibleText.length()) {
            caretIndex = visibleText.length();
        }
        if (caretIndex - head - 1 > 0 && visibleText.charAt(caretIndex - head - 1) == '\n') {
            caretX = 0;
        }
        setCaretPosition(getAbsoluteX() + caretX, indexY);
    }

    @Override
    protected void centerTextVertically() {
        System.out.println("centerTextVertically");
    }
}
