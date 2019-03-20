package ru.uxapps.counterlessons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repo {

    public interface Listener {

        void onDataChanged();

    }

    private static Repo sInstance;

    public static Repo getInstance() {
        if (sInstance == null) {
            sInstance = new Repo();
        }
        return sInstance;
    }

    private List<Counter> mList;
    private final Set<Listener> mListeners = new HashSet<>();

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
        notifyChanged();
    }

    private void notifyChanged() {
        for (Listener listener : mListeners) listener.onDataChanged();
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

}
