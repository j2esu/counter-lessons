package ru.uxapps.counterlessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CounterList mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        mList = new CounterList(findViewById(R.id.list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Repo.getInstance().inc(counter);
                updateList();
            }

            @Override
            public void onMinus(Counter counter) {
                Repo.getInstance().dec(counter);
                updateList();
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(MainActivity.this, CounterActivity.class));
            }
        });
        updateList();
    }

    private void updateList() {
        mList.setCounters(Repo.getInstance().getCounters());
    }

}
