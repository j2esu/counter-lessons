package ru.uxapps.counterlessons;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity implements Repo.Listener, ConfirmDeleteDialog.Host {

    public static final String EXTRA_ID = "EXTRA_ID";

    private long mCounterId;
    private TextView mValueTv;
    private TextView mNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCounterId = getIntent().getLongExtra(EXTRA_ID, -1);
        setContentView(R.layout.a_counter);
        findViewById(R.id.a_counter_back).setOnClickListener(v -> finish());
        findViewById(R.id.a_counter_del).setOnClickListener(v ->
                new ConfirmDeleteDialog().show(getSupportFragmentManager(), null));
        mValueTv = findViewById(R.id.value);
        mNameTv = findViewById(R.id.name);
        findViewById(R.id.plus).setOnClickListener(v -> changeValue(getCounter().value + 1));
        findViewById(R.id.minus).setOnClickListener(v -> changeValue(getCounter().value - 1));
        findViewById(R.id.reset).setOnClickListener(v -> {
            int oldValue = getCounter().value;
            changeValue(0);
            Snackbar.make(v, "Counter was reset", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", ignored -> changeValue(oldValue))
                    .show();
        });
        onDataChanged();
        Repo.getInstance(this).addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repo.getInstance(this).removeListener(this);
    }

    private void changeValue(int newValue) {
        Repo.getInstance(this).setValue(getCounter(), newValue);
    }

    private Counter getCounter() {
        return Repo.getInstance(this).getCounter(mCounterId);
    }

    @Override
    public void onDataChanged() {
        Counter counter = getCounter();
        if (counter != null) {
            mValueTv.setText(String.valueOf(counter.value));
            mNameTv.setText(counter.name);
        } else {
            finish();
        }
    }

    @Override
    public void onConfirm() {
        Repo.getInstance(this).deleteCounter(mCounterId);
    }
}
