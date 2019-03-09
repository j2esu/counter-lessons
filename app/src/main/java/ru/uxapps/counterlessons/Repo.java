package ru.uxapps.counterlessons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Repo {

    private static Repo sInstance;

    public static Repo getInstance() {
        if (sInstance == null) {
            sInstance = new Repo();
        }
        return sInstance;
    }

    private List<Counter> mList;

    private Repo() {
        mList = createTestData();
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

    public List<Counter> getCounters() {
        return mList;
    }

    public void inc(Counter counter) {
        int index = mList.indexOf(counter);
        mList.remove(index);
        mList.add(index, new Counter(counter.name, counter.value + 1));
    }

    public void dec(Counter counter) {
        int index = mList.indexOf(counter);
        mList.remove(index);
        mList.add(index, new Counter(counter.name, counter.value - 1));
    }

}
