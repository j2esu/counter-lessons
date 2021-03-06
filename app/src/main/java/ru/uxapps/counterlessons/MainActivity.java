package ru.uxapps.counterlessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements Repo.Listener {

    private CounterList mList;
    private boolean mIsListLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.m_main_create) {
                new CreateDialog().show(getSupportFragmentManager(), null);
            }
            if (menuItem.getItemId() == R.id.m_main_layout) {
                mIsListLayout = !mIsListLayout;
                mList.setLayout(mIsListLayout);
            }
            return true;
        });

        mList = new CounterList(findViewById(R.id.list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Repo.getInstance(MainActivity.this).setValue(counter, counter.value + 1);
            }

            @Override
            public void onMinus(Counter counter) {
                Repo.getInstance(MainActivity.this).setValue(counter, counter.value - 1);
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(MainActivity.this, CounterActivity.class)
                        .putExtra(CounterActivity.EXTRA_ID, counter.id));
            }
        });
        if (savedInstanceState != null) {
            mIsListLayout = savedInstanceState.getBoolean("list_layout", true);
            mList.setLayout(mIsListLayout);
        }
        onDataChanged();
        Repo.getInstance(this).addListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("list_layout", mIsListLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repo.getInstance(this).removeListener(this);
    }

    @Override
    public void onDataChanged() {
        mList.setCounters(Repo.getInstance(this).getCounters());
    }
}
