package ru.uxapps.counterlessons;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;

public class TextColorPicker implements ColorPicker {

    private final EditText mEt;

    public TextColorPicker(View root) {
        mEt = root.findViewById(R.id.d_edit_color);
    }

    @Override
    public int getColor() {
        return Color.parseColor(mEt.getText().toString());
    }

    @Override
    public void setColor(int color) {
        mEt.setText(String.format("#%06X", (0xFFFFFF & color)));
    }
}
