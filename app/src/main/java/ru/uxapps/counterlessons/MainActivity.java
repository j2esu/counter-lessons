package ru.uxapps.counterlessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        CounterList list = new CounterList(findViewById(R.id.list), new CounterList.Listener() {
            @Override
            public void onPlus(Counter counter) {
                Toast.makeText(MainActivity.this, "Plus on " + counter.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMinus(Counter counter) {
                Toast.makeText(MainActivity.this, "Minus on " + counter.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpen(Counter counter) {
                startActivity(new Intent(MainActivity.this, CounterActivity.class));
            }
        });
        list.setCounters(createTestData());
    }

    private List<Counter> createTestData() {
        int count = 100;
        Random random = new Random();
        List<Counter> data = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            data.add(new Counter("Counter " + (i + 1), random.nextInt(1000)));
        }
        return data;
    }

}
