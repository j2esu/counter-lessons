package ru.uxapps.counterlessons;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";

    private long mCounterId;
    private Counter mCounter;
    private TextView mValueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCounterId = getIntent().getLongExtra(EXTRA_ID, -1);
        mCounter = Repo.getInstance().getCounter(mCounterId);

        setContentView(R.layout.a_counter);
        mValueTv = findViewById(R.id.value);
        ((TextView) findViewById(R.id.name)).setText(mCounter.name);
        updateValue(mCounter.value);
        findViewById(R.id.plus).setOnClickListener(v -> updateValue(mCounter.value + 1));
        findViewById(R.id.minus).setOnClickListener(v -> updateValue(mCounter.value - 1));
        findViewById(R.id.reset).setOnClickListener(v -> {
            int oldValue = mCounter.value;
            updateValue(0);
            Snackbar.make(v, "Counter was reset", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> updateValue(oldValue))
                    .show();
        });
    }

    private void updateValue(int newValue) {
        Repo.getInstance().setValue(mCounter, newValue);
        mCounter = Repo.getInstance().getCounter(mCounterId);
        mValueTv.setText(String.valueOf(mCounter.value));
    }
}
