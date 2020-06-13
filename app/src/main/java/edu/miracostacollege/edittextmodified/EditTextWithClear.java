package edu.miracostacollege.edittextmodified;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;

    private void init(){
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_clear_opaque_24dp, null);


        //If clear(X) button is tapped, clear the text
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float clearButtonStart; //used for left-to-right language
                float clearButtonEnd; // used for right=to-left language
                boolean isClearButtonClicked = false;

                if ((getCompoundDrawablesRelative()[2] != null)) {
                    //Detect the touch RTL or LTR layout direction
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // If RTL, get the end of the button on the left side.
                        clearButtonEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        // Layout is LTR.
                        // Get the start of the button on the right side.
                        clearButtonStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());

                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }
                    //Check for actions if the button is tapped
                    if(isClearButtonClicked){
                        //Check for ACTION_DOWN
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            //Switch to black version of button
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        //If text changes, show or hide the clear(X) button
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                mClearButtonImage,
                null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                null,
                null);
    }
}








