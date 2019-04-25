package ru.uxapps.counterlessons;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class InputFilters {

    public static void nameFilter(EditText et, AlertDialog dialog) {
        dialog.setOnShowListener(d -> {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setEnabled(TextUtils.getTrimmedLength(s) > 0);
                }
            });
            et.setText(et.getText());
        });
    }
}
