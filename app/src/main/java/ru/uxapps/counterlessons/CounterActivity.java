package ru.uxapps.counterlessons;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    private static final String PREF_VAL = "PREF_VAL";

    private TextView mValueTv;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_counter);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mValueTv = findViewById(R.id.value);
        updateValue(getValue());
        findViewById(R.id.plus).setOnClickListener(v -> updateValue(getValue() + 1));
        findViewById(R.id.minus).setOnClickListener(v -> updateValue(getValue() - 1));
        findViewById(R.id.reset).setOnClickListener(v -> {
            int oldValue = getValue();
            updateValue(0);
            Snackbar.make(v, "Counter was reset", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> updateValue(oldValue))
                    .show();
        });
    }

    private void updateValue(int newValue) {
        mPrefs.edit().putInt(PREF_VAL, newValue).apply();
        mValueTv.setText(String.valueOf(newValue));
    }

    private int getValue() {
        return mPrefs.getInt(PREF_VAL, 0);
    }
}
