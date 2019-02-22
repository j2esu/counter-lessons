package ru.uxapps.counterlessons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        List<Counter> data = new ArrayList<>();
        data.add(new Counter("Counter 1", 123));
        data.add(new Counter("Counter 2", 456));
        data.add(new Counter("Counter 3", 789));

        CounterList list = new CounterList(findViewById(R.id.list));
        list.setCounters(data);
    }

}
