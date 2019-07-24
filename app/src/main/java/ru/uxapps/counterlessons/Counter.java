package ru.uxapps.counterlessons;

public class Counter {

    public final long id;
    public final String name;
    public final int value;
    public final int color;

    public Counter(long id, String name, int value, int color) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
