package ru.uxapps.counterlessons;

import android.graphics.Color;

public interface ColorPicker {

    int[] COLORS = new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.GRAY, Color.DKGRAY, Color.MAGENTA};

    int getColor();

    void setColor(int color);

}
