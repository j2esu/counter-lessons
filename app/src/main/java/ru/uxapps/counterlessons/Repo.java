package ru.uxapps.counterlessons;

import java.util.ArrayList;
import java.util.List;

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
        List<Counter> data = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            data.add(new Counter(i,"Counter " + (i + 1), i));
        }
        return data;
    }

    public List<Counter> getCounters() {
        return mList;
    }

    public Counter getCounter(long id) {
        for (Counter counter : mList) {
            if (counter.id == id) return counter;
        }
        return null;
    }

    public void setValue(Counter counter, int value) {
        int index = mList.indexOf(counter);
        mList.remove(index);
        mList.add(index, new Counter(counter.id, counter.name, value));
    }

}
