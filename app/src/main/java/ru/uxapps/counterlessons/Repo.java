package ru.uxapps.counterlessons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repo extends SQLiteOpenHelper {

    public interface Listener {

        void onDataChanged();

    }

    private static Repo sInstance;

    public static Repo getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Repo(context.getApplicationContext());
        }
        return sInstance;
    }

    private static final String DB_NAME = "counter.db";
    private static final int VERSION = 2;

    private static final String TABLE_NAME = "Counters";
    private static final String ID = "id";
    private static final String VAL = "val";
    private static final String NAME = "name";

    private static final String CREATE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            VAL + " INTEGER NOT NULL, " +
            NAME + " TEXT NOT NULL" +
            ");";

    private final Set<Listener> mListeners = new HashSet<>();

    private Repo(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
        createTestData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void createTestData(SQLiteDatabase db) {
        int count = 10;
        for (int i = 0; i < count; i++) {
            addCounterInner(db, "Counter " + i, i);
        }
    }

    private void addCounterInner(SQLiteDatabase db, String name, int val) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(VAL, val);
        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteCounter(long id) {
        getWritableDatabase().delete(TABLE_NAME, ID + " = " + id, null);
        notifyChanged();
    }

    public List<Counter> getCounters() {
        String[] cols = {ID, VAL, NAME};
        Cursor c = getReadableDatabase().query(TABLE_NAME, cols, null, null,
                null, null, null);
        List<Counter> list = new ArrayList<>(c.getColumnCount());
        while (c.moveToNext()) {
            long id = c.getLong(0);
            int val = c.getInt(1);
            String name = c.getString(2);
            list.add(new Counter(id, name, val));
        }
        c.close();
        return list;
    }

    @Nullable
    public Counter getCounter(long id) {
        String[] cols = {ID, VAL, NAME};
        try (Cursor c = getReadableDatabase().query(TABLE_NAME, cols, ID + " = " + id, null,
                null, null, null)) {
            if (c.moveToFirst()) {
                return new Counter(c.getLong(0), c.getString(2),
                        c.getInt(1));
            }
            return null;
        }
    }

    public void setValue(Counter counter, int value) {
        ContentValues cv = new ContentValues();
        cv.put(VAL, value);
        getWritableDatabase().update(TABLE_NAME, cv, ID + " = " + counter.id, null);
        notifyChanged();
    }

    public void addCounter(String name) {
        addCounterInner(getWritableDatabase(), name, 0);
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
