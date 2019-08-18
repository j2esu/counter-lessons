package ru.uxapps.counterlessons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScrollColorPicker implements ColorPicker {

    private final int[] mColors;
    private final View[] mMarks;
    private int mSelectedColor;

    public ScrollColorPicker(View root, int[] colors) {
        mColors = colors;
        mMarks = new View[mColors.length];
        ViewGroup content = root.findViewById(R.id.d_edit_colors);
        LayoutInflater inflater = LayoutInflater.from(root.getContext());
        for (int i = 0; i < mColors.length; i++) {
            View item = inflater.inflate(R.layout.i_color, content, false);
            int color = mColors[i];
            item.setOnClickListener(v -> setColor(color));
            ImageView bg = item.findViewById(R.id.i_color_bg);
            bg.setColorFilter(color);
            View selected = item.findViewById(R.id.i_color_selected);
            mMarks[i] = selected;
            content.addView(item, i);
        }
    }

    @Override
    public int getColor() {
        return mSelectedColor;
    }

    @Override
    public void setColor(int color) {
        mSelectedColor = color;
        for (int i = 0; i < mColors.length; i++) {
            mMarks[i].setVisibility(color == mColors[i] ? View.VISIBLE : View.GONE);
        }
    }
}
