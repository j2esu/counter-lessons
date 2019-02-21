package ru.uxapps.counterlessons;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mValueTv;
    private int mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mValueTv = findViewById(R.id.value);
        findViewById(R.id.plus).setOnClickListener(v -> {
            mValue++;
            updateValue();
        });
        findViewById(R.id.minus).setOnClickListener(v -> {
            mValue--;
            updateValue();
        });

        findViewById(R.id.reset).setOnClickListener(v -> {
            int oldValue = mValue;
            mValue = 0;
            updateValue();
            Snackbar.make(v, "Counter was reset", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> {
                        mValue = oldValue;
                        updateValue();
                    })
                    .show();
        });
    }

    private void updateValue() {
        mValueTv.setText(String.valueOf(mValue));
    }
}
